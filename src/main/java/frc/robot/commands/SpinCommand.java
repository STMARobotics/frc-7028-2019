package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;

public class SpinCommand extends PIDCommand {

    private DriveTrainSubsystem driveTrainSubsystem;
    private GyroSubsystem gyroSubsystem;
    private double degrees;
    private double target;

    public SpinCommand(DriveTrainSubsystem driveTrainSubsystem, GyroSubsystem gyroSubsystem, float degrees) {
        super(0.02, 0, 0);
        requires(driveTrainSubsystem);
        requires(gyroSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.degrees = degrees;
    }

    protected void initialize() {
        target = gyroSubsystem.getGyroPosition() + degrees;
        this.setSetpoint(target);
    }

    protected void execute() {
        
    }

    protected boolean isFinished() {
        return !gyroSubsystem.getIsRotating() && isInRange(1);
    }

    private boolean isInRange(double diff) {
        double gyroPosition = gyroSubsystem.getGyroPosition();
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
        return gyroSubsystem.getGyroPosition();
    }

}