package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {
    private final int width, height;
    private final Vector2d lowerLeft, upperRight;
    private final List<Animal> animals;

    public RectangularMap(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width - 1, height - 1);
        this.animals = new ArrayList<Animal>();
    }

    @Override
    public Vector2d lowerLeft() {
        return lowerLeft;
    }

    @Override
    public Vector2d upperRight() {
        return upperRight;
    }
}