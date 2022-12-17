package simulation;

import java.util.List;

public interface IMap {
    public boolean isLegalPosition(Vector2d desiredPosition);

    public Vector2d newAnimalPosition(Vector2d desiredPosition);

    public Vector2d getRandomPosition();

}
