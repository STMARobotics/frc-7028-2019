package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.drivesystems.operator.Operator;

public class ManipulatorsSubsystem extends Subsystem {

    private Spark intake = new Spark(1);
    private WPI_TalonSRX pivot = new WPI_TalonSRX(4);
    private PowerDistributionPanel pdp;
    // TODO find correct channel for pivot
    private final int PIVOT_CHANNEL = 1;
    // TODO calibrate max draw
    private final double MAX_DRAW = 10;
    private boolean lock = false;
    private SendableChooser<Operator> operatorChooser;

    public ManipulatorsSubsystem(PowerDistributionPanel pdp, SendableChooser<Operator> operatorChooser) {
        this.pdp = pdp;
        this.operatorChooser = operatorChooser;

        TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
        talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        talonConfig.neutralDeadband =  0.001;
        talonConfig.slot0.kF = 1023.0/6800.0;
        talonConfig.slot0.kP = 1.0;
        talonConfig.slot0.kI = 0.0;
        talonConfig.slot0.kD = 0.0;
        talonConfig.slot0.integralZone = 400;
        talonConfig.slot0.closedLoopPeakOutput = .6;

        pivot.configAllSettings(talonConfig);

        pivot.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
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
     * @param speed + is up, - is down
     */
    public void setPivotSpeed(double speed) {
        // double powerDraw = pdp.getCurrent(PIVOT_CHANNEL);
        // if (powerDraw >= MAX_DRAW) {
        //     pivot.set(0);
        //     lock = true;
        // } else if (lock) {
        //     pivot.set(0);
        // } else if (lock && speed == 0) {
        //     pivot.set(0);
        //     lock = false;
        // } else {
            pivot.set(speed);
        // }
    }

    public void setPivotPosition(PivotPosition position) {
        pivot.set(ControlMode.MotionMagic, position.getPosition());
    }

    public int getPivotPositon() {
        return pivot.getSelectedSensorPosition();
    }

    public void initDefaultCommand() {
        
    }

}