package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;

public class PointCommand extends Command {

    private DriveTrainSubsystem driveTrain;
    private GyroSubsystem gyro;
    private double degrees;

    public PointCommand(DriveTrainSubsystem driveTrain, GyroSubsystem gyro, double degrees) {
        this.driveTrain = driveTrain;
        this.gyro = gyro;
        requires(driveTrain);
        requires(gyro);
    }

    @Override
    protected void execute() {
        double off = degrees - gyro.getGyroPosition();
        double speed = Math.abs((2 / (1 + Math.pow(1.1, off))) - 1);
        // Google abs(​2/​(1+​1.1^​x)-​1) for the graph
        driveTrain.getDiffDrive().arcadeDrive(0, speed, false);
    }

    @Override
    protected void end() {
        driveTrain.getDiffDrive().arcadeDrive(0, 0);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(gyro.getGyroPosition()) < 0.5;
    }
}
