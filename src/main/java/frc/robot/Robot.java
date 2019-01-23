/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.OperateCommand;
import frc.robot.commands.PathCommand;
import frc.robot.commands.PathGroupCommand;
import frc.robot.commands.SpinCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.commands.VisionCommands.AutoTarget;
import frc.robot.commands.VisionCommands.CombinedTarget;

import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.drivesystems.BrandonDriver;
import frc.robot.drivesystems.BrandonOperator;
import frc.robot.drivesystems.Driver;
import frc.robot.drivesystems.HunterDriver;
import frc.robot.drivesystems.HunterOperator;
import frc.robot.drivesystems.JorgeDriver;
import frc.robot.drivesystems.JorgeOperator;
import frc.robot.drivesystems.Operator;
import frc.robot.motion.Path;

public class Robot extends TimedRobot {


  private static SendableChooser<Command> autoChooser = new SendableChooser<>();
  private Command driveCommand;
  private Command operateCommand;
  private Command autoCommand;
  private Path start2BayOne;
  private Path bayOne2Human;
  private Path human2BayTwo;

  @Override
  public void robotInit() {

    driveCommand = new DriveCommand();
    operateCommand = new OperateCommand();

    Controls.robotInit();

    autoChooser.addOption("Follow the thing", new AutoTarget());

    SmartDashboard.putData("Auto Chooser", autoChooser);

    start2BayOne = Path.loadFromPathWeaver("Start2BayOne");
    bayOne2Human = Path.loadFromPathWeaver("BayOne2Human");
    human2BayTwo = Path.loadFromPathWeaver("Human2BayTwo");

  }


  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
    driveCommand.cancel();
    time.delay(2);
    Globals.getDrivetrain().setNeutralMode(NeutralMode.Coast);
  }

  Timer time;
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  PathGroupCommand pGroup;
  @Override
  public void autonomousInit() {
    Globals.getDrivetrain().setNeutralMode(NeutralMode.Brake);
    // autoCommand.start();
    pGroup = new PathGroupCommand();
    pGroup.addSequential(new PathCommand(start2BayOne, Globals.getDrivetrain()));
    pGroup.addSequential(new SpinCommand(-90));
    pGroup.addSequential(new PathCommand(bayOne2Human, Globals.getDrivetrain()));
    pGroup.addSequential(new SpinCommand(-90));
    pGroup.addSequential(new SpinCommand(-90));
    pGroup.addSequential(new PathCommand(human2BayTwo, Globals.getDrivetrain()));
    pGroup.start();
  }

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
    Globals.getDrivetrain().setNeutralMode(NeutralMode.Coast);
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
