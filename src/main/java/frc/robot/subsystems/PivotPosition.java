package frc.robot.subsystems;

public enum PivotPosition {
    UNLOCK_HATCH(0),
    START(0),
    LOCK_HATCH(-350),
    SHUTTLE_CARGO(-850),
    ROCKET_CARGO(-625),
    CLIMB(-1000);

    private final int position;

    private PivotPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}