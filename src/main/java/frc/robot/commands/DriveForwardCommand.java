package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveForwardCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private double distance;
    private double rotations;
    private double speed;
    private double leftTarget;
    private double rightTarget;
    private double timeout;
    private Timer timer = new Timer();

    public DriveForwardCommand(DriveTrainSubsystem driveTrainSubsystem, double speed, double distance) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.distance = distance;
        this.speed = speed;
    }

    public DriveForwardCommand(DriveTrainSubsystem driveTrainSubsystem, double speed, double distance, double timeout) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.distance = distance;
        this.speed = speed;
        this.timeout = timeout;
    }

    protected void initialize() {
        rotations = (distance / 18.8495559215) * 4096;
        leftTarget = rotations + driveTrainSubsystem.getLeftEncoderPosition();
        rightTarget = rotations + driveTrainSubsystem.getRightEncoderPosition();
        timer.start();
    }

    protected void execute() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(speed, 0);
    }

    protected boolean isFinished() {
        if (timeout > 0) {
            if (timer.get() >= timeout) {
                return true;
            }
        }
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