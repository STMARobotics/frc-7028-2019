package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class SpinCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private float degrees;
    private float target;
    private float startPosition;

    public SpinCommand(DriveTrainSubsystem driveTrainSubsystem, float degrees) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.degrees = degrees;
    }

    protected void initialize() {
        startPosition = driveTrainSubsystem.getGyroPosition();
        float currentPosition = startPosition + 180;
        target = ((currentPosition + degrees) % 360f) - 180;
    }

    protected void execute() {
        double speed = .5d;
        if (isInRange(15)) {
            speed = .35d;
        }
        if (degrees > 0) {
            driveTrainSubsystem.getDriveTrain().arcadeDrive(0, speed);
        } else {
            driveTrainSubsystem.getDriveTrain().arcadeDrive(0, -speed);
        }
    }

    protected boolean isFinished() {
        System.out.println("Start Position: " + startPosition);
        System.out.println("Degrees: " + degrees);
        System.out.println("Target: " + target);
        System.out.println("Gyro Position: " + driveTrainSubsystem.getGyroPosition());
        return isInRange(1);
    }

    private boolean isInRange(double diff) {
        double gyroPosition = driveTrainSubsystem.getGyroPosition();
        return target - diff <= gyroPosition && gyroPosition <= target + diff;
    }

    protected void end() {
        System.out.println("End: " + driveTrainSubsystem.getGyroPosition());;
        driveTrainSubsystem.getDriveTrain().arcadeDrive(0, 0);
        System.out.println("End 2: " + driveTrainSubsystem.getGyroPosition());
    }

    protected void interrupted() {

    }

}