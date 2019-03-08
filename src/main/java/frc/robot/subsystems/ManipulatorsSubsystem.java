package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
        talonConfig.nominalOutputForward = .3;
        talonConfig.nominalOutputReverse = .3;
        talonConfig.clearPositionOnLimitF = true;

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
     * sets pivot power output percent
     * @param speed - is up + is down
     */
    public void setPivotOutputPercent(double speed) {
        pivot.set(speed);
    }

    public void setVelocity(double speed) {
        pivot.set(ControlMode.Velocity, speed);
    }

    public void setPivotPosition(PivotPosition position) {
        SmartDashboard.putString("Arm position", position.name());
        switch(position){
            case REST:
                pivot.set(ControlMode.PercentOutput, 0.0);
                break;
            case UNLOCK_HATCH:
                System.out.println("Dropping");
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

    public boolean isPivotAtBottomLimit() {
        return pivot.getSensorCollection().isFwdLimitSwitchClosed();
    }

    public boolean isPivotAtTopLimit() {
        return pivot.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void initDefaultCommand() {
        
    }
}
