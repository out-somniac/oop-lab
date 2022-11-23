package agh.ics.oop.maps;

import java.util.Random;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.elements.Grass;

public class GrassField
        extends AbstractWorldMap {
    private final int grass_count;
    private final Random random = new Random();

    public GrassField(int grass_count) {
        super();
        this.grass_count = grass_count;

        for (int i = 0; i < this.grass_count; i++) {
            addRandomGrass();
        }
    }

    private void addRandomGrass() {
        int x, y;
        do {
            x = random.nextInt((int) Math.sqrt(10 * grass_count));
            y = random.nextInt((int) Math.sqrt(10 * grass_count));
        } while (isOccupied(new Vector2d(x, y)));
        Vector2d position = new Vector2d(x, y);
        this.entities.put(position, new Grass(position)); // TODO: Should be done like this. Rather with
                                                          // AbstractWorldMap.place() and make entities private
        this.map_boundary.addPosition(position);
    }

    @Override
    public Vector2d upperRight() {
        return this.map_boundary.getUpperRight();
    }

    @Override
    public Vector2d lowerLeft() {
        return this.map_boundary.getLowerLeft();
    }
}
