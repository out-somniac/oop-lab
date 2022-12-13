package org.example;

public class Animal {
    private final Genotype genotype;
    private final IMap map;

    private Direction direction;
    private Vector2d position;
    public int energy;
    private Configuration simulation_config;

    public Animal(Vector2d position, Direction direction, IMap map, Configuration config) {
        this.position = position;
        this.direction = direction;
        this.genotype = new Genotype(config.getGenomeLength());
        this.map = map;
        this.energy = config.getStartingEnergy();
        this.simulation_config = config;
    }

    @Override
    public String toString() {
        return this.position.toString() + " " + this.direction.toString();
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public boolean is_alive() {
        return this.energy >= 0;
    }

    public void move() {
        this.direction = this.direction.rotate(this.genotype.get_rotation());
        this.genotype.advance_gene();
        Vector2d desired_position = this.position.add(this.direction.toUnitVector());
        if (this.map.isLegalPosition(desired_position)) {
            this.position = desired_position;
        } else {
            this.position = this.map.randomAnimalPosition();
            this.energy -= simulation_config.getEnergyPenalty();
        }
    }
}
