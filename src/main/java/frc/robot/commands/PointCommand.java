package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Globals;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.vision.Limelight;

public class PointCommand extends PIDCommand{

    private DriveTrainSubsystem driveTrain = Globals.getDrivetrain();
    private Limelight limelight = Globals.getLimelight();
    private GyroSubsystem gyro = Globals.getGyro();

    double degrees;
    public PointCommand(double degrees){
        super(0.005, 0.0003, 0);
        this.degrees = degrees;
    }

    public PointCommand(DriveTrainSubsystem driveTrain, GyroSubsystem gyro, Limelight limelight, double degrees){
        this(degrees);
        this.driveTrain = driveTrain;
        this.limelight = limelight;
        this.gyro = gyro;
    }

    @Override
    protected void end() {
        Globals.getDrivetrain().getDiffDrive().arcadeDrive(0, 0);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(degrees);
    }

    @Override
    protected double returnPIDInput() {
        return gyro.getGyroPosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        driveTrain.getDiffDrive().arcadeDrive(0, output);;
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(gyro.getGyroPosition()) < 0.5;
    }




}