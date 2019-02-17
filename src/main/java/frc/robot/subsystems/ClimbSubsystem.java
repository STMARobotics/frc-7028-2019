package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.drivesystems.operator.Operator;

public class ClimbSubsystem extends Subsystem {

    private DoubleSolenoid climbGuides = new DoubleSolenoid(0, 1);
    private Compressor compressor = new Compressor(0);
    private Spark rack = new Spark(0);
    private Spark climbWheel = new Spark(2);
    private SendableChooser<Operator> operatorChooser;

    public ClimbSubsystem(SendableChooser<Operator> operatorChooser) {
        this.operatorChooser = operatorChooser;
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

    @Override
    protected void initDefaultCommand() {

    }

}
