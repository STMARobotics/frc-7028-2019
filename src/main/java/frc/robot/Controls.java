package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.drivesystems.driver.*;
import frc.robot.drivesystems.operator.*;

public class Controls {

    public static SendableChooser<Driver> driverChooser = new SendableChooser<>();
    public static SendableChooser<Operator> operatorChooser = new SendableChooser<>();
    private static SendableChooser<XboxController> driverControllerChooser = new SendableChooser<>();
    private static SendableChooser<XboxController> operatorControllerChooser = new SendableChooser<>();
    private static final XboxController controllerOne = new XboxController(0);
    private static final XboxController controllerTwo = new XboxController(2);
    //private static final ControlSet controlSet = new ControlSet(driverControllerChooser, operatorControllerChooser);

    public static void robotInit(){
        driverChooser.setDefaultOption("Jorge Driver", new JorgeDriver(driverControllerChooser));
        driverChooser.addOption("Brandon Driver", new BrandonDriver(driverControllerChooser));
        driverChooser.addOption("Hunter Driver", new HunterDriver(driverControllerChooser));
        SmartDashboard.putData("Driver", driverChooser);

        driverControllerChooser.setDefaultOption("Driver Controller: 1", controllerOne);
        driverControllerChooser.addOption("Driver Controller: 2", controllerTwo);
        SmartDashboard.putData("Driver Controller", driverControllerChooser);

        operatorChooser.setDefaultOption("Jorge Operator", new JorgeOperator(driverControllerChooser));
        operatorChooser.addOption("Brandon Operator", new BrandonOperator(driverControllerChooser));
        operatorChooser.addOption("Hunter Operator", new HunterOperator(driverControllerChooser));
        SmartDashboard.putData("Operator", operatorChooser);

        operatorControllerChooser.setDefaultOption("Operator Controller: 1", controllerOne);
        operatorControllerChooser.addOption("Operator Controller: 2", controllerTwo);
        SmartDashboard.putData("Operator Controller", operatorControllerChooser);
    }

}