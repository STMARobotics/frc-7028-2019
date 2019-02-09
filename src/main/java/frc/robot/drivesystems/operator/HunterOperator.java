package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class HunterOperator implements Operator {

    private Joystick joystick;

    public HunterOperator(Joystick joystick) {
        this.joystick = joystick;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
        manipulatorsSubsystem.setPivotSpeed(getPivotSpeed());
    }

    private double getIntakeSpeed() {
        boolean intake = joystick.getRawButton(10);
        boolean output = joystick.getTrigger();
        if (intake && !output) {
            return 1;
        } else if (output && !intake) {
            return -1;
        }
        return 0;
    }

    private double getPivotSpeed() {
        return -joystick.getY();
    }

}