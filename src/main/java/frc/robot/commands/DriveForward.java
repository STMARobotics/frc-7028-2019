package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveForward extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private double time;
    private Timer timer;

    public DriveForward(DriveTrainSubsystem driveTrainSubsystem, double time) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.time = time;
    }

    protected void initialize() {
        this.timer = new Timer();
    }

    protected void execute() {
        driveTrainSubsystem.getDriveTrain().arcadeDrive(.5, 0);;
    }

    protected boolean isFinished() {
        double currentTime = timer.get();
        return currentTime >= time;
    }

    protected void end() {
        timer.stop();
    }

    protected void interrupted() {

    }

}