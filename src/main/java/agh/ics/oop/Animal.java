package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    @Override
    public String toString() {
        return String.format("%s facing %s",
                this.position, this.orientation.toString());
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    private void move_by_vector(Vector2d displacement) {
        Vector2d new_position = this.position.add(displacement);
        if (new_position.precedes(new Vector2d(4, 4)) && new_position.follows(new Vector2d(0, 0))) {
            this.position = new_position;
        }
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD -> this.move_by_vector(this.orientation.toUnitVector());
            case BACKWARD -> this.move_by_vector(this.orientation.toUnitVector().opposite());
        }
    }

}
