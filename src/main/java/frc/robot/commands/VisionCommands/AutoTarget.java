package frc.robot.commands.VisionCommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.vision.Limelight.Target;
import frc.robot.vision.Limelight.Value;

public class AutoTarget extends Command {

    public AutoTarget() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrainSubsystem);
      }
    
      // Called just before this Command runs the first time
      @Override
      protected void initialize() {
      }
    

      double xOffDeg;
      double aPercent;
      // Called repeatedly when this Command is scheduled to run
      @Override
      protected void execute() {

        xOffDeg = Robot.limelight.getValue(Value.xOffDeg, Target.target);
        aPercent = Robot.limelight.getValue(Value.areaPercent, Target.target);
        try{
            double x = 0.0, y = 0.0;
            double direction = Math.signum(xOffDeg);
            if(Math.abs(xOffDeg) >= 10.0){
                x += 0.3*direction;
            } else if (Math.abs(xOffDeg) >= 4){
                x += 0.1*direction;
            }

            if(aPercent == 0.0){
                x = 0.0;
                y = 0.0;
            } else if(aPercent < 2){
                y += 0.3;
            } else if(aPercent  < 4){
                y += 0.1;
            } else if ( aPercent > 5){
                y -= 0.1;
            }


            Robot.driveTrainSubsystem.getDriveTrain().arcadeDrive(x, y);


        /*if(Math.abs(xOffDeg) >= 10.0){
            double direction = Math.signum(xOffDeg);
            Robot.m_subsystem.leftSide.set(0.3*direction);
            Robot.m_subsystem.rightSide.set(0.3*direction);
        } else if (Math.abs(xOffDeg) >= 4.0){
            double direction = Math.signum(xOffDeg);
            Robot.m_subsystem.leftSide.set(0.1*direction);
            Robot.m_subsystem.rightSide.set(0.1*direction);
        } else {
            Robot.m_subsystem.leftSide.set(0.0);
            Robot.m_subsystem.rightSide.set(0.0);
        
            double aValue = Robot.limelight.getValue(Value.areaPercent, Target.target);
            if(aValue != 0.0){
                if (aValue < 2){
                    Robot.m_subsystem.leftSide.set(0.4);
                    Robot.m_subsystem.rightSide.set(-0.4);
                    System.out.println("Faster march");
                } else if (aValue <  4){
                    Robot.m_subsystem.leftSide.set(0.1);
                    Robot.m_subsystem.rightSide.set(-0.1);
                    System.out.println("Foward march");
                }else if(aValue > 5){
                    Robot.m_subsystem.leftSide.set(-0.1);
                    Robot.m_subsystem.rightSide.set(0.1);
                    System.out.println("To close");
                } else{
                    Robot.m_subsystem.leftSide.set(0.0);
                    Robot.m_subsystem.rightSide.set(0.0);
                    System.out.println("Full Stop");
                }
            } else{
                Robot.m_subsystem.rightSide.set(0.0);
                Robot.m_subsystem.leftSide.set(0.0);
            }

        }*/
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    
      // Make this return true when this Command no longer needs to run execute()
      @Override
      protected boolean isFinished() {
        return false;
      }
    
      // Called once after isFinished returns true
      @Override
      protected void end() {
      }
    
      // Called when another command which requires one or more of the same
      // subsystems is scheduled to run
      @Override
      protected void interrupted() {
      }



}