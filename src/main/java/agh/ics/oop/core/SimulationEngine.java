package agh.ics.oop.core;

import java.util.ArrayList;
import java.util.List;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.gui.App;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.maps.AbstractWorldMap;
import javafx.application.Platform;

public class SimulationEngine implements IEngine, Runnable {
    private final List<MoveDirection> directions;
    private final List<Animal> animals;
    private final int move_delay = 1000;
    private final App app;
    private final AbstractWorldMap map;

    public SimulationEngine(MoveDirection[] directions, AbstractWorldMap map, Vector2d[] positions, App app) {
        this.directions = List.of(directions);
        this.animals = new ArrayList<>();
        this.app = app;
        this.map = map;
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
        Platform.runLater(() -> {
            app.renderMap(this.map);
        });
        try {
            Thread.sleep(this.move_delay);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < directions.size(); i++) {
            Animal currentAnimal = animals.get(i % animals.size());
            currentAnimal.move(directions.get(i));
            Platform.runLater(() -> {
                app.renderMap(this.map);
            });
            try {
                Thread.sleep(this.move_delay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
