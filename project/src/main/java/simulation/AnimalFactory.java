package simulation;

public class AnimalFactory {

    private final Configuration simulation_config;

    private final IMap map;

    AnimalFactory(Configuration simulation_config, IMap map) {
        this.simulation_config = simulation_config;
        this.map = map;
    }


    public Animal createAnimal(Animal parent1, Animal parent2) {
        assert parent1.getPosition().equals(parent2.getPosition());
        Animal newAnimal = new Animal(parent1.getPosition(), Direction.randomDirection(), map, simulation_config);
        newAnimal.setEnergy(simulation_config.getCreationEnergy()*2);
        return newAnimal;
    }
}
