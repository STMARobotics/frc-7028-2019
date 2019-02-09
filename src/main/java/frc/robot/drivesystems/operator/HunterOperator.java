package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;

public class HunterOperator implements Operator {

    private Joystick joystick;

    public HunterOperator(Joystick joystick) {
        this.joystick = joystick;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem) {
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
        manipulatorsSubsystem.setPivotSpeed(getPivotSpeed());
        if (joystick.getRawButtonPressed(5)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.LOCK_HATCH);
        } else if (joystick.getRawButtonPressed(3)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.UNLOCK_HATCH);
        } else if (joystick.getRawButtonPressed(6)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.SHUTTLE_CARGO);
        } else if (joystick.getRawButtonPressed(4)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.ROCKET_CARGO);
        }
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