package simulation;

public class Animal {
    private final Genotype genotype;
    private final IMap map;

    private Direction direction;
    private Vector2d position;
    public int energy;
    private Configuration simulationConfig;

    public Animal(Vector2d position, Direction direction, IMap map, Configuration config) {
        this.position = position;
        this.direction = direction;
        this.genotype = new Genotype(config.getGenomeLength());
        this.map = map;
        this.energy = config.getStartingEnergy();
        this.simulationConfig = config;
    }

    void setEnergy(int energy) {
        this.energy = energy;
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

    public boolean isAlive() {
        return this.energy > 0;
    }

    public void move() {
        this.direction = this.direction.rotate(this.genotype.getRotation());
        this.genotype.advanceGene();
        Vector2d desiredPosition = this.position.add(this.direction.toUnitVector());
        if (this.map.isLegalPosition(desiredPosition)) {
            this.position = desiredPosition;
        } else {
            this.position = this.map.newAnimalPosition(desiredPosition);
            this.energy -= simulationConfig.getEnergyPenalty();
        }
    }

    public boolean wantsToReproduce() {
        return this.energy >= simulationConfig.getFullEnergy();
    }

    public void eatVegetation(Plant plant) {
        this.energy = Math.min(simulationConfig.getMaxEnergy(), this.energy + plant.getEnergy());
    }
}
