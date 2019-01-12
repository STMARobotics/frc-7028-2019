package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.PointCommand;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.vision.Limelight.Value;

public class AdvancedTarget extends CommandGroup{

    double heading;
    double distance1;
    double targetAngleField = -87;
    double targetDistance = 5;

    public AdvancedTarget(){
        double xOffDeg = Robot.limelight.getValue(Value.xOffDeg);

        double distance = calculateDistanceFromArea(Robot.limelight.getValue(Value.areaPercent));

        double currentAngleField = GyroSubsystem.getGyroPosition();

        double angle = Math.abs(targetAngleField - (currentAngleField - xOffDeg));

        double inchesOffFromTarget = distance*Math.sin(Math.toRadians(angle));

        double wantedForwardTravel = Math.sqrt(distance*distance - inchesOffFromTarget*inchesOffFromTarget) - targetDistance;

        this.heading = targetAngleField - (90 - Math.toDegrees(Math.atan(wantedForwardTravel / inchesOffFromTarget)));

        this.distance1 = Math.sqrt(inchesOffFromTarget*inchesOffFromTarget + wantedForwardTravel*wantedForwardTravel);
    
        addSequential(new PointCommand(heading));
        addSequential(new DriveForwardCommand(0.1, distance));
        addSequential(new PointCommand(targetAngleField));
        addSequential(new DriveForwardCommand(0.1, targetDistance));
    }
    

    @Override
    public void initialize(){
        
    }

    protected void execute() {
        
        //System.out.println(angle + ", " + Math.toRadians(angle));
        //System.out.println(inchesOffFromTarget + ", " + distance);
        //System.out.println(this.heading + ", " + this.distance1);
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