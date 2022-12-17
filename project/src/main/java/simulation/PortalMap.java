package simulation;

import java.util.List;

public class PortalMap implements IMap {
    private final int width, height;
    private final Configuration config;

    public PortalMap(Configuration config) {
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.config = config;
    }

    @Override
    public boolean isLegalPosition(Vector2d desiredPosition) {
        return !(desiredPosition.x < 0 || desiredPosition.y < 0 || desiredPosition.x >= width
                || desiredPosition.y >= height);
    }

    @Override
    public Vector2d newAnimalPosition(Vector2d desiredPosition) {
        return getRandomPosition();
    }

    @Override
    public Vector2d getRandomPosition() {
        int x = (int) (Math.random() * (this.width + 1));
        int y = (int) (Math.random() * (this.height + 1));
        return new Vector2d(x, y);
    }
}
