package simulation;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class responsible for managing interactions between different objects on a map.
 * It is also responsible for creating and deleting entities.
 */
public class MapActionsHandler {

    private final IMap map;
    private final IVegetationModel vegetationModel;
    private final List<Animal> animals = new ArrayList<>();
    private Map<Vector2d, List<Animal>> groupedAnimals;
    private final AnimalFactory animalFactory;
    private final Configuration config;
    private int currentDay = 0;


    public MapActionsHandler(Configuration config, IMap map, IVegetationModel vegetationModel) {
        this.map = map;
        this.vegetationModel = vegetationModel;
        this.config = config;
        this.animalFactory = new AnimalFactory(this.config, this.map);
    }

    void moveAnimals() {
        animals.forEach(Animal::move);
    }

    void createInitialAnimals(int n) {
        IntStream.range(0, n).forEach(i -> {
            this.animals
                    .add(new Animal(
                            this.map.getRandomPosition(),
                            Direction.randomDirection(),
                            this.map,
                            this.config, 0));
        });
    }

    Comparator<Animal> energyComp = Comparator.comparingInt(a -> -a.energy);
    Comparator<Animal> ageComp = Comparator.comparingInt(a -> a.dayOfBirth);
    Comparator<Animal> childrenCom = Comparator.comparingInt(a -> -a.nrOfChildren);
    Comparator<Animal> animalComparator = energyComp.thenComparing(ageComp).thenComparing(childrenCom);

    void groupAnimals() {
        groupedAnimals = animals.stream().collect(Collectors.groupingBy(Animal::getPosition, Collectors.toList()));
    }

    void animalsEatGrass() {
        for(List<Animal> animalsAtOnePlace : groupedAnimals.values()) {
            animalsAtOnePlace.stream()
                    .min(animalComparator)
                    .ifPresent(animal -> {
                        if (vegetationModel.isPlantThere(animal.getPosition())) {
                            animal.eatVegetation(vegetationModel.getPlant(animal.getPosition()));
                        }
                    });
        }
    }

    void breedAnimals() {
        for(List<Animal> animals : groupedAnimals.values()) {
            List<Animal> willingAnimals = animals.stream()
                    .filter(Animal::wantsToReproduce)
                    .sorted(animalComparator)
                    .toList();
            for (int i = 0; i < willingAnimals.size() - 1; i += 2) {
                this.animals.add(createAnimal(willingAnimals.get(i), willingAnimals.get(i + 1)));
            }
        }
    }
    void removeDeadEntities() {
        this.animals.removeIf(animal -> !animal.isAlive());
    }

    void growPlants() {
        vegetationModel.growPlants(config.getPlantGrowth());
    }

    private Animal createAnimal(Animal parent1, Animal parent2) {
        parent1.breed();
        parent2.breed();
        Animal newAnimal = animalFactory.createAnimal(parent1, parent2, currentDay);
        return newAnimal;
    }

    public void subtractDailyEnergy() {
        animals.forEach(animal -> animal.energy -= config.getDailyEnergyLoss());
    }

    public void simulateDay() {
        currentDay++;
        // Animals moving...
        moveAnimals();
        // Conflict resolution...
        groupAnimals();
        // Grass eating...
        animalsEatGrass();
        subtractDailyEnergy();
        // Removing animals that starved to death...
        removeDeadEntities();
        // Breeding step...
        breedAnimals();
        // Grow plants ...
        growPlants();
    }

    Statistics generateDaySummary() {
        double averageEnergy = animals.stream().mapToInt(animals -> animals.energy).average().orElse(0f);
        long bornAnimals = animals.stream().filter(animal -> animal.dayOfBirth == currentDay).count();
        return new Statistics(currentDay, animals.size(), bornAnimals, averageEnergy, vegetationModel.plantCount());
    }
}
