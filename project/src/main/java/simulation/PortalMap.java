package simulation;

import java.util.List;

public class PortalMap implements IMap {
    private final int width, height;
    private final Tile[][] tiles;
    private final Configuration config;

    public PortalMap(Configuration config) {
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.config = config;
        tiles = new Tile[height + 1][width + 1];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile(1);
            }
        }
    }

    @Override
    public boolean isLegalPosition(Vector2d desired_position) {
        return !(desired_position.x < 0 || desired_position.y < 0 || desired_position.x >= width
                || desired_position.y >= height);
    }

    @Override
    public Vector2d newAnimalPosition(Vector2d desired_position) {
        return getRandomPosition();
    }

    @Override
    public Vector2d getRandomPosition() {
        int x = (int) (Math.random() * (this.width + 1));
        int y = (int) (Math.random() * (this.height + 1));
        return new Vector2d(x, y);
    }

    @Override
    public void growPlants(int n) {
        for (int i = 0; i < n; i++) {
            Vector2d position = getRandomPosition();
            if (tiles[position.x][position.y].getPlant() == null)
                tiles[position.x][position.y].placePlant(new Plant(config.getPlantEnergy()));
        }
    }

    @Override
    public void placeAnimals(List<Animal> animals) {
        for (Tile[] tileRow : tiles) {
            for (Tile tile : tileRow) {
                tile.removeAllAnimals();
            }
        }

        for (Animal animal : animals) {
            Vector2d pos = animal.getPosition();
            tiles[pos.x][pos.y].addAnimal(animal);
        }
    }

    @Override
    public Tile[][] getTiles() {
        return tiles;
    }
}
