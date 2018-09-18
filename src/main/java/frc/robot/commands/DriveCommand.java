package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Robot;
import frc.robot.drivesystems.Driver;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private SendableChooser<Driver> driverChooser = Robot.getDriverChooser();

    public DriveCommand(DriveTrainSubsystem driveTrainSubsystem) {
        requires(driveTrainSubsystem);
        this.driveTrainSubsystem = driveTrainSubsystem;
    }

    protected void initialize() {

    }

    protected void execute() {
        Driver driver = driverChooser.getSelected();
        driver.drive(driveTrainSubsystem.getDriveTrain());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {

    }

}