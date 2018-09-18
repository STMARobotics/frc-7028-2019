package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;

public class SpinCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private double degrees;
    private Timer timer = new Timer();

    public SpinCommand(DriveTrainSubsystem driveTrainSubsystem, double degrees) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
    }

    protected void initialize() {
        
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }

    protected void interrupted() {

    }

}