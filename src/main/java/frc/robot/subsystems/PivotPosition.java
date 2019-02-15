package frc.robot.subsystems;

public enum PivotPosition {
    UNLOCK_HATCH(-500),
    LOCK_HATCH(0),
    SHUTTLE_CARGO(-1000),
    ROCKET_CARGO(-1000);

    private final int position;

    private PivotPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}