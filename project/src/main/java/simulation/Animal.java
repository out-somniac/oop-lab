package simulation;

public class Animal {
    private final Genotype genotype;
    private final IMap map;

    private Direction direction;
    private Vector2d position;
    public int energy;
    final int dayOfBirth;

    int nrOfChildren;
    private final Configuration config;

    public Animal(Vector2d position, Direction direction, IMap map, Configuration config, int dayOfBirth) {
        this(position, direction, map, config, new Genotype(config.getGenomeLength()), dayOfBirth);
    }

    public Animal(Vector2d position, Direction direction, IMap map, Configuration config, Genotype genotype, int dayOfBirth) {
        this.position = position;
        this.direction = direction;
        this.genotype = genotype;
        this.map = map;
        this.energy = config.getStartingEnergy();
        this.config = config;
        this.dayOfBirth = dayOfBirth;
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
            this.energy -= config.getEnergyPenalty();
        }
    }

    public boolean wantsToReproduce() {
        return this.energy >= config.getFullEnergy();
    }

    public void eatVegetation(Plant plant) {
        this.energy = Math.min(config.getMaxEnergy(), this.energy + plant.getEnergy());
    }

    public void breed() {
        energy -= config.getCreationEnergy();
        nrOfChildren++;
    }

    public Genotype getGenotype() {
        return genotype;
    }
}
