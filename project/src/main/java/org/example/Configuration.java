package org.example;

// TODO: Read config from file instead of hardcoded values.
public class Configuration {
    // Hardcoded values - should be read from a config
    private final int width = 10;
    private final int height = 10;

    private final int plants_initial_total = 100; // How many plants are spawned at the beginning
    private final int plant_energy = 1; // How much energy a plant gives
    private final int plant_growth = 10; // How many plants grow per iteration
    private final int animals_initial_total = 100; // Analogous
    private final int starting_energy = 10;
    private final int full_energy = 5;
    private final int creatiown_energy = 3;
    private final int min_mutations = 0;
    private final int max_mutattions = 5;
    private final int genome_length = 10;
    private final int energy_penalty = 1; // Penalty for crossing the bounding box

    public Configuration(String filepath) {
        // Reading from file...
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getGenomeLength() {
        return this.genome_length;
    }

    public int getStartingEnergy() {
        return this.starting_energy;
    }

    public int getEnergyPenalty() {
        return this.energy_penalty;
    }

    public int getInitialAnimalsTotal() {
        return this.animals_initial_total;
    }
}
