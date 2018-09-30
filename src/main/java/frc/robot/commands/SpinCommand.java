package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrainSubsystem;

public class SpinCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private float degrees;
    private float target;

    public SpinCommand(DriveTrainSubsystem driveTrainSubsystem, float degrees) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.degrees = degrees;
    }

    protected void initialize() {
        target = driveTrainSubsystem.getGyroPosition() + degrees;
        if (target < -180) {
            target = target + 360;
        } else if (target > 180) {
            target = target - 360;
        }
    }

    protected void execute() {
        if (degrees > 0) {
            driveTrainSubsystem.getDriveTrain().arcadeDrive(0, .5);
        } else {
            driveTrainSubsystem.getDriveTrain().arcadeDrive(0, -.5);
        }
    }

    protected boolean isFinished() {
        double gyroPosition = driveTrainSubsystem.getGyroPosition();
        return target - 1 <= gyroPosition && gyroPosition <= target + 1;
    }

    protected void end() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(0, 0);
    }

    protected void interrupted() {

    }

}