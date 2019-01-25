package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Globals;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Value;

public class CombinedTarget extends Command {

    private DriveTrainSubsystem driveTrain = Globals.getDrivetrain();
    private Limelight limelight = Globals.getLimelight();

    public CombinedTarget(double targetArea) {
        // Use requires() here to declare subsystem dependencies
        requires(Globals.getDrivetrain());
        this.areaTarget = targetArea;
    }

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
    double KpAim = 0.02; //P value in pid
    double KpDistanceY = 0.03;
    double KpDistanceArea = 0.5;

    //Spin
    double minPower = 0.09; //About the minimum amount of power required to move
    double xDeadZone = 1.5; //In degrees

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

        boolean dirtyFlag = true;
        if(Math.abs(turnAdjust) < 0.05){
            turnAdjust = 0.0;
        } else {
            turnAdjust += Math.signum(turnAdjust)*minPower;
            dirtyFlag = false;
        }
        if(Math.abs(foreAdjust) < 0.05){
            foreAdjust = 0.0;
        } else {
            foreAdjust += Math.signum(foreAdjust)*minPower;
            dirtyFlag = false;
        }
        finished = dirtyFlag;
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

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return finished;
    }

    public double distance;

    // Called once after isFinished returns true
    @Override
    protected void end() { 
        distance = calculateDistanceFromArea(Globals.getLimelight().getValue(Value.areaPercent));
        System.out.println("We should be about " + distance);
        Globals.getDrivetrain().getDiffDrive().arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }

    double kFactorPercent = 1.8; //Value ta (degrees)
    double KFactorDistance = 22; //Distance from target(inches) with that value ta
    public double calculateDistanceFromArea(double CurrentArea){
        return kFactorPercent*KFactorDistance / CurrentArea;
    }
}