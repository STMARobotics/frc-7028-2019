package frc.robot.commands.VisionCommands;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Globals;
import frc.robot.vision.Limelight;

public class CommandTillVision extends Command {

	private Command parentCommand;
	private Command childCommand;

	private Limelight limelight;

	/**
	 * Will Run Command(arg0) till Vision Target is Found for xFrames then will run visionCommand(arg1)
	 * @param command Initial Running command
	 * @param visionCommand Take over command 
	 * @param limelight Custom limelight Subsytem
	 */
	public CommandTillVision(Command command, Command visionCommand, Limelight limelight){
		this.parentCommand = command;
		this.childCommand = visionCommand;
		this.limelight = limelight;
		parentCommand.start();
	}
	
	/**
	 * Will run commmand(arg0) till Vision target is found for xFrames then will run visionCommand(arg1)
	 * @param command Initial running command
	 * @param visionCommand Take over command
	 */
	public CommandTillVision(Command command, Command visionCommand){
		this(command, visionCommand, Globals.getLimelight());
	}
	
	int frames = 0;
	boolean started = false;
	@Override
	protected void execute() {
		//TODO Only take if seen for x frames.
		//TODO Have xFrames on ShuffleBoard
		//TODO MINIMUN AREA to switch to vision in suffleboard
		if(limelight.hasTarget()){
			frames++;
		}
		if(frames > 20 && !started){
			Globals.getDrivetrain().setNeutralMode(NeutralMode.Coast);
			childCommand.start();
			parentCommand.cancel();
			started = true;
		} else if(frames > 40 && started){
			Globals.getDrivetrain().setNeutralMode(NeutralMode.Brake);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
    }
    
}