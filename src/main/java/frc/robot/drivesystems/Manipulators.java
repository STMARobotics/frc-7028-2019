package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Manipulators {

    private Spark lift;
    private SpeedControllerGroup intake;

    public Manipulators(Spark lift, SpeedControllerGroup intake) {
        this.lift = lift;
        this.intake = intake;
    }

    public void setLiftSpeed(double speed) {
        lift.set(speed);
    }

    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }

}