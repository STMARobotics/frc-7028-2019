package frc.robot.commands.vision;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.vision.Limelight;
import frc.robot.vision.Limelight.Value;

public class CommandTillVision extends Command {

	// Subsystems
	private Limelight limelight;
	private DriveTrainSubsystem driveTrain;

	// System variables
	private Command parentCommand;
	private Command childCommand;

	// Config - defaults
	private int waitFrames = 20; // Frame every 20ms
	private double minArea = 0.0;

	private int frames = 0;
	private boolean started = false;

	/**
	 * Will Run Command(arg0) till Vision Target is Found for xFrames then will run
	 * visionCommand(arg1)
	 * 
	 * @param command       Initial Running command
	 * @param visionCommand Take over command
	 * @param limelight     Custom limelight Subsytem
	 */
	public CommandTillVision(Command command, Command visionCommand, Limelight limelight,
			DriveTrainSubsystem driveTrain) {
		this.parentCommand = command;
		this.childCommand = visionCommand;
		this.limelight = limelight;
		this.driveTrain = driveTrain;
	}
	
	/**
	 * Sets the amount of frames target must be aquired before vision takes over
	 * 
	 * @param waitFrames Frames(~20ms)
	 * @return newVisionCommand
	 */
	public CommandTillVision setWaitFrames(int waitFrames) {
		this.waitFrames = waitFrames;
		return this;
	}

	/**
	 * Sets the minimum area(percent of screen) before vision takes over Must be in
	 * conjuntion with waitFrames
	 * 
	 * @param areaPercent Percent area
	 * @return newVisionCommand
	 */
	public CommandTillVision setMinArea(double areaPercent) {
		this.minArea = areaPercent;
		return this;
	}

	@Override
	protected void initialize() {
		parentCommand.start();
	}

	@Override
	protected void execute() {
		// TODO Have xFrames on ShuffleBoard
		System.out.print(limelight.getIsTargetFound());
		if (limelight.getIsTargetFound() && frames >= 0) {
			frames++;
			System.out.println("Target Found");
		}
		if (frames > waitFrames && !started) {
			childCommand.start();
			parentCommand.cancel();
			started = true;
		} else if (frames != -1 && frames > waitFrames + 20 && started
				&& limelight.getValue(Value.AREA_PERCENT) > minArea) {
			frames = -1;
		}
	}

	@Override
	protected void interrupted() {
		childCommand.cancel();
		parentCommand.cancel();
		System.out.println("Stopping");
	}

	@Override
	protected boolean isFinished() {
		return childCommand.isCompleted();
	}

}