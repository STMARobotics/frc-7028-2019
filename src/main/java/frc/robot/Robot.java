/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.OperateCommand;
import frc.robot.commands.AutoCommands.PathCommand;
import frc.robot.commands.AutoCommands.PathGroupCommand;
import frc.robot.commands.VisionCommands.CombinedTarget;
import frc.robot.commands.VisionCommands.CommandTillVision;
import frc.robot.motion.Path;

public class Robot extends TimedRobot {

  private Command driveCommand;
  private Command operateCommand;
  private Command autoCommand;
  private Path start2BayOne;
  private Path bayOne2Human;
  private Path human2BayTwo;

  @Override
  public void robotInit() {

    Globals.Setup();

    driveCommand = new DriveCommand();
    operateCommand = new OperateCommand();

    Controls.robotInit();

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
    Timer.delay(2);
    Globals.getDrivetrain().setNeutralMode(NeutralMode.Coast);
  }
  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  PathGroupCommand pGroup;
  @Override
  public void autonomousInit() {
    Globals.getDrivetrain().setNeutralMode(NeutralMode.Brake);

    PathGroupCommand pGroup = new PathGroupCommand();
    pGroup.addSequential(new CommandTillVision(new PathCommand(start2BayOne, Globals.getDrivetrain()), new CombinedTarget().setTarget(1.2)));
    //pGroup.addSequential(new CombinedTarget().setTarget(1.1));
    
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
