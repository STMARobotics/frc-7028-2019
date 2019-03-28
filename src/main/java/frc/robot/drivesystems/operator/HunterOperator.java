package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;

public class HunterOperator implements Operator {

    private XboxController controlPanel;

    public HunterOperator(XboxController controlPanel) {
        this.controlPanel = controlPanel;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem, ClimbSubsystem climbSubsystem) {
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
        climbSubsystem.setRackSpeed(getRackSpeed());
        climbSubsystem.setClimbWheelSpeed(getClimbWheelSpeed());
        if (controlPanel.getRawButton(11)) {
            climbSubsystem.dropClimbGuides();
        }
        if (controlPanel.getRawButton(12)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.LOCK_HATCH);
        } else if (controlPanel.getRawButton(3)) {
            manipulatorsSubsystem.setVelocity(110);
        } else if (controlPanel.getRawButton(13)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.UNLOCK_HATCH);
        } else if (controlPanel.getRawButton(6)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.SHUTTLE_CARGO);
        } else if (controlPanel.getRawButton(14)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.ROCKET_CARGO);
        } else if (controlPanel.getRawButton(4)) {
            manipulatorsSubsystem.setPivotPosition(PivotPosition.CLIMB);
        }
    }

    private double getIntakeSpeed() {
        boolean intake = controlPanel.getRawButton(9);
        boolean output = controlPanel.getRawButton(7);
        if (intake && !output) {
            return .4;
        } else if (output && !intake) {
            return -1;
        }
        return 0;
    }

    private double getRackSpeed() {
        boolean up = controlPanel.getRawButton(10);
        boolean down = controlPanel.getRawButton(8);
        if (up && !down) {
            return -1;
        } else if (down && !up) {
            return 1;
        }
        return 0;
        //return (controlPanel.getRawButton(12) ? 1 : 0) - (controlPanel.getRawButton(11) ? -1 : 0);
    }

    private double getClimbWheelSpeed() {
        boolean forward = controlPanel.getRawButton(2);
        boolean backward = controlPanel.getRawButton(5);
        if (forward && !backward) {
            return 1;
        } else if (backward && !forward) {
            return -1;
        }
        return 0;
    }

}