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
import frc.robot.commands.VisionCommands.AutoTarget;
import frc.robot.commands.VisionCommands.CombinedTarget;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Robot extends TimedRobot {


  private static SendableChooser<Command> autoChooser = new SendableChooser<>();
  private Command driveCommand;
  private Command operateCommand;
  private Command autoCommand;

  @Override
  public void robotInit() {

    driveCommand = new DriveCommand();
    operateCommand = new OperateCommand();

    Controls.robotInit();

    autoChooser.addOption("Follow the thing", new AutoTarget());

    SmartDashboard.putData("Auto Chooser", autoChooser);
  }


  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
    driveCommand.cancel();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    Globals.getDrivetrain().setNeutralMode(NeutralMode.Brake);
    
    System.out.println(autoCommand);
    autoCommand = new CombinedTarget();
    autoCommand.start();
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
