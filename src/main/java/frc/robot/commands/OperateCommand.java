package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.drivesystems.operator.Operator;
import frc.robot.subsystems.ManipulatorsSubsystem;

public class OperateCommand extends Command {

    private ManipulatorsSubsystem manipulatorsSubsystem;
    private SendableChooser<Operator> operatorChooser;

    public OperateCommand(ManipulatorsSubsystem manipulatorsSubsystem, SendableChooser<Operator> operatorChooser){
        this.operatorChooser = operatorChooser;
        this.manipulatorsSubsystem = manipulatorsSubsystem;
        requires(manipulatorsSubsystem);
    }

    public void execute() {
        Operator operator = operatorChooser.getSelected();
        operator.operate(manipulatorsSubsystem);
    }

    public boolean isFinished() {
        return false;
    }

}