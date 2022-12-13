package org.example;

import java.io.ObjectInputFilter.Config;
import java.util.ArrayList;

public class Simulation {
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private boolean running;
    private Configuration config = new Configuration(""); // TODO: Read config from file

    private void run() {
        running = true;
        while (running) {
            removeDeadEntities();
            moveEntities();
            // Conflict resolution...
            // Breeding step...
            // growPlants();
        }
    }

    private void removeDeadEntities() {
        this.animals.removeIf(animal -> !animal.is_alive());
    }

    private void moveEntities() {
        for (Animal animal : this.animals) {
            animal.move();
        }
    }
}
