package frc.robot.smartdashboard;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * CachingSendableChooser
 */
public class CachingSendableChooser<T> extends SendableChooser<T> {

    private long lastCheck;

    private T cachedSelected;

    @Override
    public T getSelected() {
        long now = System.currentTimeMillis();
        if (now - lastCheck > 1000) {
            cachedSelected = super.getSelected();
            lastCheck = now;
        }
        return cachedSelected;
    }

}