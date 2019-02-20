package frc.robot.drivesystems.operator;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;
import frc.robot.subsystems.PivotPosition;

public class SoloOperator implements Operator {

    private double minTriggerToIntake = 0.05;
    private XboxController controller;
    private boolean isShuttlePos = false;
    private boolean isClimbing = false;

    public SoloOperator(XboxController controller) {
        this.controller = controller;
    }

    public void operate(ManipulatorsSubsystem manipulatorsSubsystem, ClimbSubsystem climbSubsystem) {
        manipulatorsSubsystem.setIntakeSpeed(getIntakeSpeed());
        climbSubsystem.setRackSpeed(getRackSpeed());
        climbSubsystem.setClimbWheelSpeed(getClimbWheelSpeed());
        if (controller.getBackButton() && controller.getStartButton()) {
            climbSubsystem.dropClimbGuides();
            isClimbing = true;
        }

        PivotPosition targetPosition = null;
        if (controller.getXButton()) {
            targetPosition = isShuttlePos ? PivotPosition.ROCKET_CARGO : PivotPosition.SHUTTLE_CARGO;
            isShuttlePos = !isShuttlePos;
        } else {
            isShuttlePos = false;
            if (controller.getBButton()) {
                targetPosition = PivotPosition.LOCK_HATCH;
            } else if (controller.getAButton()) {
                targetPosition = PivotPosition.UNLOCK_HATCH;
            } else if (controller.getBackButton()) {
                targetPosition = PivotPosition.CLIMB;
            }
        }

        if (targetPosition != null) {
            manipulatorsSubsystem.setPivotPosition(targetPosition);
        }
    }

    private double getIntakeSpeed() {
        double intake = controller.getTriggerAxis(Hand.kLeft);
        double output = controller.getTriggerAxis(Hand.kRight);
        if (intake > minTriggerToIntake) {
            return .4;
        }
        if (output > minTriggerToIntake) {
            return -1;
        }
        return 0;
    }

    private double getRackSpeed() {
        if (isClimbing) {
            boolean up = controller.getBumper(Hand.kRight);
            boolean down = controller.getBumper(Hand.kLeft);
            if (up && !down) {
                return -1;
            }
            if (down && !up) {
                return 1;
            }
        }
        return 0;
    }

    private double getClimbWheelSpeed() {
        if (isClimbing) {
            return controller.getY();
        }

        return 0;

    }

}