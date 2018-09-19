package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class CenterAutoCommand extends CommandGroup {

    public CenterAutoCommand(DriveTrainSubsystem driveTrainSubsystem, ManipulatorsSubsystem manipulatorsSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .25, 2.5));
    }

}