package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ManipulatorsSubsystem extends Subsystem {

    private Spark intake = new Spark(1);
    private WPI_TalonSRX pivot = new WPI_TalonSRX(4);

    public ManipulatorsSubsystem() {
        TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
        talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        talonConfig.neutralDeadband =  0.001;
        talonConfig.slot0.kF = 1.5;
        talonConfig.slot0.kP = .15;
        talonConfig.slot0.kI = 0.0;
        talonConfig.slot0.kD = 0;
        talonConfig.slot0.closedLoopPeakOutput = .6;

        pivot.configAllSettings(talonConfig);

        pivot.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        pivot.setSensorPhase(false);
    }

    /**
     * sets intake speed
     * @param speed + is intake, - is output
     */
    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }

    /**
     * sets pivot speed
     * @param speed - is up + is down
     */
    public void setPivotSpeed(double speed) {
        pivot.set(speed);
    }

    public void setPivotPosition(PivotPosition position) {
        pivot.set(ControlMode.Position, position.getPosition());
    }

    public int getPivotPositon() {
        return pivot.getSelectedSensorPosition();
    }

    public void calibratePivotEncoder() {
        pivot.setSelectedSensorPosition(0);
    }

    public void initDefaultCommand() {
        
    }

}