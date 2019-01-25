package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Controls;
import frc.robot.Globals;
import frc.robot.drivesystems.driver.Driver;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private SendableChooser<Driver> driverChooser;

    public DriveCommand() {
        this(Globals.getDrivetrain());
    }

    public DriveCommand(DriveTrainSubsystem driveTrain){
        this.driveTrainSubsystem = driveTrain;
        this.driverChooser = Controls.driverChooser;
        requires(driveTrainSubsystem);
    }

    protected void initialize() {

    }

    protected void execute() {
        Driver driver = driverChooser.getSelected();
        driver.drive(driveTrainSubsystem.getDiffDrive());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    protected void interrupted() {

    }

}