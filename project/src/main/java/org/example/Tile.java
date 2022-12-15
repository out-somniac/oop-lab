package org.example;

import javafx.util.Pair;

import java.util.*;

public class Tile {
    private final List<Animal> animalList = new ArrayList<>();

    private Plant plant;

    private int soilFertility = 1;

    public Tile(int soilFertility) {
        this.soilFertility = soilFertility;
    }

    public void removeAllAnimals() {
        animalList.clear();
    }

    public void addAnimal(Animal animal) {
        animalList.add(animal);
    }

    public Animal getTheFittestAnimal() {
        return animalList.stream().reduce((animal, animal2) -> animal.energy > animal2.energy ? animal : animal2).orElse(null);
    }

    /***
     * Function to retrieve pairs of animals that want to reproduce on a given tile
     * and couples them by their descending energy
     * @return list of pairs of animals
     */
    public List<Pair<Animal, Animal>> getBreedingPairs() {
        List<Animal> wantTOReproduce = animalList.stream()
                .filter(Animal::wantsToReproduce)
                .sorted(Comparator.comparingInt(animal -> -animal.energy))
                .toList();
        List<Pair<Animal, Animal>> animalPairs = new ArrayList<>();
        for (int i = 0; i < wantTOReproduce.size() - 1; i += 2) {
            animalPairs.add(new Pair<>(wantTOReproduce.get(i), wantTOReproduce.get(i + 1)));
        }
        return animalPairs;
    }

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
