package agh.ics.oop.lab3;

import agh.ics.oop.lab2.MapDirection;
import agh.ics.oop.lab2.MoveDirection;
import agh.ics.oop.lab2.Vector2d;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    @Override
    public String toString() {
        return String.format("Position: (%s, %s) Direction: %s",
                this.position.x, this.position.y, this.orientation.toString());
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    private void move_by_vector(Vector2d displacement) {
        Vector2d new_position = this.position.add(displacement);
        if (new_position.precedes(new Vector2d(4, 4)) && new_position.follows(new Vector2d(0, 0))) {
            this.position = new_position;
        }
        this.position = new_position;
    }

    public void move(MoveDirection direction) {
        switch(direction) {
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD -> this.move_by_vector(this.orientation.toUnitVector());
            case BACKWARD -> this.move_by_vector(this.orientation.toUnitVector().opposite());
        }
    }

}
