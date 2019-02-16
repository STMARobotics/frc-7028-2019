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
        // manipulatorsSubsystem.setPivotSpeed(getPivotSpeed());
        climbSubsystem.setRackSpeed(getRackSpeed());
        climbSubsystem.setClimbWheelSpeed(getClimbWheelSpeed());
        climbSubsystem.dropClimbGuides();
        if (joystick.getRawButtonPressed(5)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.LOCK_HATCH);
        } else if (joystick.getRawButtonPressed(3)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.UNLOCK_HATCH);
        } else if (joystick.getRawButtonPressed(6)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.SHUTTLE_CARGO);
        } else if (joystick.getRawButtonPressed(4)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.ROCKET_CARGO);
        } else if (joystick.getRawButtonPressed(12)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.CLIMB);
        }
    }

    private double getIntakeSpeed() {
        boolean intake = joystick.getRawButton(2);
        boolean output = joystick.getTrigger();
        if (intake && !output) {
            return .25;
        } else if (output && !intake) {
            return -1;
        }
        return 0;
    }

    // private double getPivotSpeed() {
    //     return -joystick.getY() * .75;
    // }

    private double getRackSpeed() {
        boolean up = joystick.getRawButton(8);
        boolean down = joystick.getRawButton(7);
        if (up && !down) {
            return 1;
        } else if (down && !up) {
            return -1;
        }
        return 0;
    }

    private double getClimbWheelSpeed() {
        return joystick.getX();
    }

    public boolean getDropKeyPressed() {
        return joystick.getRawButton(11);
    }

}