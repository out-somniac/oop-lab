package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassField
        extends AbstractWorldMap {
    private final int grass_count;
    private final Vector2d lower_left;
    private final Vector2d upper_right;
    public List<Grass> grass = new ArrayList<Grass>();
    public List<Animal> animals = new ArrayList<Animal>();

    public GrassField(int grass_count) {
        super();
        this.grass_count = grass_count;
        this.lower_left = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upper_right = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        Random random = new Random();
        for (int i = 0; i < this.grass_count; i++) {
            int x = random.nextInt((int) Math.sqrt(10 * grass_count));
            int y = random.nextInt((int) Math.sqrt(10 * grass_count));
            if (!isOccupied(new Vector2d(x, y))) {
                this.grass.add(new Grass(new Vector2d(x, y)));
            }
        }
    }

    @Override
    public Vector2d lowerLeft() {
        return lower_left;
    }

    @Override
    public Vector2d upperRight() {
        return upper_right;
    }
}
