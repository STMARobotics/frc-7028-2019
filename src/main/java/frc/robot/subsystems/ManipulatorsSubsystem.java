package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ManipulatorsSubsystem extends Subsystem {

    private final Spark lift = new Spark(0);
    private final DigitalInput topLimitSwitch = new DigitalInput(0);
    private final DigitalInput bottomLimitSwitch = new DigitalInput(1);
    private final Spark leftIntake = new Spark(1);
    private final Spark rightIntake = new Spark(2);
    private final SpeedControllerGroup intake = new SpeedControllerGroup(leftIntake, rightIntake);

    public ManipulatorsSubsystem() {
        leftIntake.setInverted(true);
    }

    public void initDefaultCommand() {
        
    }

    public void setLiftSpeed(double speed) {
        lift.set(speed);
    }

    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }

    public boolean getTopLimitSwitch() {
        return topLimitSwitch.get();
    }

    public boolean getBottomLimitSwitch() {
        return bottomLimitSwitch.get();
    }

}