package simulation;

import javafx.util.Pair;

import java.util.*;

public class Tile {
    private final List<Animal> animalList = new ArrayList<>();

    private Plant plant;

    private int soilFertility = 1;

    public Tile(int soilFertility) {
        this.soilFertility = soilFertility;
    }

    public void addAnimal(Animal animal) {
        animalList.add(animal);
    }



    /***
     * Function to retrieve pairs of animals that want to reproduce on a given tile
     * and couples them by their descending energy
     * 
     * @return list of pairs of animals
     */


    public Plant getPlant() {
        return plant;
    }

    public void placePlant(Plant plant) {
        this.plant = plant;
    }

    public boolean hasAnimals() {
        return !animalList.isEmpty();
    }
}
