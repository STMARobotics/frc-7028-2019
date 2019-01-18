package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveCommand;
import frc.robot.drivesystems.Driver;

public class DriveTrainSubsystem extends Subsystem {

    private static final int SENSOR_UNITS_PER_ROTATION = 4096;
    private static final double WHEEL_DIAMETER_INCHES = 6d;
    private static final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * Math.PI;

    private final WPI_TalonSRX leftFront = new WPI_TalonSRX(0);
    private final WPI_TalonSRX leftBack = new WPI_TalonSRX(1);
    private final WPI_TalonSRX rightFront = new WPI_TalonSRX(2);
    private final WPI_TalonSRX rightBack = new WPI_TalonSRX(3);
    private final DifferentialDrive driveTrain;

    private SendableChooser<Driver> driverChooser;

    public DriveTrainSubsystem(SendableChooser<Driver> driverChooser) {
        TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
        talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        talonConfig.neutralDeadband =  0.001;
        talonConfig.slot0.kF = 1023.0/6800.0;
        talonConfig.slot0.kP = 1.0;
        talonConfig.slot0.kI = 0.0;
        talonConfig.slot0.kD = 0.0;
        talonConfig.slot0.integralZone = 400;
        talonConfig.slot0.closedLoopPeakOutput = 1.0;

        rightFront.configAllSettings(talonConfig);
        leftFront.configAllSettings(talonConfig);

        leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        rightFront.setInverted(true);
        rightBack.setInverted(true);
        rightFront.setSensorPhase(true);
        leftFront.setSensorPhase(true);

        SpeedControllerGroup leftSpeedGroup =  new SpeedControllerGroup(leftFront, leftBack);
        SpeedControllerGroup rightSpeedGroup = new SpeedControllerGroup(rightFront, rightBack);
        rightSpeedGroup.setInverted(true);

        driveTrain = new DifferentialDrive(leftSpeedGroup, rightSpeedGroup);

        this.driverChooser = driverChooser;

        SmartDashboard.putData("Left Master", leftFront);
        SmartDashboard.putData("Left Slave", leftBack);

        SmartDashboard.putData("Right Master", rightFront);
        SmartDashboard.putData("Right Slave", rightBack);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new DriveCommand(driverChooser, this));
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

    public void setUseDifferentialDrive(boolean useDifferentialDrive) {
        driveTrain.setSafetyEnabled(useDifferentialDrive);
        if (!useDifferentialDrive) {
            leftBack.follow(leftFront);
            rightBack.follow(rightFront);
        }
    }

    public WPI_TalonSRX getLeftTalonSRX() {
        return leftFront;
    }

    public WPI_TalonSRX getRightTalonSRX() {
        return rightFront;
    }

    /**
     * Converts inches to wheel revolutions
     * @param inches inches
     * @return wheel revolutions
     */
    public static double insToRevs(double inches) {
        return inches / WHEEL_CIRCUMFERENCE_INCHES; 
    }

    /**
     * Converts inches to encoder steps
     * @param inches inches
     * @return encoder steps
     */
    public static double insToSteps(double inches) {
        return (insToRevs(inches) * SENSOR_UNITS_PER_ROTATION);
    }

    /**
     * Converts a velocity in Inches/Second to Encoder Steps per Decisecond
     * @param inchesPerSec velocity in inces per second
     * @return velocity in encoder units per decisecond (100 ms)
     */
    public static double insPerSecToStepsPerDecisec(double inchesPerSec) {
        return insToSteps(inchesPerSec) * .1;
    }

}
