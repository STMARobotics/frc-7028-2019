package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.drivesystems.operator.Operator;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class OperateCommand extends Command {

    private ManipulatorsSubsystem manipulatorsSubsystem;
    private ClimbSubsystem climbSubsystem;
    private SendableChooser<Operator> operatorChooser;

    public OperateCommand(ManipulatorsSubsystem manipulatorsSubsystem, ClimbSubsystem climbSubsystem, SendableChooser<Operator> operatorChooser){
        this.operatorChooser = operatorChooser;
        this.manipulatorsSubsystem = manipulatorsSubsystem;
        this.climbSubsystem = climbSubsystem;
        requires(manipulatorsSubsystem);
        requires(climbSubsystem);
    }

    public void execute() {
        Operator operator = operatorChooser.getSelected();
        operator.operate(manipulatorsSubsystem, climbSubsystem);
    }

    public boolean isFinished() {
        return false;
    }

}