package org.example;

import java.util.ArrayList;

public class Simulation {
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private boolean running;
    private Configuration config = new Configuration("");
    private int lifetime = 0;
    private IMap map = new PortalMap(config.getWidth(), config.getHeight(), config); // For now hardcoded...

    private final AnimalFactory animalFactory = new AnimalFactory(config, map);


    public void run() {
        running = true;
        createInitialAnimals(config.getInitialAnimalsTotal());
        while (nrOfAnimals > 0) {
            resetStats();
            moveAnimals();
            map.placeAnimals(animals);
            // Conflict resolution...
            Tile[][] simulationTiles = map.getTiles();
            consumePlants(simulationTiles);
            animals.forEach(animal -> animal.energy -= config.getDailyEnergyLoss());
            // Breeding step...
            breedAnimals(simulationTiles);
            // Grow plants ...
            removeDeadEntities();
            map.growPlants(config.getPlantGrowth());
            collectStats();
            System.out.println(getDayStats());
            lifetime += 1;
        }

    }

    private void resetStats() {
        animalsBorn = 0;
        animalsDied = 0;
        plantsEaten = 0;
    }

    private void collectStats() {
        nrOfAnimals = animals.size();
        averageEnergy = animals.stream().mapToInt(animal -> animal.energy).average().orElse(0f);
    }

    private void createInitialAnimals(int total) {
        for (int i = 0; i < total; i++) {
            Vector2d position = this.map.randomAnimalPosition();
            Direction direction = Direction.randomDirection();
            this.animals.add(new Animal(position, direction, this.map, this.config));
        }
    }

    // stats, terrible I know
    private int animalsDied = 0;
    private int animalsBorn = 0;
    private int plantsEaten = 0;
    private int nrOfAnimals = 1;
    private double averageEnergy = 0;

    public String getDayStats() {
        return """
                ========================
                day: %d overview
                animals born: %d
                animals killed: %d
                plants eaten: %d
                surviving animals: %d
                average energy: %.2f
                ========================
                """.formatted(lifetime, animalsBorn, animalsDied, plantsEaten, nrOfAnimals, averageEnergy);
    }

    private void removeDeadEntities() {
        animalsDied = (int) this.animals.stream().filter(animal -> !animal.is_alive()).count();
        this.animals.removeIf(animal -> !animal.is_alive());
    }

    private void moveAnimals() {
        for (Animal animal : this.animals) {
            animal.move();
        }
    }

    private void breedAnimals(Tile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].getBreedingPairs().forEach(pair -> {
                    animals.add(createAnimal(pair.getKey(), pair.getValue()));
                    animalsBorn++;
                });
            }
        }
    }

    private void consumePlants(Tile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile thisTile = tiles[i][j];
                if(thisTile.getPlant() != null && thisTile.hasAnimals()) {
                    thisTile.getTheFittestAnimal().eatVegetation(thisTile.getPlant());
                    thisTile.placePlant(null);
                    plantsEaten++;
                }
            }
        }
    }

    private Animal createAnimal(Animal parent1, Animal parent2) {
        parent1.energy -= config.getCreationEnergy();
        parent2.energy -= config.getCreationEnergy();
        return animalFactory.createAnimal(parent1, parent2);
    }



}
