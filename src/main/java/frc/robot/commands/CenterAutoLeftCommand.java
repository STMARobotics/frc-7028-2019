package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class CenterAutoLeftCommand extends CommandGroup {

    public CenterAutoLeftCommand(DriveTrainSubsystem driveTrainSubsystem, ManipulatorsSubsystem manipulatorsSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .5, 36));
        addSequential(new SpinCommand(driveTrainSubsystem, -90));
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .5, 78));
        addParallel(new RaiseLiftCommand(manipulatorsSubsystem));
        addSequential(new SpinCommand(driveTrainSubsystem, 90));
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .5, 61));
        addSequential(new OperateIntakeCommand(manipulatorsSubsystem, .5, 1));
    }

}