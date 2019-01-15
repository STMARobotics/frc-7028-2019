/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.OperateCommand;
import frc.robot.commands.VisionCommands.AdvancedTarget;
import frc.robot.commands.VisionCommands.AutoTarget;
import frc.robot.commands.VisionCommands.CombinedTarget;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.vision.Limelight;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivesystems.BrandonDriver;
import frc.robot.drivesystems.BrandonOperator;
import frc.robot.drivesystems.ControlSet;
import frc.robot.drivesystems.Driver;
import frc.robot.drivesystems.HunterDriver;
import frc.robot.drivesystems.HunterOperator;
import frc.robot.drivesystems.JorgeDriver;
import frc.robot.drivesystems.JorgeOperator;
import frc.robot.drivesystems.Operator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static SendableChooser<Driver> driverChooser = new SendableChooser<>();
  private static SendableChooser<Operator> operatorChooser = new SendableChooser<>();
  private static SendableChooser<XboxController> driverControllerChooser = new SendableChooser<>();
  private static SendableChooser<XboxController> operatorControllerChooser = new SendableChooser<>();
  private static SendableChooser<Command> autoChooser = new SendableChooser<>();
  private final XboxController controllerOne = new XboxController(0);
  private final XboxController controllerTwo = new XboxController(2);
  private final ControlSet controlSet = new ControlSet(driverControllerChooser, operatorControllerChooser);

  
  public static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem(driverChooser);
  private static ManipulatorsSubsystem manipulatorsSubsystem = new ManipulatorsSubsystem();
  public static GyroSubsystem gyroSubsystem = new GyroSubsystem();
  public static Limelight limelight = new Limelight();

  private Command driveCommand;
  private Command operateCommand;
  private Command autoCommand;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    driveCommand = new DriveCommand(driverChooser, driveTrainSubsystem);
    operateCommand = new OperateCommand(operatorChooser, manipulatorsSubsystem);

    driverChooser.setDefaultOption("Jorge Driver", new JorgeDriver(controlSet));
    driverChooser.addOption("Brandon Driver", new BrandonDriver(controlSet));
    driverChooser.addOption("Hunter Driver", new HunterDriver(controlSet));
    SmartDashboard.putData("Driver", driverChooser);

    driverControllerChooser.setDefaultOption("Driver Controller: 1", controllerOne);
    driverControllerChooser.addOption("Driver Controller: 2", controllerTwo);
    SmartDashboard.putData("Driver Controller", driverControllerChooser);

    operatorChooser.setDefaultOption("Jorge Operator", new JorgeOperator(controlSet));
    operatorChooser.addOption("Brandon Operator", new BrandonOperator(controlSet));
    operatorChooser.addOption("Hunter Operator", new HunterOperator(controlSet));
    SmartDashboard.putData("Operator", operatorChooser);

    operatorControllerChooser.setDefaultOption("Operator Controller: 1", controllerOne);
    operatorControllerChooser.addOption("Operator Controller: 2", controllerTwo);
    SmartDashboard.putData("Operator Controller", operatorControllerChooser);

    autoChooser.addOption("Follow the thing", new AutoTarget());
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
    driveCommand.cancel();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    driveTrainSubsystem.setNeutralMode(NeutralMode.Brake);
    
    System.out.println(autoCommand);
    autoCommand = new CombinedTarget();
    autoCommand.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autoCommand != null) {
      autoCommand.cancel();
    }
    driveTrainSubsystem.setNeutralMode(NeutralMode.Coast);
    driveCommand.start();
    operateCommand.start();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    teleopInit();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    teleopPeriodic();
  }

}
