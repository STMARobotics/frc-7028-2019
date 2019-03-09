package frc.robot.commands.auto;

import java.util.Arrays;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AutoCommandGroup extends CommandGroup {

    /**
     * 
     * @param requiredSubSystems SubSystems to lock by this command group
     */
    public AutoCommandGroup(Subsystem... requiredSubSystems) {
        Arrays.stream(requiredSubSystems).forEach(this::requires);
    }

}