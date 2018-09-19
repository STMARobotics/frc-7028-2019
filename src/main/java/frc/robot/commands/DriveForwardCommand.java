package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveForwardCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private double time;
    private double speed;
    private Timer timer;

    public DriveForwardCommand(DriveTrainSubsystem driveTrainSubsystem, double speed, double time) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.time = time;
        this.speed = speed;
    }

    protected void initialize() {
        this.timer = new Timer();
    }

    protected void execute() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(-speed, 0);;
    }

    protected boolean isFinished() {
        double currentTime = timer.get();
        return currentTime >= time;
    }

    protected void end() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(0, 0);
        timer.stop();
    }

    protected void interrupted() {

    }

}