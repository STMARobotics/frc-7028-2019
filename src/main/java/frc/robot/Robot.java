/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.OperateCommand;
import frc.robot.commands.auto.PathCommand;
import frc.robot.commands.auto.PointCommand;
import frc.robot.commands.vision.CombinedTarget;
import frc.robot.commands.vision.CommandTillVision;
import frc.robot.drivesystems.driver.BrandonJoystickDriver;
import frc.robot.drivesystems.driver.BrandonXboxDriver;
import frc.robot.drivesystems.driver.Driver;
import frc.robot.drivesystems.driver.HunterXboxDriver;
import frc.robot.drivesystems.operator.HunterOperator;
import frc.robot.drivesystems.operator.Operator;
import frc.robot.motion.Path;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class Robot extends TimedRobot {

  private Command driveCommand;
  private Command operateCommand;
  private Path start2BayOne;
  private Path bayOne2Human;
  private Path human2BayTwo;
  private CommandGroup pGroup;

  private DriveTrainSubsystem driveTrainSubsystem;
  private ManipulatorsSubsystem manipulatorsSubsystem;
  private ClimbSubsystem climbSubsystem;
  private GyroSubsystem gyroSubsystem;
  private PowerDistributionPanel pdp = new PowerDistributionPanel();

  private SendableChooser<Driver> driverChooser = new SendableChooser<>();
  private SendableChooser<Operator> operatorChooser = new SendableChooser<>();

  private Joystick driverJoystick = new Joystick(0);
  private Joystick operatorJoystick = new Joystick(2);
  private XboxController driverController = new XboxController(0);
  private XboxController operatorController = new XboxController(2);

  @Override
  public void robotInit() {

    Globals.Setup();

    // driverChooser.setDefaultOption("Jorge Xbox Driver", new JorgeXboxDriver(driverController));
    driverChooser.addOption("Hunter Xbox Driver", new HunterXboxDriver(driverController));
    driverChooser.addOption("Brandon Xbox Driver", new BrandonXboxDriver(driverController));
    driverChooser.setDefaultOption("Brandon Joystick Driver", new BrandonJoystickDriver(driverJoystick));
    SmartDashboard.putData("Driver Chooser", driverChooser);

    operatorChooser.setDefaultOption("Hunter Operator", new HunterOperator(operatorJoystick));
    SmartDashboard.putData("Operator Chooser", operatorChooser);

    driveTrainSubsystem = new DriveTrainSubsystem(driverChooser);
    manipulatorsSubsystem = new ManipulatorsSubsystem(pdp, operatorChooser);
    climbSubsystem = new ClimbSubsystem(operatorChooser, driverChooser);
    gyroSubsystem = new GyroSubsystem();

    driveCommand = new DriveCommand(driveTrainSubsystem, driverChooser);
    operateCommand = new OperateCommand(manipulatorsSubsystem, climbSubsystem, operatorChooser);

    driveTrainSubsystem.setDefaultCommand(driveCommand);
    manipulatorsSubsystem.setDefaultCommand(operateCommand);
    climbSubsystem.setDefaultCommand(operateCommand);

    start2BayOne = Path.loadFromPathWeaver("Start2BayOne");
    bayOne2Human = Path.loadFromPathWeaver("BayOne2Human");
    human2BayTwo = Path.loadFromPathWeaver("Human2BayTwo");

    generalInit();
  }

  public void generalInit() {
    climbSubsystem.resetClimbGuides();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
    driveCommand.cancel();
    driveTrainSubsystem.setNeutralMode(NeutralMode.Coast);

    Globals.getLimelight().Disable();
  }
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    generalInit();
    Globals.getLimelight().Init();
    driveTrainSubsystem.setNeutralMode(NeutralMode.Brake);

    pGroup = new CommandGroup();
    pGroup.addSequential(new CommandTillVision(new PathCommand(start2BayOne, driveTrainSubsystem), new CombinedTarget(driveTrainSubsystem, Globals.getLimelight()).setTarget(1.2), driveTrainSubsystem));
    pGroup.addSequential(new PointCommand(driveTrainSubsystem, gyroSubsystem, -90));
    pGroup.addSequential(new CommandTillVision(new PathCommand(bayOne2Human, driveTrainSubsystem), new CombinedTarget(driveTrainSubsystem, Globals.getLimelight()).setTarget(1.2), driveTrainSubsystem));
    pGroup.addSequential(new PointCommand(driveTrainSubsystem, gyroSubsystem, 180));
    pGroup.addSequential(new CommandTillVision(new PathCommand(human2BayTwo, driveTrainSubsystem), new CombinedTarget(driveTrainSubsystem, Globals.getLimelight()).setTarget(1.2), driveTrainSubsystem));
    pGroup.start();
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    if (driverChooser.getSelected().getAutoOverride() && pGroup != null) {
      pGroup.cancel();
      pGroup = null;
      System.out.println("pGroup canceled-----------------------------------");
    }
  }

  @Override
  public void teleopInit() {
    generalInit();
    Globals.getLimelight().Init();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (pGroup != null) {
      pGroup.cancel();
    }
    driveTrainSubsystem.setNeutralMode(NeutralMode.Coast);
    driveCommand.start();
    operateCommand.start();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    teleopInit();
  }

  @Override
  public void testPeriodic() {
    teleopPeriodic();
  }

}
