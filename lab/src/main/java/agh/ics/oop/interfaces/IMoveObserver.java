package agh.ics.oop.interfaces;

import agh.ics.oop.core.Vector2d;

public interface IMoveObserver {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param new_position New position of the object
     * @param old_position Old position of the object to be updated
     */
    public void updatePosition(Vector2d new_position, Vector2d old_position);
}
