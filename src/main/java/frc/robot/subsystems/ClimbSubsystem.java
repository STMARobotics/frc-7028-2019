package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.drivesystems.driver.Driver;
import frc.robot.drivesystems.operator.Operator;

public class ClimbSubsystem extends Subsystem {

    private DoubleSolenoid climbGuides = new DoubleSolenoid(0,1);
    private Compressor compressor = new Compressor(0);
    private Spark rack = new Spark(0);
    private Spark climbWheel = new Spark(2);
    private SendableChooser<Operator> operatorChooser;
    private SendableChooser<Driver> driverChooser;

    public ClimbSubsystem(SendableChooser<Operator> operatorChooser, SendableChooser<Driver> driverChooser) {
        this.operatorChooser = operatorChooser;
        this.driverChooser = driverChooser;
        compressor.setClosedLoopControl(true);
    }

    public void setRackSpeed(double speed) {
        rack.set(speed);
    }

    public void setClimbWheelSpeed(double speed) {
        climbWheel.set(speed);
    }

    public void dropClimbGuides() {
        if (driverChooser.getSelected().getDropKeyPressed() && operatorChooser.getSelected().getDropKeyPressed()) {
            climbGuides.set(Value.kForward);
            System.out.println("ClimbGuidesDropped");
        }
    }

    public void resetClimbGuides() {
        climbGuides.set(Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {

    }

}