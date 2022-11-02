package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    private final List<MoveDirection> directions;
    private final List<Animal> animals;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions) {
        this.map = map;
        this.directions = List.of(directions);
        this.animals = new ArrayList<>();
        for (Vector2d position : positions) {
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }
    }

    public Animal getAnimal(int i) {
        return animals.get(i);
    }

    @Override
    public void run() {
        for (int i = 0; i < directions.size(); i++) {
            animals.get(i % animals.size()).move(directions.get(i));
        }
    }
}
