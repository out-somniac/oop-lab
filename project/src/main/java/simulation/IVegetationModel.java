package simulation;

import java.util.List;

/** An interface to create, grow and manage vegetation on a 2D map.
 * It's responsible for keeping track of all the plants and their distribution on a map.
 */
public interface IVegetationModel {

    void growPlants(int n);

    int plantCount();

    /**
     * retrieves energy of the eaten plant, zero if the plant isn't there
     */
    int eatPlant(Vector2d position);

    boolean isPlantThere(Vector2d position);

    List<Vector2d> getPlantPosition();
}
