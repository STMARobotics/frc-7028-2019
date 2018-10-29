package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;

public class LeftAutoNoSwitchCommand extends CommandGroup {

    public LeftAutoNoSwitchCommand(DriveTrainSubsystem driveTrainSubsystem, GyroSubsystem gyroSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, gyroSubsystem, .5, 145));
    }

}