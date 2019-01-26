package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Globals;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Value;

public class CombinedTarget extends Command {

    private DriveTrainSubsystem driveTrain;
    private Limelight limelight;

    public CombinedTarget() {
        this(Globals.getDrivetrain(), Globals.getLimelight());
    }

    public CombinedTarget(DriveTrainSubsystem driveTrain, Limelight limelight){
        this.driveTrain = driveTrain;
        this.limelight = limelight;
        requires(driveTrain);
    }

    public CombinedTarget setTarget(double targetArea){
        this.areaTarget = targetArea;
        return this;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    //Tuning Values
    double KpAim = 0.02;
    double KpDistanceY = 0.03;
    double KpDistanceArea = 0.5;

    //Spin
    double minPower = 0.09; //About the minimum amount of power required to move

    //Forewards/Backwards
    double areaTarget = 1.8; //Percent of screen
    double yTarget = 0.0; //Degrees from center

    boolean finished = false;


    int noFrame = 0;
    int maxNoFrames = 20;
    @Override
    protected void execute() {

        if (limelight.getValue(Value.areaPercent) == 0.0){
            System.out.println("NO TARGET");
            if(noFrame++ > maxNoFrames){
                finished = true;
            }
            driveTrain.getDiffDrive().arcadeDrive(0, 0);
            return;
        } else {
            noFrame = 0;
        }
        
        double turnAdjust = getXAdjust();


        double foreAdjust = getYAdjustArea();
        
        System.out.println(foreAdjust);

        boolean complete = true;//dirty
        if(Math.abs(turnAdjust) < 0.05){
            turnAdjust = 0.0;
        } else {
            turnAdjust += Math.signum(turnAdjust)*minPower;
            complete = false;
        }
        if(Math.abs(foreAdjust) < 0.05){
            foreAdjust = 0.0;
        } else {
            foreAdjust += Math.signum(foreAdjust)*minPower;
            complete = false;
        }
        finished = complete;
        
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

        if (headingErr == 0.0) return 0;

        return KpAim*headingErr;
    }

    private double getYAdjustArea(){

        //(areaTarget - Robot...areaPercent) is distanceError
        // above is desiredDistance(areaTarget) - currentDistance(Robot...areaPercent)

        //Driving Adjust is KpDistance * distanceError (above)

        if( limelight.getValue(Value.areaPercent) == 0.0) return 0;
        System.out.println((areaTarget - limelight.getValue(Value.areaPercent)));
        return (KpDistanceArea* (areaTarget - limelight.getValue(Value.areaPercent)));
    }

    private double getYAdjustHeight(){
        
        //double yOffDeg = Robot.limelight.getValue(Value.yOffDeg) - yTarget;
        //double distanceAdjust = KpDistance * -yOffDeg;

        if (yTarget == 0) return 0;

        return KpDistanceY * -(limelight.getValue(Value.yOffDeg) - yTarget);

    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    public double distance;

    @Override
    protected void end() { 
        distance = limelight.getDistanceApprox();
        System.out.println("We should be about " + distance);
        driveTrain.getDiffDrive().arcadeDrive(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}