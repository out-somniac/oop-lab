package agh.ics.oop.interfaces;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.elements.AbstractEntity;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);

    void place(AbstractEntity entity);

    boolean isOccupied(Vector2d position);

    Object objectAt(Vector2d position);
}