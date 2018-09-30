package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;

public class LeftAutoNoSwitchCommand extends CommandGroup {

    public LeftAutoNoSwitchCommand(DriveTrainSubsystem driveTrainSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, .5, 120));
    }

}