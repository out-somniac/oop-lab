package simulation;

import java.util.concurrent.ThreadLocalRandom;

public class AnimalFactory {

    private final Configuration config;

    private final IMap map;

    AnimalFactory(Configuration config, IMap map) {
        this.config = config;
        this.map = map;
    }

    public Animal createAnimal(Animal parent1, Animal parent2, int currentDay) {
        assert parent1.getPosition().equals(parent2.getPosition());
        Genotype newGenotype = createGenotype(parent1, parent2);
        Animal newAnimal = new Animal(parent1.getPosition(), Direction.randomDirection(), map, config, newGenotype , currentDay);
        newAnimal.setEnergy(config.getCreationEnergy() * 2);
        return newAnimal;
    }

    private Genotype createGenotype(Animal parent1, Animal parent2) {
        Genotype newGenotype = ThreadLocalRandom.current().nextBoolean() ?
                    Genotype.mixGenotypes(parent1.getGenotype(), parent2.getGenotype(), (double) parent1.energy / parent2.energy) :
                    Genotype.mixGenotypes(parent2.getGenotype(), parent1.getGenotype(), (double) parent2.energy / parent1.energy);
        newGenotype.mutateGenes(ThreadLocalRandom.current().nextInt(config.getMinMutations(), config.getMaxMutations() + 1), 1);
        return newGenotype;
    }
}
