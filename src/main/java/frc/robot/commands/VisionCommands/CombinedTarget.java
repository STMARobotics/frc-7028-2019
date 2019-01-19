package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Globals;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Target;
import frc.robot.vision.Limelight.Value;

public class CombinedTarget extends Command {

    private DriveTrainSubsystem driveTrain = Globals.getDrivetrain();
    private Limelight limelight = Globals.getLimelight();

    public CombinedTarget() {
        // Use requires() here to declare subsystem dependencies
        requires(Globals.getDrivetrain());
    }

    public CombinedTarget(DriveTrainSubsystem driveTrain, Limelight limelight){
        this.driveTrain = driveTrain;
        this.limelight = limelight;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    //Tuning Values
    double KpAim = 0.01; //P value in pid
    double KpDistance = 0.2;

    //Spin
    double minPower = 0.09; //About the minimum amount of power required to move
    double xDeadZone = 1.5; //In degrees

    //Forewards/Backwards
    double areaTarget = 0.5; //Percent of screen
    double yTarget = 0.0; //Degrees from center

    @Override
    protected void execute() {
        
        double turnAdjust = getXAdjust();


        double foreAdjust = getYAdjustHeight();
        
        if(Math.abs(turnAdjust) < 0.05){
            turnAdjust = 0.0;
        } else {
            turnAdjust += Math.signum(turnAdjust)*minPower;
        }
        if(Math.abs(foreAdjust) < 0.05){
            foreAdjust = 0.0;
        } else {
            foreAdjust += Math.signum(foreAdjust)*minPower;
        }

        //System.out.println(foreAdjust);
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

        driveTrain.getDiffDrive().arcadeDrive(foreAdjust, turnAdjust, false);


    }

    private double getXAdjust(){
        //Get current degrees from center
        double xOffDeg = limelight.getValue(Value.xOffDeg);

        //Convert that to what we need to change
        double headingErr = xOffDeg;

        return KpAim*headingErr;
    }

    private double getYAdjustArea(){

        //(areaTarget - Robot...areaPercent) is distanceError
        // above is desiredDistance(areaTarget) - currentDistance(Robot...areaPercent)

        //Driving Adjust is KpDistance * distanceError (above)

        return (KpDistance * (areaTarget - limelight.getValue(Value.areaPercent)));
    }

    private double getYAdjustHeight(){
        
        //double yOffDeg = Robot.limelight.getValue(Value.yOffDeg) - yTarget;
        //double distanceAdjust = KpDistance * -yOffDeg;

        return KpDistance * -(limelight.getValue(Value.yOffDeg) - yTarget);

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