package agh.ics.oop.maps;

import java.util.HashMap;
import java.util.Map;

import agh.ics.oop.core.MapVisualizer;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.elements.AbstractEntity;
import agh.ics.oop.interfaces.IMoveObserver;
import agh.ics.oop.interfaces.IWorldMap;

public abstract class AbstractWorldMap implements IWorldMap, IMoveObserver {
    protected Map<Vector2d, AbstractEntity> entities = new HashMap<>();

    public abstract Vector2d lowerLeft();

    public abstract Vector2d upperRight();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft()) && position.precedes(upperRight()) && !isOccupied(position);
    }

    @Override
    public void updatePosition(Vector2d new_position, Vector2d old_position) {
        AbstractEntity entity = entities.get(old_position);
        // if (entity == null) {
        // throw new IllegalArgumentException("No entity at " + old_position);
        // }
        // TODO: According to all known laws of coding this should work. But It doesn't
        // >:(
        entities.remove(old_position);
        entities.put(new_position, entity);
    }

    @Override
    public void place(AbstractEntity entity) throws IllegalArgumentException {
        if (!canMoveTo(entity.getPosition())) {
            throw new IllegalArgumentException("Can not move entity to " + entity.getPosition());
        }
        entities.put(entity.getPosition(), entity);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return entities.containsKey(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return entities.get(position);
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(lowerLeft(), upperRight());
    }
}
