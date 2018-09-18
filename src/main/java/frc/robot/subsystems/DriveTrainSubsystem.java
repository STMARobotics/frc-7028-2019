package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainSubsystem extends Subsystem {

    private final Talon leftFront = new Talon(0);
    private final Talon leftBack = new Talon(1);
    private final Talon rightFront = new Talon(2);
    private final Talon rightBack = new Talon(3);
    private final SpeedControllerGroup leftDriveTrain = new SpeedControllerGroup(leftFront, leftBack);
    private final SpeedControllerGroup rightDriveTrain = new SpeedControllerGroup(rightFront, rightBack);
    private final DifferentialDrive driveTrain = new DifferentialDrive(leftDriveTrain, rightDriveTrain);

    public void initDefaultCommand() {

    }

    public DifferentialDrive getDriveTrain() {
        return this.driveTrain;
    }

}