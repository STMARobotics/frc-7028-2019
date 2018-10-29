package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

/**
 * DriveTrainSubDefaultCommand
 * 
 * continuously tell drivetrain to stay idle
 */
public class DriveTrainSubDefaultCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;

    public DriveTrainSubDefaultCommand(DriveTrainSubsystem driveTrainSubsystem) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
    }

    public void execute() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(0, 0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}