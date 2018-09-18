package frc.robot.commands;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveToSwitchRight extends Command {

    private DifferentialDrive driveTrain;
    private Spark lift;
    private Timer timer;

    public DriveToSwitchRight(Subsystem subsystem, DifferentialDrive driveTrain, Spark lift) {
        requires(subsystem);
        this.driveTrain = driveTrain;
        this.lift = lift;
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