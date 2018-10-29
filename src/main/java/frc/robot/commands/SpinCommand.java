package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.subsystems.DriveTrainSubsystem;

public class SpinCommand extends PIDCommand {

    private DriveTrainSubsystem driveTrainSubsystem;
    private double degrees;
    private double target;

    public SpinCommand(DriveTrainSubsystem driveTrainSubsystem, float degrees) {
        super(0.02, 0, 0);
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.degrees = degrees;
    }

    protected void initialize() {
        target = driveTrainSubsystem.getGyroPosition() + degrees;
        this.setSetpoint(target);
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return !driveTrainSubsystem.getGyro().isRotating() && isInRange(1);
    }

    private boolean isInRange(double diff) {
        double gyroPosition = driveTrainSubsystem.getGyroPosition();
        double rangeLow = target - diff;
        double rangeHigh = target + diff;
        return rangeLow <= gyroPosition && gyroPosition <= rangeHigh;
    }

    protected void end() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(0, 0);
    }

    protected void interrupted() {

    }
    @Override
    protected void usePIDOutput(double output) {
        if (output > .5) {
            output = .5;
        }
        driveTrainSubsystem.getDriveTrain().arcadeDrive(0, output);
    }

    @Override
    protected double returnPIDInput() {
        return driveTrainSubsystem.getGyroPosition();
    }

}