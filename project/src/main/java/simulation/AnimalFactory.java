package simulation;

public class AnimalFactory {

    private final Configuration simulationConfig;

    private final IMap map;

    AnimalFactory(Configuration simulationConfig, IMap map) {
        this.simulationConfig = simulationConfig;
        this.map = map;
    }

    public Animal createAnimal(Animal parent1, Animal parent2) {
        assert parent1.getPosition().equals(parent2.getPosition());
        Animal newAnimal = new Animal(parent1.getPosition(), Direction.randomDirection(), map, simulationConfig);
        newAnimal.setEnergy(simulationConfig.getCreationEnergy() * 2);
        return newAnimal;
    }
}
