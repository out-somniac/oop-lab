package simulation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimulationEngine {

    private final IMap map;
    private final IVegetationModel vegetationModel;
    private final List<Animal> animals = new ArrayList<>();
    private Map<Vector2d, List<Animal>> groupedAnimals;
    private final AnimalFactory animalFactory;
    private final Configuration config;
    private int currentDay = 0;


    public SimulationEngine(Configuration config, IMap map, IVegetationModel vegetationModel) {
        this.map = map;
        this.vegetationModel = vegetationModel;
        this.config = config;
        this.animalFactory = new AnimalFactory(this.config, this.map);
        createInitialAnimals(this.config.getInitialAnimalsTotal());
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
        for (List<Animal> animalsAtOnePlace : groupedAnimals.values()) {
            animalsAtOnePlace.stream()
                    .min(animalComparator)
                    .ifPresent(animal -> {
                        if (vegetationModel.isPlantThere(animal.getPosition())) {
                            animal.eatVegetation(vegetationModel.eatPlant(animal.getPosition()));
                        }
                    });
        }
    }

    void breedAnimals() {
        for (List<Animal> animals : groupedAnimals.values()) {
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


    public Map<Vector2d, Tile> getTiles() {
        Map<Vector2d, Tile> objectMap = new HashMap<>();
        for (Map.Entry<Vector2d, List<Animal>> animalEntry : groupedAnimals.entrySet()) {
            Vector2d thisPosition = animalEntry.getKey();
            objectMap.put(thisPosition, new Tile(vegetationModel.isPlantThere(thisPosition), animalEntry.getValue()));
        }
        for (Vector2d position : vegetationModel.getPlantPosition()) {
            if (!objectMap.containsKey(position)) {
                objectMap.put(position, new Tile(true, null));
            }
        }
        return objectMap;

    }
}