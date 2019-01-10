package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.PointCommand;
import frc.robot.vision.Gyro;
import frc.robot.vision.Limelight.Value;

public class AdvancedTarget extends CommandGroup{

    double heading;
    double distance;
    double targetAngleField = 270;
    double targetDistance = 12;


    public AdvancedTarget() {
        double xOffDeg = Robot.limelight.getValue(Value.xOffDeg);

        double distance = calculateDistanceFromArea(Robot.limelight.getValue(Value.areaPercent));

        double currentAngleField = Gyro.getGyroPosition();

        double inchesOffFromTarget = distance*Math.sin(targetAngleField - (currentAngleField - xOffDeg));

        double wantedForwardTravel = Math.sqrt(distance*distance - inchesOffFromTarget*inchesOffFromTarget) - targetDistance;

        heading = targetAngleField - (90 - Math.atan(wantedForwardTravel / inchesOffFromTarget));

        distance = Math.sqrt(inchesOffFromTarget*inchesOffFromTarget + wantedForwardTravel*wantedForwardTravel);
    
        addSequential(new PointCommand(heading));
        addSequential(new DriveForwardCommand(0.1, distance));
        addSequential(new PointCommand(targetAngleField));
        addSequential(new DriveForwardCommand(0.1, targetDistance));
    
    }
    double kFactorPercent = 5; //Value ta (degrees)
    double KFactorDistance = 5; //Distance from target(inches) with that value ta
    public double calculateDistanceFromArea(double CurrentArea){
        return kFactorPercent*KFactorDistance / CurrentArea;
    }
}