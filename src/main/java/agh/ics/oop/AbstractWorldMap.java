package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<Animal>();
    protected List<Grass> grass = new ArrayList<Grass>();

    public abstract Vector2d lowerLeft();

    public abstract Vector2d upperRight();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft()) && position.precedes(upperRight()) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return true;
            }
        }
        for (Grass grass_unit : grass) {
            if (grass_unit.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        for (Grass grass_unit : grass) {
            if (grass_unit.getPosition().equals(position)) {
                return grass_unit;
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
