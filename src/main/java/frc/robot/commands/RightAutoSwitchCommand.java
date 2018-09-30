package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class RightAutoSwitchCommand extends CommandGroup {

    public RightAutoSwitchCommand(DriveTrainSubsystem driveTrainSubsystem, ManipulatorsSubsystem manipulatorsSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .5, 120));
        addSequential(new WaitCommand(.5));
        addParallel(new RaiseLiftCommand(manipulatorsSubsystem));
        addSequential(new SpinCommand(driveTrainSubsystem, -90));
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .5, 36));
        addSequential(new OperateIntakeCommand(manipulatorsSubsystem, .5, 1));
    }

}