package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.subsystems.GyroSubsystem;

public class PointCommand extends PIDCommand{


    double degrees;
    public PointCommand(double degrees){
        super(0.005, 0.0003, 0);
        this.degrees = degrees;
    }

    @Override
    protected void end() {
        Robot.driveTrainSubsystem.getDriveTrain().arcadeDrive(0, 0);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(degrees);
    }

    @Override
    protected double returnPIDInput() {
        return GyroSubsystem.getGyroPosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.driveTrainSubsystem.getDriveTrain().arcadeDrive(0, output);;
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(GyroSubsystem.getGyroPosition()) < 0.5;
    }




}