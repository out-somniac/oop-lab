package org.example;

public class Animal {
    private Direction direction;
    private Vector2d position;
    private Genotype genotype;

    public Animal(Vector2d position, Direction direction, int number_of_genes) {
        this.position = position;
        this.direction = direction;
        this.genotype = new Genotype(number_of_genes);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void move() {
        this.direction = this.genotype.move_direction(this.direction);
        // TODO: Note that this code doesn't care about map constraints
        this.position.add(this.direction.toUnitVector());
    }
}
