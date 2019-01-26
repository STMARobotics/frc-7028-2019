package frc.robot.commands.VisionCommands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Globals;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Value;

public class CommandTillVision extends Command {

	//Subsystems
	private Limelight limelight;
	private DriveTrainSubsystem driveTrain;

	//System variables
	private Command parentCommand;
	private Command childCommand;

	//Config - defaults
	private int waitFrames = 20;
	private double minArea = 0.0;

	/**
	 * Will Run Command(arg0) till Vision Target is Found for xFrames then will run visionCommand(arg1)
	 * @param command Initial Running command
	 * @param visionCommand Take over command 
	 * @param limelight Custom limelight Subsytem
	 */
	public CommandTillVision(Command command, Command visionCommand, Limelight limelight, DriveTrainSubsystem driveTrain){
		this.parentCommand = command;
		this.childCommand = visionCommand;
		this.limelight = limelight;
		this.driveTrain = driveTrain;
		parentCommand.start();
	}
	
	/**
	 * Will run commmand(arg0) till Vision target is found for xFrames then will run visionCommand(arg1)
	 * @param command Initial running command
	 * @param visionCommand Take over command
	 */
	public CommandTillVision(Command command, Command visionCommand){
		this(command, visionCommand, Globals.getLimelight(), Globals.getDrivetrain());
	}
	
	/**
	 * Sets the amount of frames target must be aquired 
	 * before vision takes over
	 * @param waitFrames Frames(~20ms)
	 * @return newVisionCommand
	 */
	public CommandTillVision setWaitFrames(int waitFrames){
		this.waitFrames = waitFrames;
		return this;
	}

	/**
	 * Sets the minimum area(percent of screen) before vision takes over
	 * Must be in conjuntion with waitFrames
	 * @param areaPercent Percent area
	 * @return newVisionCommand
	 */
	public CommandTillVision setMinArea(double areaPercent){
		this.minArea = areaPercent;
		return this;
	}


	int frames = 0;
	boolean started = false;
	@Override
	protected void execute() {
		//TODO Have xFrames on ShuffleBoard
		if(limelight.isTargetFound() && frames >= 0){
			frames++;
		}
		if(frames > waitFrames && !started){
			driveTrain.setNeutralMode(NeutralMode.Coast);
			childCommand.start();
			parentCommand.cancel();
			started = true;
		} else if(frames != -1 && frames > waitFrames+20  && started && limelight.getValue(Value.areaPercent) > minArea){
			driveTrain.setNeutralMode(NeutralMode.Brake);
			frames = -1;
		}
	}

	@Override
	protected boolean isFinished() {
		return childCommand.isCompleted();
    }
    
}