package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class DriveToSwitchLeft extends CommandGroup {

    public DriveToSwitchLeft(DriveTrainSubsystem driveTrainSubsystem, ManipulatorsSubsystem manipulatorsSubsystem) {
        addSequential(new DriveForward(driveTrainSubsystem, 3));
        addSequential(new SpinCommand(driveTrainSubsystem, 90));
        addParallel(new RaiseLiftCommand(manipulatorsSubsystem, 2));
        addSequential(new DriveForward(driveTrainSubsystem, 2));
    }

}