package frc.robot.subsystems;

public enum PivotPosition {
    UNLOCK_HATCH(0),
    LOCK_HATCH(0),
    SHUTTLE_CARGO(0),
    ROCKET_CARGO(0);

    private final int position;

    private PivotPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}