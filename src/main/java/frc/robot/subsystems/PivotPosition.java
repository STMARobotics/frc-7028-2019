package frc.robot.subsystems;

public enum PivotPosition {
    UNLOCK_HATCH(0),
    LOCK_HATCH(-400),
    SHUTTLE_CARGO(-850),
    ROCKET_CARGO(-625),
    CLIMB(-1300),
    REST(0);

    private final int position;

    private PivotPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}