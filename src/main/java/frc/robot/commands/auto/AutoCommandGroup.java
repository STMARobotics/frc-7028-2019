package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class AutoCommandGroup extends CommandGroup {

    public AutoCommandGroup(ManipulatorsSubsystem manipulatorsSubsystem, ClimbSubsystem climbSubsystem, DriveTrainSubsystem driveTrainSubsystem) {
        requires(manipulatorsSubsystem);
        requires(climbSubsystem);
        requires(driveTrainSubsystem);
    }

}