package org.example;

import java.util.ArrayList;

public class Simulation {
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private boolean running;
    private Configuration config = new Configuration("");
    private int lifetime = 0;
    private IMap map = new PortalMap(config.getWidth(), config.getHeight()); // For now hardcoded...

    public void run() {
        running = true;
        createInitialAnimals(config.getInitialAnimalsTotal());
        while (running) {
            removeDeadEntities();
            moveAnimals();
            // Conflict resolution...
            // Breeding step...
            // Grow plants ...
            lifetime += 1;
        }

    }

    private void createInitialAnimals(int total) {
        for (int i = 0; i < total; i++) {
            Vector2d position = this.map.randomAnimalPosition();
            Direction direction = Direction.randomDirection();
            this.animals.add(new Animal(position, direction, this.map, this.config));
        }
    }

    private void removeDeadEntities() {
        this.animals.removeIf(animal -> !animal.is_alive());
    }

    private void moveAnimals() {
        for (Animal animal : this.animals) {
            animal.move();
        }
    }
}
