package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.enums.MapDirection;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.maps.RectangularMap;

/*
 * Unit tests for the Animal class
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnimalTest {
    private Animal animal;

    @BeforeEach
    public void setup() {
        this.animal = new Animal(new RectangularMap(5, 5), new Vector2d(2, 2));
    }

    @Test
    public void test_position() {
        assertEquals(animal.getPosition(), new Vector2d(2, 2));
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(2, 3));
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(2, 4));
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(1, 4));
    }

    @Test
    public void test_orientation() {
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getOrientation(), MapDirection.EAST);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getOrientation(), MapDirection.SOUTH);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getOrientation(), MapDirection.EAST);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getOrientation(), MapDirection.SOUTH);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getOrientation(), MapDirection.WEST);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getOrientation(), MapDirection.NORTH);
    }

    @Test
    public void test_outOfBounds() {
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(2, 4)));
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(4, 4)));
    }
}
