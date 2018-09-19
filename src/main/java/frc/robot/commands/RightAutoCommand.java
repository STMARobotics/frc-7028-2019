package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class RightAutoCommand extends CommandGroup {

    public RightAutoCommand(DriveTrainSubsystem driveTrainSubsystem, ManipulatorsSubsystem manipulatorsSubsystem, char switchPosition) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .25, 3));
        if (switchPosition == 'R') {
            addSequential(new SpinCommand(driveTrainSubsystem, -90));
            addParallel(new RaiseLiftCommand(manipulatorsSubsystem, 1, 1.5));
            addSequential(new DriveForwardCommand(driveTrainSubsystem, .25, 2));
            addSequential(new OperateIntakeCommand(manipulatorsSubsystem, .1, 1));
        }
    }

}