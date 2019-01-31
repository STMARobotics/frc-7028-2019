package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ManipulatorsSubsystem extends Subsystem {

    private Spark intake = new Spark(0);
    private Spark pivot = new Spark(1);

    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }

    public void setPivotSpeed(double speed) {
        pivot.set(speed);
    }

    public int getPivotPositon() {
        return 0;
    }

    public void initDefaultCommand() {
        
    }

}