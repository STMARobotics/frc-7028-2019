package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class CenterAutoRightCommand extends CommandGroup {

    public CenterAutoRightCommand(DriveTrainSubsystem driveTrainSubsystem, ManipulatorsSubsystem manipulatorsSubsystem, GyroSubsystem gyroSubsystem) {
        addSequential(new DriveForwardCommand(driveTrainSubsystem, gyroSubsystem, .5, 36));
        addSequential(new SpinCommand(driveTrainSubsystem, gyroSubsystem, 90));
        addSequential(new DriveForwardCommand(driveTrainSubsystem, gyroSubsystem, .5, 30));
        addParallel(new RaiseLiftCommand(manipulatorsSubsystem));
        addSequential(new SpinCommand(driveTrainSubsystem, gyroSubsystem, -90));
        addSequential(new DriveForwardCommand(driveTrainSubsystem, gyroSubsystem, .5, 61, 5));
        addSequential(new OperateIntakeCommand(manipulatorsSubsystem, .5, 1));
    }

}