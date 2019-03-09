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
import frc.robot.commands.auto.CalibratePivotCommand;
import frc.robot.commands.auto.DepositHatch;
import frc.robot.commands.auto.PathCommand;
import frc.robot.commands.auto.PointCommand;
import frc.robot.commands.vision.CombinedTarget;
import frc.robot.commands.vision.CommandTillVision;
import frc.robot.commands.vision.VisionTillTouch;
import frc.robot.drivesystems.driver.Driver;
import frc.robot.drivesystems.driver.JorgeXboxDriver;
import frc.robot.drivesystems.driver.SlowDriver;
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
    private Path start2BayOneLeft;
    private Path bayOne2HumanLeft;
    private Path human2BayTwoLeft;
    private Path start2BayOneRight;
    private Path bayOne2HumanRight;
    private Path human2BayTwoRight;
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

        driveTrainSubsystem = new DriveTrainSubsystem();
        manipulatorsSubsystem = new ManipulatorsSubsystem();
        climbSubsystem = new ClimbSubsystem(operatorChooser);
        gyroSubsystem = new GyroSubsystem();

        driverChooser.setDefaultOption("Jorge Xbox Driver", 
            new JorgeXboxDriver(driverController, new PointCommand(driveTrainSubsystem, gyroSubsystem)));
        driverChooser.addOption("Solo", new SoloDriver(driverController));
        driverChooser.addOption("Slow", new SlowDriver(driverController));
        SmartDashboard.putData("Driver Chooser", driverChooser);

        operatorChooser.setDefaultOption("Hunter Operator", new HunterOperator(operatorJoystick));
        operatorChooser.addOption("Solo Operator", new SoloOperator(driverController));
        SmartDashboard.putData("Operator Chooser", operatorChooser);

        driveCommand = new DriveCommand(driveTrainSubsystem, driverChooser);
        operateCommand = new OperateCommand(manipulatorsSubsystem, climbSubsystem, operatorChooser);

        driveTrainSubsystem.setDefaultCommand(driveCommand);
        manipulatorsSubsystem.setDefaultCommand(operateCommand);
        //climbSubsystem.setDefaultCommand(operateCommand);

        start2BayOneLeft = Path.loadFromPathWeaver("Start2BayOneLeft");
        bayOne2HumanLeft = Path.loadFromPathWeaver("BayOne2HumanLeft");
        human2BayTwoLeft = Path.loadFromPathWeaver("Human2BayTwoLeft");

        start2BayOneRight = Path.loadFromPathWeaver("Start2BayOneRight");
        bayOne2HumanRight = Path.loadFromPathWeaver("BayOne2HumanRight");
        human2BayTwoRight = Path.loadFromPathWeaver("Human2BayTwoRight");

        generalInit();

        Thread cameraThread = new Thread(() -> {
            CameraServer.getInstance().startAutomaticCapture();
        });
        cameraThread.run();
        gyroSubsystem.reset();
    }

    public void generalInit() {
        climbSubsystem.resetClimbGuides();
    }

    @Override
    public void robotPeriodic() {
        if (driverController.getYButtonPressed()) {
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
        gyroSubsystem.reset();
        limelight.init();
        driveTrainSubsystem.setNeutralMode(NeutralMode.Brake);
        
        autoCommand = new CommandGroup();
        autoCommand.addSequential(new CalibratePivotCommand(manipulatorsSubsystem));

        //Front left Hatch
        autoCommand.addSequential(new CommandTillVision(
            new PathCommand(start2BayOneLeft, driveTrainSubsystem),
            new DepositHatch(driveTrainSubsystem, gyroSubsystem, limelight, manipulatorsSubsystem), 
            limelight, 
            driveTrainSubsystem));
        //Left Hatch Player Depsit
        //autoCommand.addSequential(new CommandTillVision(new PathCommand(bayOne2HumanLeft, driveTrainSubsystem),
                 //new DepositHatch(driveTrainSubsystem, gyroSubsystem, limelight, manipulatorsSubsystem, 180), limelight, driveTrainSubsystem));
        //Side right hatch
        //autoCommand.addSequential(new CommandTillVision(new PathCommand(human2BayTwoLeft, driveTrainSubsystem),
                 //new DepositHatch(driveTrainSubsystem, gyroSubsystem, limelight, manipulatorsSubsystem, 90), limelight, driveTrainSubsystem));
        
        //autoCommand.addSequential(new CombinedTarget(driveTrainSubsystem, limelight));
        autoCommand.start();

        
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();


        if (driverChooser.getSelected().getAutoOverride() && autoCommand != null && autoCommand.isCompleted()) {
            teleopInit();
            System.out.println("Stopping Auto");
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
