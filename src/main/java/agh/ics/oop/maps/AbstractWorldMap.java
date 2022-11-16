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
        AbstractEntity entity = entities.get(old_position); // TODO: Dangerous since entity can be null. Throw exception
        entities.remove(old_position);
        entities.put(new_position, entity);
    }

    @Override
    public boolean place(AbstractEntity entity) {
        if (!isOccupied(entity.getPosition())) {
            entities.put(entity.getPosition(), entity);
            return true;
        }
        return false;
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
