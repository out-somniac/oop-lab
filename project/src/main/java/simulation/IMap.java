package simulation;

import java.util.List;

public interface IMap {
    public boolean isLegalPosition(Vector2d desired_position);

    public Vector2d randomAnimalPosition();

    public void growPlants(int n);

    void placeAnimals(List<Animal> animals);

    Tile[][] getTiles();
}
