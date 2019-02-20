/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.OperateCommand;
import frc.robot.commands.auto.AutoCommandGroup;
import frc.robot.commands.auto.CalibratePivotCommand;
import frc.robot.commands.vision.CombinedTarget;
import frc.robot.commands.vision.VisionTillTouch;
import frc.robot.drivesystems.driver.Driver;
import frc.robot.drivesystems.driver.JorgeXboxDriver;
import frc.robot.drivesystems.driver.SoloDriver;
import frc.robot.drivesystems.operator.HunterOperator;
import frc.robot.drivesystems.operator.Operator;
import frc.robot.drivesystems.operator.SoloOperator;
import frc.robot.motion.Path;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;
import frc.robot.vision.Limelight;

public class Robot extends TimedRobot {

    private Command driveCommand;
    private Command operateCommand;
    private Path start2BayOne;
    private Path bayOne2Human;
    private Path human2BayTwo;
    private CommandGroup autoCommand;

    private DriveTrainSubsystem driveTrainSubsystem;
    private ManipulatorsSubsystem manipulatorsSubsystem;
    private ClimbSubsystem climbSubsystem;
    private GyroSubsystem gyroSubsystem;
    private Limelight limelight;

    private SendableChooser<Driver> driverChooser = new SendableChooser<>();
    private SendableChooser<Operator> operatorChooser = new SendableChooser<>();

    private Joystick driverJoystick = new Joystick(0);
    private Joystick operatorJoystick = new Joystick(2);
    private XboxController driverController = new XboxController(0);
    private XboxController operatorController = new XboxController(2);

    @Override
    public void robotInit() {

        limelight = new Limelight(true);

        driverChooser.setDefaultOption("Jorge Xbox Driver", new JorgeXboxDriver(driverController));
        driverChooser.addOption("Solo", new SoloDriver(driverController));
        SmartDashboard.putData("Driver Chooser", driverChooser);

        operatorChooser.setDefaultOption("Hunter Operator", new HunterOperator(operatorJoystick));
        operatorChooser.addOption("Solo Operator", new SoloOperator(driverController));
        SmartDashboard.putData("Operator Chooser", operatorChooser);

        driveTrainSubsystem = new DriveTrainSubsystem();
        manipulatorsSubsystem = new ManipulatorsSubsystem();
        climbSubsystem = new ClimbSubsystem(operatorChooser);
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

        Thread cameraThread = new Thread(() -> {
            CameraServer.getInstance().startAutomaticCapture();
        });
        cameraThread.run();
    }

    public void generalInit() {
        climbSubsystem.resetClimbGuides();
        // manipulatorsSubsystem.setPivotPosition(PivotPosition.START);
    }

    @Override
    public void robotPeriodic() {
        if (driverController.getYButton()) {
            new VisionTillTouch(new CombinedTarget(driveTrainSubsystem, limelight), driverController).start();
        }
    }

    @Override
    public void disabledInit() {
        driveCommand.cancel();

        manipulatorsSubsystem.setPivotPosition(PivotPosition.UNLOCK_HATCH);
        limelight.disable();
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        generalInit();
        limelight.init();
        driveTrainSubsystem.setNeutralMode(NeutralMode.Brake);

        autoCommand = new AutoCommandGroup(manipulatorsSubsystem, climbSubsystem, driveTrainSubsystem);
        autoCommand.addParallel(new CalibratePivotCommand(manipulatorsSubsystem));
        // autoCommand.addSequential(new CommandTillVision(new PathCommand(start2BayOne,
        // driveTrainSubsystem), new CombinedTarget(driveTrainSubsystem,
        // Globals.getLimelight()).setTarget(1.2), driveTrainSubsystem));
        // autoCommand.addSequential(new PointCommand(driveTrainSubsystem,
        // gyroSubsystem, -90));
        // autoCommand.addSequential(new CommandTillVision(new PathCommand(bayOne2Human,
        // driveTrainSubsystem), new CombinedTarget(driveTrainSubsystem,
        // Globals.getLimelight()).setTarget(1.2), driveTrainSubsystem));
        // autoCommand.addSequential(new PointCommand(driveTrainSubsystem,
        // gyroSubsystem, 180));
        // autoCommand.addSequential(new CommandTillVision(new PathCommand(human2BayTwo,
        // driveTrainSubsystem), new CombinedTarget(driveTrainSubsystem,
        // Globals.getLimelight()).setTarget(1.2), driveTrainSubsystem));
        autoCommand.start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        if (driverChooser.getSelected().getAutoOverride() && autoCommand != null) {
            autoCommand.cancel();
            autoCommand = null;
        }
    }

    @Override
    public void teleopInit() {
        generalInit();
        limelight.init();
        if (autoCommand != null) {
            autoCommand.cancel();
        }
        driveTrainSubsystem.setNeutralMode(NeutralMode.Brake);
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
