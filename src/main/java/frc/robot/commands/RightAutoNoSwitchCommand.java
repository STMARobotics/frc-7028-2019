package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;

public class RightAutoNoSwitchCommand extends CommandGroup {

    public RightAutoNoSwitchCommand(DriveTrainSubsystem driveTrainSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .5, 120));
    }

}