package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;

public class DriveForwardCommand extends PIDCommand {

    private DriveTrainSubsystem driveTrainSubsystem;
    private GyroSubsystem gyroSubsystem;
    private double distance;
    private double rotations;
    private double speed;
    private double leftTarget;
    private double rightTarget;
    private double timeout;
    private Timer timer = new Timer();

    public DriveForwardCommand(DriveTrainSubsystem driveTrainSubsystem, double speed, double distance, GyroSubsystem gyroSubsystem) {
        this(driveTrainSubsystem, speed, distance, gyroSubsystem, 0.0);
    }

    public DriveForwardCommand(DriveTrainSubsystem driveTrainSubsystem, double speed, double distance, GyroSubsystem gyroSubsystem, double timeout) {
        super(0.04, 0, 0);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.distance = distance;
        this.speed = speed;
        this.timeout = timeout;
        this.gyroSubsystem = gyroSubsystem;
    }

    protected void initialize() {
        rotations = (distance / 18.8495559215) * 4096;
        leftTarget = rotations + driveTrainSubsystem.getLeftEncoderPosition();
        rightTarget = rotations + driveTrainSubsystem.getRightEncoderPosition();
        timer.start();
        this.setSetpoint(gyroSubsystem.getGyroPosition());
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        if (timeout > 0) {
            if (timer.get() >= timeout) {
                return true;
            }
        }
        return driveTrainSubsystem.getLeftEncoderPosition() >= leftTarget && driveTrainSubsystem.getRightEncoderPosition() >= rightTarget;
    }

    protected void end() {
        timer.stop();
        driveTrainSubsystem.getDiffDrive().arcadeDrive(0, 0);
    }

    protected void interrupted() {

    }

    @Override
    protected void usePIDOutput(double output) {
        driveTrainSubsystem.getDiffDrive().arcadeDrive(speed, output);
    }

    @Override
    protected double returnPIDInput() {
        return gyroSubsystem.getGyroPosition();
    }

}