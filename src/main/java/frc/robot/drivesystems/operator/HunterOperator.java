package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;

public class HunterOperator implements Operator {

    private Joystick joystick;

    public HunterOperator(Joystick joystick) {
        this.joystick = joystick;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem, ClimbSubsystem climbSubsystem) {
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
        climbSubsystem.setRackSpeed(getRackSpeed());
        climbSubsystem.setClimbWheelSpeed(getClimbWheelSpeed());
        climbSubsystem.dropClimbGuides();
        if (joystick.getRawButtonPressed(5)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.LOCK_HATCH);
        } else if (joystick.getRawButtonPressed(3)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.UNLOCK_HATCH);
        } else if (joystick.getRawButtonPressed(4)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.SHUTTLE_CARGO);
        } else if (joystick.getRawButtonPressed(6)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.ROCKET_CARGO);
        } else if (joystick.getRawButtonPressed(12)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.CLIMB);
        }
    }

    private double getIntakeSpeed() {
        boolean intake = joystick.getRawButton(2);
        boolean output = joystick.getTrigger();
        if (intake && !output) {
            return .3;
        } else if (output && !intake) {
            return -1;
        }
        return 0;
    }

    private double getRackSpeed() {
        int speed = joystick.getPOV();
        if (speed == 8 || speed == 0 || speed == 1) {
            return 1;
        } else if (speed == 3 || speed == 4 || speed == 5) {
            return -1;
        }
        return 0;
    }

    private double getClimbWheelSpeed() {
        return -joystick.getY();
    }

    public boolean getDropKeyPressed() {
        return joystick.getRawButton(11);
    }

}