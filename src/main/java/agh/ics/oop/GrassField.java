package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassField
        extends AbstractWorldMap {
    private final int grass_count;
    private final Vector2d lower_left;
    private final Vector2d upper_right;
    private final Random random = new Random();

    public GrassField(int grass_count) {
        super();
        this.grass_count = grass_count;
        this.lower_left = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upper_right = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (int i = 0; i < this.grass_count; i++) {
            addRandomGrass();
        }
    }

    private void addRandomGrass() {
        int x, y;
        do {
            x = random.nextInt((int) Math.sqrt(10 * grass_count));
            y = random.nextInt((int) Math.sqrt(10 * grass_count));
        } while (!isOccupied(new Vector2d(x, y)));
        this.entities.add(new Grass(new Vector2d(x, y)));
    }

    @Override
    public Vector2d lowerLeft() {
        return lower_left;
    }

    @Override
    public Vector2d upperRight() {
        return upper_right;
    }

    public void replace(Grass grass) {
        entities.remove(grass);
        addRandomGrass();
    }
}
