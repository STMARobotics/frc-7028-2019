package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ManipulatorsSubsystem extends Subsystem {

    private final Spark lift = new Spark(0);
    private final Spark intake = new Spark(1);

    public void initDefaultCommand() {

    }

    public void setLiftSpeed(double speed) {
        lift.set(speed);
    }

    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }

}