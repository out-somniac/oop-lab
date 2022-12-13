package org.example;

public interface IMap {
    public boolean isLegalPosition(Vector2d desired_position);

    public Vector2d newAnimalPosition();
}
