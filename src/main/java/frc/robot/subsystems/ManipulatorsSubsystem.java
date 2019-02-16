package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ManipulatorsSubsystem extends Subsystem {

    private Spark intake = new Spark(1);
    private WPI_TalonSRX pivot = new WPI_TalonSRX(4);

    public ManipulatorsSubsystem() {
        TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
        talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        talonConfig.neutralDeadband =  0.001;
        talonConfig.slot0.kF = 1;
        talonConfig.slot0.kP = .5;
        talonConfig.slot0.kI = .0;
        talonConfig.slot0.kD = 0;
        talonConfig.slot0.closedLoopPeakOutput = 1;

        pivot.configAllSettings(talonConfig);

        pivot.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        pivot.setSensorPhase(false);
        pivot.setNeutralMode(NeutralMode.Brake);
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


    private boolean firstRun = true;
    public void setPivotPosition(PivotPosition position) {
        System.out.println("Moving arm to position: " + position.getPosition());
        // pivot.set(ControlMode.Position, ((45 * position.getPosition()) / 512));
        switch(position){
            case START:
                pivot.set(ControlMode.PercentOutput, 0.0);
                if(getPivotPositon() < 100){
                    pivot.set(ControlMode.PercentOutput, 0.2);
                    System.out.println("DO THTE THBINGSONEIN");
                } else if (getPivotPositon() < 200){
                    pivot.set(ControlMode.PercentOutput, 0.1);
                }
                break;
            case UNLOCK_HATCH:
                pivot.set(ControlMode.PercentOutput, 0.0);
                break;
            default:
                pivot.set(ControlMode.Position, position.getPosition());
                break;
        }
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