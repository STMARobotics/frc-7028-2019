package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimbSubsystem extends Subsystem {

    private DoubleSolenoid climbGuides = new DoubleSolenoid(0, 1);
    private Compressor compressor = new Compressor(0);
    private WPI_TalonSRX rack = new WPI_TalonSRX(5);
    private Spark climbWheel = new Spark(2);

    public ClimbSubsystem() {
        TalonSRXConfiguration talonConfig = new TalonSRXConfiguration();
        talonConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.CTRE_MagEncoder_Relative;
        talonConfig.neutralDeadband =  0.001;

        // TODO make sure reverse is retracted
        talonConfig.clearPositionOnLimitR = true;
        talonConfig.forwardSoftLimitEnable = true;
        // TODO what is the correct soft limit value?
        talonConfig.forwardSoftLimitThreshold = 1000000;

        rack.configAllSettings(talonConfig);
        compressor.setClosedLoopControl(true);
    }

    public void setRackSpeed(double speed) {
        rack.set(speed);
    }

    public void setClimbWheelSpeed(double speed) {
        climbWheel.set(speed);
    }

    public void dropClimbGuides() {
        climbGuides.set(Value.kForward);
    }

    public void resetClimbGuides() {
        climbGuides.set(Value.kReverse);
    }

    public int getRackPosition() {
        return rack.getSelectedSensorPosition();
    }

    public boolean isRackRetracted() {
        // TODO Make sure reverse is retracted
        return rack.getSensorCollection().isRevLimitSwitchClosed();
    }

    @Override
    protected void initDefaultCommand() {

    }

}
