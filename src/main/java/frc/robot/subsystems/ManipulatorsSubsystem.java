package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.OperateCommand;
import frc.robot.drivesystems.operator.Operator;

public class ManipulatorsSubsystem extends Subsystem {

    private Spark intake = new Spark(0);
    private Spark pivot = new Spark(1);
    private PowerDistributionPanel pdp;
    // TODO find correct channel for pivot
    private final int PIVOT_CHANNEL = 1;
    // TODO calibrate max draw
    private final double MAX_DRAW = 10;
    private boolean lock = false;
    private SendableChooser<Operator> operatorChooser;

    public ManipulatorsSubsystem(PowerDistributionPanel pdp, SendableChooser<Operator> operatorChooser) {
        this.pdp = pdp;
        this.operatorChooser = operatorChooser;
    }

    /**
     * sets intake speed
     * @param speed + is intake, - is output
     */
    public void setIntakeSpeed(double speed) {
        intake.set(speed);
    }

    /**
     * sets pivot speed
     * @param speed + is up, - is down
     */
    public void setPivotSpeed(double speed) {
        double powerDraw = pdp.getCurrent(PIVOT_CHANNEL);
        if (powerDraw >= MAX_DRAW) {
            pivot.set(0);
            lock = true;
        } else if (lock) {
            pivot.set(0);
        } else if (lock && speed == 0) {
            pivot.set(0);
            lock = false;
        } else {
            pivot.set(speed);
        }
    }

    public int getPivotPositon() {
        return 0;
    }

    public void initDefaultCommand() {
        setDefaultCommand(new OperateCommand(this, operatorChooser));
    }

}