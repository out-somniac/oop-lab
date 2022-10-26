package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private IWorldMap map;

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    private Animal(IWorldMap map) {
        this.map = map;
        this.position = new Vector2d(2, 2);
    }

    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return String.format("%s", this.orientation.toString());
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    private void move_by_vector(Vector2d displacement) {
        Vector2d new_position = this.position.add(displacement);
        if (map.canMoveTo(new_position)) {
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
