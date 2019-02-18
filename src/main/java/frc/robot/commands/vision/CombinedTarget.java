package frc.robot.commands.vision;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Value;

public class CombinedTarget extends Command {

    private DriveTrainSubsystem driveTrain;
    private Limelight limelight;

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
        driveTrain.setNeutralMode(NeutralMode.Brake);
    }

    //Tuning Values
    private double KpAim = 0.02;
    private double KpDistanceY = 0.03;
    private double KpDistanceArea = 0.2;

    //Spin
    double minPower = 0.09; //About the minimum amount of power required to move

    //Forewards/Backwards
    double areaTarget = 2.3; //Percent of screen
    double yTarget = 0.0; //Degrees from center

    boolean finished = false;


    int noFrame = 0;
    int maxNoFrames = 20;
    @Override
    protected void execute() {

        if (limelight.getIsTargetFound()){
            noFrame = 0;
        } else {
            System.out.println("NO TARGET");
            if(noFrame++ > maxNoFrames){
                finished = true;
            }
            driveTrain.getDiffDrive().arcadeDrive(0, 0);
            return;
        }
        
        double turnAdjust = getXAdjust();
        double foreAdjust = getYAdjustArea();

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
        finished = turnAdjust == 0 && foreAdjust == 0;
        
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
        double xOffDeg = limelight.getTargetX();

        return KpAim*xOffDeg;
    }

    private double getYAdjustArea(){

        //(areaTarget - Robot...areaPercent) is distanceError
        // above is desiredDistance(areaTarget) - currentDistance(Robot...areaPercent)

        //Driving Adjust is KpDistance * distanceError (above)

        if( !limelight.getIsTargetFound()){
            return 0d;
        }
        
        return (KpDistanceArea* (areaTarget - limelight.getValue(Value.areaPercent)));
    }

    @Override
    protected boolean isFinished() {
        return finished;
    }

    @Override
    protected void end() { 
        double distance = limelight.getDistanceApprox();
        System.out.println("We should be about " + distance);
        driveTrain.getDiffDrive().arcadeDrive(0, 0);
        driveTrain.setNeutralMode(NeutralMode.Brake);
    }
}