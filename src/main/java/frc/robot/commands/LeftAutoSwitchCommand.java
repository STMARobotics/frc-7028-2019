package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class LeftAutoSwitchCommand extends CommandGroup {

    public LeftAutoSwitchCommand(DriveTrainSubsystem driveTrainSubsystem, ManipulatorsSubsystem manipulatorsSubsystem, GyroSubsystem gyroSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, gyroSubsystem, .5, 145));
        addSequential(new WaitCommand(.5));
        addParallel(new RaiseLiftCommand(manipulatorsSubsystem));
        addSequential(new SpinCommand(driveTrainSubsystem, gyroSubsystem, 90));
        addSequential(new DriveForwardCommand(driveTrainSubsystem, gyroSubsystem, .5, 18, 3));
        addSequential(new OperateIntakeCommand(manipulatorsSubsystem, .5, 1));
    }

}