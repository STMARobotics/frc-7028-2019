package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jdk.jfr.Experimental;

@Deprecated
@Experimental
public class AdvancedTarget extends CommandGroup{

    double heading;
    double distance1;
    double targetAngleField = -87;
    double targetDistance = 5;

    public AdvancedTarget(){
        // double xOffDeg = Globals.getLimelight().getValue(Value.xOffDeg);

        // double distance = calculateDistanceFromArea(Globals.getLimelight().getValue(Value.areaPercent));

        // double currentAngleField = GyroSubsystem.getGyroPosition();

        // double angle = Math.abs(targetAngleField - (currentAngleField - xOffDeg));

        // double inchesOffFromTarget = distance*Math.sin(Math.toRadians(angle));

        // double wantedForwardTravel = Math.sqrt(distance*distance - inchesOffFromTarget*inchesOffFromTarget) - targetDistance;

        // this.heading = targetAngleField - (90 - Math.toDegrees(Math.atan(wantedForwardTravel / inchesOffFromTarget)));

        // this.distance1 = Math.sqrt(inchesOffFromTarget*inchesOffFromTarget + wantedForwardTravel*wantedForwardTravel);
    
        // addSequential(new PointCommand(heading));
        // addSequential(new DriveForwardCommand(0.1, distance));
        // addSequential(new PointCommand(targetAngleField));
        // addSequential(new DriveForwardCommand(0.1, targetDistance));
    }
    

    @Override
    public void initialize(){
        
    }

    protected void execute() {

    }

    double kFactorPercent = 3.5; //Value ta (degrees)
    double KFactorDistance = 23; //Distance from target(inches) with that value ta
    public double calculateDistanceFromArea(double CurrentArea){
        return kFactorPercent*KFactorDistance / CurrentArea;
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}