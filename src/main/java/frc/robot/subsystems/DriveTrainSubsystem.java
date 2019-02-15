package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainSubsystem extends Subsystem {

    private static final int SENSOR_UNITS_PER_ROTATION = 4096;
    private static final double WHEEL_DIAMETER_INCHES = 6d;
    private static final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * Math.PI;

    private final WPI_TalonSRX leftMaster = new WPI_TalonSRX(2);
    private final WPI_VictorSPX leftSlave = new WPI_VictorSPX(0); // Victor
    private final WPI_TalonSRX rightMaster = new WPI_TalonSRX(3);
    private final WPI_VictorSPX rightSlave = new WPI_VictorSPX(1); // Victor
    private final DifferentialDrive driveTrain;

    public DriveTrainSubsystem() {
        TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
        talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        talonConfig.neutralDeadband =  0.001;
        talonConfig.slot0.kF = 1023.0/6800.0;
        talonConfig.slot0.kP = 1.0;
        talonConfig.slot0.kI = 0.0;
        talonConfig.slot0.kD = 0.0;
        talonConfig.slot0.integralZone = 400;
        talonConfig.slot0.closedLoopPeakOutput = 1.0;

        rightMaster.configAllSettings(talonConfig);
        leftMaster.configAllSettings(talonConfig);

        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        rightMaster.setInverted(true);
        rightSlave.setInverted(true);
        rightMaster.setSensorPhase(true);
        leftMaster.setSensorPhase(true);

        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);
        
        driveTrain = new DifferentialDrive(leftMaster, rightMaster);
        driveTrain.setRightSideInverted(false);
    }

    public void initDefaultCommand() {
        
    }

    public DifferentialDrive getDiffDrive() {
        return this.driveTrain;
    }

    public void setNeutralMode(NeutralMode neutralMode) {
        leftMaster.setNeutralMode(neutralMode);
        leftSlave.setNeutralMode(neutralMode);
        rightMaster.setNeutralMode(neutralMode);
        rightSlave.setNeutralMode(neutralMode);
    }

    public int getLeftEncoderPosition() {
        return leftMaster.getSelectedSensorPosition(0);
    }

    public int getRightEncoderPosition() {
        return rightMaster.getSelectedSensorPosition(0);
    }

    public void setUseDifferentialDrive(boolean useDifferentialDrive) {
        driveTrain.setSafetyEnabled(useDifferentialDrive);
        if (!useDifferentialDrive) {
            leftSlave.follow(leftMaster);
            rightSlave.follow(rightMaster);
        }
    }

    public WPI_TalonSRX getLeftTalonSRX() {
        return leftMaster;
    }

    public WPI_TalonSRX getRightTalonSRX() {
        return rightMaster;
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
