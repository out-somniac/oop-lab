package agh.ics.oop.elements;

import java.util.ArrayList;
import java.util.List;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.interfaces.IMoveObserver;

public abstract class AbstractEntity {
    private List<IMoveObserver> observers = new ArrayList<>();

    public abstract Vector2d getPosition();

    public abstract String getImagePath();

    public abstract String getLabelText();

    protected void notifyMove(Vector2d new_position, Vector2d old_position) {
        observers.forEach(observer -> observer.updatePosition(new_position, old_position));
    }

    public void addObserver(IMoveObserver observer) {
        observers.add(observer);
    }

    protected void removeObserver(IMoveObserver observer) {
        observers.remove(observer);
    }
}
