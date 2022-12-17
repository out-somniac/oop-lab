package simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LushEquatorsVegetation implements IVegetationModel {

    private final int width, height;
    private final Configuration config;
    private final Random random;

    private Map<Vector2d, Plant> plants = new HashMap<>();


    LushEquatorsVegetation(Configuration config) {
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.config = config;
        this.random = new Random();
    }

    @Override
    public void growPlants(int n) {
        int tries = 5 * n;
        while (n > 0 && tries > 0) {
            Vector2d newPosition = getRandomPosition();
            if (!isPlantThere(newPosition)) {
                plants.put(newPosition, new Plant(config.getPlantEnergy()));
                n--;
            }
            tries--;
        }
    }

    @Override
    public int plantCount() {
        return plants.size();
    }

    @Override
    public Plant getPlant(Vector2d position) {
        return plants.get(position);
    }

    @Override
    public boolean isPlantThere(Vector2d position) {
        return getPlant(position) != null;
    }

    private Vector2d getRandomPosition() {
        int x = (int) (Math.max(0, Math.min(1, random.nextGaussian(0.5, 0.5))) * (this.width + 1));
        int y = (int) (Math.max(0, Math.min(1, random.nextGaussian(0.5, 0.5))) * (this.height + 1));
        return new Vector2d(x, y);
    }
}
