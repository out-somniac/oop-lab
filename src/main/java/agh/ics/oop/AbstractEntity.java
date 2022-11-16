package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntity {
    private List<IMoveObserver> observers = new ArrayList<>();

    public abstract Vector2d getPosition();

    protected void notifyMove(Vector2d new_position, Vector2d old_position) {
        observers.forEach(observer -> observer.updatePosition(new_position, old_position));
    }

    protected void addObserver(IMoveObserver observer) {
        observers.add(observer);
    }

    protected void removeObserver(IMoveObserver observer) {
        observers.remove(observer);
    }
}
