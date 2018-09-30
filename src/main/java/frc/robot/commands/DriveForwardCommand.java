package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveForwardCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private double distance;
    private double rotations;
    private double speed;
    private double leftTarget;
    private double rightTarget;

    public DriveForwardCommand(DriveTrainSubsystem driveTrainSubsystem, double speed, double distance) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.distance = distance;
        this.speed = speed;
    }

    protected void initialize() {
        rotations = (distance / 18.8495559215) * 4096;
        leftTarget = rotations + driveTrainSubsystem.getLeftEncoderPosition();
        rightTarget = rotations + driveTrainSubsystem.getRightEncoderPosition();
    }

    protected void execute() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(speed, 0);
    }

    protected boolean isFinished() {
        boolean isfinished = driveTrainSubsystem.getLeftEncoderPosition() >= leftTarget 
            && driveTrainSubsystem.getRightEncoderPosition() >= rightTarget;
        return isfinished;
    }

    protected void end() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(0, 0);
    }

    protected void interrupted() {

    }

}