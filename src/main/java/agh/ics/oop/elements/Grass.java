package agh.ics.oop.elements;

import agh.ics.oop.core.Vector2d;

public class Grass extends AbstractEntity {
    private Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
