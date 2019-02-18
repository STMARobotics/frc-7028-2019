package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.drivesystems.driver.Driver;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveCommand extends Command {

    private DriveTrainSubsystem driveTrainSubsystem;
    private SendableChooser<Driver> driverChooser;

    public DriveCommand(DriveTrainSubsystem driveTrainSubsystem, SendableChooser<Driver> driverChooser) {
        this.driveTrainSubsystem = driveTrainSubsystem;
        this.driverChooser = driverChooser;
        requires(driveTrainSubsystem);
    }

    protected void initialize() {
        driveTrainSubsystem.setNeutralMode(NeutralMode.Brake);
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