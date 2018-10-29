package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.DriveTrainSubDefaultCommand;

public class DriveTrainSubsystem extends Subsystem {

    private final WPI_TalonSRX leftFront = new WPI_TalonSRX(0);
    private final WPI_TalonSRX leftBack = new WPI_TalonSRX(1);
    private final WPI_TalonSRX rightFront = new WPI_TalonSRX(2);
    private final WPI_TalonSRX rightBack = new WPI_TalonSRX(3);
    private final SpeedControllerGroup leftDriveTrain = new SpeedControllerGroup(leftFront, leftBack);
    private final SpeedControllerGroup rightDriveTrain = new SpeedControllerGroup(rightFront, rightBack);
    private final DifferentialDrive driveTrain = new DifferentialDrive(leftDriveTrain, rightDriveTrain);

    public DriveTrainSubsystem() {
        leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        leftFront.setSensorPhase(true);
        leftFront.setSafetyEnabled(true);
        leftBack.setSafetyEnabled(true);
        leftBack.follow(leftFront);

        rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        rightFront.setSafetyEnabled(true);
        rightBack.setSafetyEnabled(true);
        rightBack.follow(rightFront);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveTrainSubDefaultCommand(this));
    }

    public DifferentialDrive getDriveTrain() {
        return this.driveTrain;
    }

    public void setNeutralMode(NeutralMode neutralMode) {
        leftFront.setNeutralMode(neutralMode);
        leftBack.setNeutralMode(neutralMode);
        rightFront.setNeutralMode(neutralMode);
        rightBack.setNeutralMode(neutralMode);
    }

    public int getLeftEncoderPosition() {
        return leftFront.getSelectedSensorPosition(0);
    }

    public int getRightEncoderPosition() {
        return rightFront.getSelectedSensorPosition(0);
    }

}