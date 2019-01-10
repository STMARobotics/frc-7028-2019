package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.vision.Limelight.Target;
import frc.robot.vision.Limelight.Value;

public class CombinedTarget extends Command {

    public CombinedTarget() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrainSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    //Tuning Values
    double KpAim = 0.1; //P value in pid
    double KpDistance = 0.1;

    //Spin
    double minPower = 0.05; //About the minimum amount of power required to move
    double xDeadZone = 1.0; //In degrees

    //Forewards/Backwards
    double areaTarget = 4.0; //Percent of screen
    double yTarget = 5.0; //Degrees from center

    @Override
    protected void execute() {
        
        double turnAdjust = getXAdjust();

        double foreAdjust = getYAdjustArea();
        
        /**
         *    ^  ^   ^  |  Drive Straight and turn right
         *    |  |   |  v  ++ +-
         * 
         *    ^  ^   |  ^  Drive Straight and turn left
         *    |  |   v  |  ++ -+
         * 
         *    |  |   ^  |  Drive Back and turn right
         *    v  v   |  v  -- +-
         * 
         *    |  |   |  ^  Drive back and turn left
         *    v  v   v  |  -- -+
         */

        Robot.driveTrainSubsystem.getDriveTrain().arcadeDrive(foreAdjust, turnAdjust);


    }

    private double getXAdjust(){
        //Get current degrees from center
        double xOffDeg = Robot.limelight.getValue(Value.xOffDeg);

        //Convert that to what we need to change
        double headingErr = xOffDeg;

        //Start from none moving
        double steeringAdjust = 0.0;

        if(xOffDeg > xDeadZone){
            steeringAdjust = KpAim*headingErr - minPower;
        }else if (xOffDeg < xDeadZone){
            steeringAdjust = KpAim*headingErr + minPower;
        }

        return steeringAdjust;
    }

    private double getYAdjustArea(){

        //(areaTarget - Robot...areaPercent) is distanceError
        // above is desiredDistance(areaTarget) - currentDistance(Robot...areaPercent)

        //Driving Adjust is KpDistance * distanceError (above)

        return (KpDistance * (areaTarget - Robot.limelight.getValue(Value.areaPercent)));
    }

    private double getYAdjustHeight(){
        
        //double yOffDeg = Robot.limelight.getValue(Value.yOffDeg) - yTarget;
        //double distanceAdjust = KpDistance * -yOffDeg;

        return KpDistance * -(Robot.limelight.getValue(Value.yOffDeg) - yTarget);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}