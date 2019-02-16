package frc.robot.subsystems;

public enum PivotPosition {
    UNLOCK_HATCH(0),
    START(0),
    LOCK_HATCH(700),
    SHUTTLE_CARGO(700),
    ROCKET_CARGO(400),
    CLIMB(200);

    private final int position;

    private PivotPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}