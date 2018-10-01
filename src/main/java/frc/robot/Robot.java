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
import frc.robot.commands.CenterAutoLeftCommand;
import frc.robot.commands.CenterAutoRightCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.LeftAutoNoSwitchCommand;
import frc.robot.commands.LeftAutoSwitchCommand;
import frc.robot.commands.OperateCommand;
import frc.robot.commands.RightAutoNoSwitchCommand;
import frc.robot.commands.RightAutoSwitchCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivesystems.BrandonDriver;
import frc.robot.drivesystems.BrandonOperator;
import frc.robot.drivesystems.ControlSet;
import frc.robot.drivesystems.Driver;
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
  private static DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  private static ManipulatorsSubsystem manipulatorsSubsystem = new ManipulatorsSubsystem();
  private static OI m_oi;

  private static SendableChooser<Command> m_chooser = new SendableChooser<>();
  private static SendableChooser<Driver> driverChooser = new SendableChooser<>();
  private static SendableChooser<Operator> operatorChooser = new SendableChooser<>();
  private static SendableChooser<XboxController> driverControllerChooser = new SendableChooser<>();
  private static SendableChooser<XboxController> operatorControllerChooser = new SendableChooser<>();
  private static SendableChooser<String> autoChooser = new SendableChooser<>();
  private final XboxController controllerOne = new XboxController(0);
  private final XboxController controllerTwo = new XboxController(2);
  private final ControlSet controlSet = new ControlSet(driverControllerChooser, operatorControllerChooser);

  private Command m_autonomousCommand;
  private Command driveCommand;
  private Command operateCommand;
  private Command autoCommand;

  // finds position of switches and scale
  // array of three characters (either 'L' or 'R')
  // first char = position of alliance switch
  // second char = position of scale
  // third cher = position of opponent switch

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    driveCommand = new DriveCommand(driverChooser, driveTrainSubsystem);
    operateCommand = new OperateCommand(operatorChooser, manipulatorsSubsystem);
    // chooser.addObject("My Auto", new MyAutoCommand());
    //SmartDashboard.putData("Auto mode", m_chooser);

    driverChooser.addDefault("Jorge Driver", new JorgeDriver(controlSet));
    driverChooser.addObject("Brandon Driver", new BrandonDriver(controlSet));
    SmartDashboard.putData("Driver", driverChooser);

    driverControllerChooser.addDefault("Driver Controller: 1", controllerOne);
    driverControllerChooser.addObject("Driver Controller: 2", controllerTwo);
    SmartDashboard.putData("Driver Controller", driverControllerChooser);

    operatorChooser.addDefault("Jorge Operator", new JorgeOperator(controlSet));
    operatorChooser.addObject("Brandon Operator", new BrandonOperator(controlSet));
    SmartDashboard.putData("Operator", operatorChooser);

    operatorControllerChooser.addObject("Operator Controller: 1", controllerOne);
    operatorControllerChooser.addDefault("Operator Controller: 2", controllerTwo);
    SmartDashboard.putData("Operator Controller", operatorControllerChooser);

    autoChooser.addDefault("Center Auto", "Center");
    autoChooser.addObject("Right Auto", "Right");
    autoChooser.addObject("Left Auto", "Left");
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
    SmartDashboard.putNumber("Gyro position", driveTrainSubsystem.getGyroPosition());
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
    // m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    // m_autonomousCommand.start();
    // }
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    char switchPosition = gameData.charAt(0);
    String chosenCommand = autoChooser.getSelected();
    if (switchPosition == 'L') {
      if (chosenCommand.equals("Right")) {
        autoCommand = new RightAutoNoSwitchCommand(driveTrainSubsystem);
      } else if (chosenCommand.equals("Left")) {
        autoCommand = new LeftAutoSwitchCommand(driveTrainSubsystem, manipulatorsSubsystem);
      } else {
        autoCommand = new CenterAutoLeftCommand(driveTrainSubsystem, manipulatorsSubsystem);
      }
    } else {
      if (chosenCommand.equals("Right")) {
        autoCommand = new RightAutoSwitchCommand(driveTrainSubsystem, manipulatorsSubsystem);
      } else if (chosenCommand.equals("Left")) {
        autoCommand = new LeftAutoNoSwitchCommand(driveTrainSubsystem);
      } else {
        autoCommand = new CenterAutoRightCommand(driveTrainSubsystem, manipulatorsSubsystem);
      }
    }
    driveTrainSubsystem.setNeutralMode(NeutralMode.Brake);
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
    // if (m_autonomousCommand != null) {
    // m_autonomousCommand.cancel();
    // }
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
