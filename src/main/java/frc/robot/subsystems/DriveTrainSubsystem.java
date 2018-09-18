package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainSubsystem extends Subsystem {

    private final WPI_TalonSRX leftFront = new WPI_TalonSRX(0);
    private final WPI_TalonSRX leftBack = new WPI_TalonSRX(1);
    private final WPI_TalonSRX rightFront = new WPI_TalonSRX(2);
    private final WPI_TalonSRX rightBack = new WPI_TalonSRX(3);
    private final SpeedControllerGroup leftDriveTrain = new SpeedControllerGroup(leftFront, leftBack);
    private final SpeedControllerGroup rightDriveTrain = new SpeedControllerGroup(rightFront, rightBack);
    private final DifferentialDrive driveTrain = new DifferentialDrive(leftDriveTrain, rightDriveTrain);

    public void initDefaultCommand() {

    }

    public DifferentialDrive getDriveTrain() {
        return this.driveTrain;
    }

}