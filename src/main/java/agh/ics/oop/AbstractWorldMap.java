package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    protected List<IEntity> entities = new ArrayList<IEntity>();

    public abstract Vector2d lowerLeft();

    public abstract Vector2d upperRight();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft()) && position.precedes(upperRight()) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            entities.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (IEntity entity : entities) {
            if (entity.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (IEntity entity : entities) {
            if (entity.getPosition().equals(position)) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(lowerLeft(), upperRight());
    }
}
