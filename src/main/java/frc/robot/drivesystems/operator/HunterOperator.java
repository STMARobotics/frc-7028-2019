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
        if (joystick.getRawButton(8)) {
            climbSubsystem.dropClimbGuides();
        }
        if (joystick.getRawButton(5)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.LOCK_HATCH);
        } else if (joystick.getRawButton(3)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.UNLOCK_HATCH);
        } else if (joystick.getRawButton(6)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.SHUTTLE_CARGO);
        } else if (joystick.getRawButton(4)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.ROCKET_CARGO);
        } else if (joystick.getRawButton(7)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.CLIMB);
        } else if (joystick.getRawButton(9)){
            //manipulatorsSubsystem.setPivotPosition(PivotPosition.REST);
        }
    }

    private double getIntakeSpeed() {
        boolean intake = joystick.getRawButton(2);
        boolean output = joystick.getTrigger();
        if (intake && !output) {
            return .4;
        } else if (output && !intake) {
            return -1;
        }
        return 0;
    }

    private double getRackSpeed() {
        boolean up = joystick.getRawButton(12);
        boolean down = joystick.getRawButton(11);
        if (up && !down) {
            return -1;
        } else if (down && !up) {
            return 1;
        }
        return 0;
        //return (joystick.getRawButton(12) ? 1 : 0) - (joystick.getRawButton(11) ? -1 : 0);
    }

    private double getClimbWheelSpeed() {
        return -joystick.getY();
    }

}