package frc.robot.drivesystems;

import edu.wpi.first.wpilibj.Spark;

public class Manipulators {

    public final Spark lift;
    public final Spark intake;

    public Manipulators(Spark lift, Spark intake) {
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