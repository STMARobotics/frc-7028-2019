package frc.robot.drivesystems.driver;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.auto.PointCommand;

public class JorgeXboxDriver implements Driver {

    private XboxController controller;
    private boolean slowMode = false;
    private PointCommand pointCommand;

    public JorgeXboxDriver(XboxController controller, PointCommand pointCommand) {
        this.controller = controller;
    }

    @Override
    public void drive(DifferentialDrive differentialDrive) {
        double speed = -getSpeed();
        double rotation = getRotation();
        if (getSlowMode()) {
            speed = speed * .6;
            rotation = rotation * .8;
        }
        differentialDrive.arcadeDrive(speed, rotation, true);

        if(controller.getPOV() != -1){
            pointCommand.setTarget(controller.getPOV());
            pointCommand.start();
        }

    }



    /**public enum RotatePosition{
        
        LEFT(270), RIGHT(90), FORWARD(0), BACK(180);

        public double getAngle(){
            return angle;
        }

        double angle;
        RotatePosition(double Position) {
            this.angle = Position;
        }
    }*/

    private double getSpeed() {
        return controller.getY(Hand.kLeft);
    }

    private double getRotation() {
        return controller.getX(Hand.kRight) * .7;
    }

    private boolean getSlowMode() {
        if (controller.getBButton()) {
            slowMode = !slowMode;
        }
        return slowMode;
    }

    public boolean getAutoOverride() {
        return controller.getAButton();
    }

}
