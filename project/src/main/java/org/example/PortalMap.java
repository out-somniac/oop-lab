package org.example;

public class PortalMap implements IMap {
    private final int width, height;

    public PortalMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isLegalPosition(Vector2d desired_position) {
        return !(desired_position.x < 0 || desired_position.y < 0 || desired_position.x >= width
                || desired_position.y >= height);
    }

    @Override
    public Vector2d randomAnimalPosition() {
        int x = (int) (Math.random() * (this.width + 1));
        int y = (int) (Math.random() * (this.height + 1));
        return new Vector2d(x, y);
    }
}
