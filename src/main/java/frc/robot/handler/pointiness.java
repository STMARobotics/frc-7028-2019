package frc.robot.handler;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.auto.PointCommand;
import frc.robot.subsystems.DriveTrainSubSystemTest;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

/**
 * pointiness
 */
public class pointiness {

    static Command currentCommand = null;
    public static void queueCommand(double position){
        currentCommand = new PointCommand(dT, g, position);
        currentCommand.start();
    }







    static DriveTrainSubsystem dT;
    static GyroSubsystem g;

    static{

    }

    public static void setup(DriveTrainSubsystem dT, GyroSubsystem g) {
        pointiness.dT = dT;
        pointiness.g = g;
    }
}