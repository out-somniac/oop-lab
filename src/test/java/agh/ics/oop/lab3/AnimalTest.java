package agh.ics.oop.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import agh.ics.oop.lab2.MoveDirection;
import agh.ics.oop.lab2.Vector2d;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnimalTest {
    private Animal animal;

    @BeforeEach
    public void setup() {
        this.animal = new Animal();
    }

    @Test
    public void test_position() {
        assertEquals(new Animal().toString(), "(2, 2) facing North");
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.toString(), "(2, 3) facing North");
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.toString(), "(2, 4) facing North");
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.toString(), "(1, 4) facing West");
    }

    @Test
    public void test_orientation() {
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.toString(), "(2, 2) facing East");
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.toString(), "(2, 2) facing South");
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.toString(), "(2, 2) facing East");
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.toString(), "(2, 2) facing South");
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.toString(), "(2, 2) facing West");
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.toString(), "(2, 2) facing North");
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

    @Test
    public void test_stringInput() {
        MoveDirection[] moves = OptionsParser.parse("forward Szynszyl b left left forward f f".split(" "));
        Arrays.stream(moves).forEach(move -> this.animal.move(move));
        assertTrue(animal.isAt(new Vector2d(2, 0)));
        moves = OptionsParser.parse("right ¯\\_(ツ)_/¯ forward f f".split(" "));
        Arrays.stream(moves).forEach(move -> this.animal.move(move));
        assertTrue(animal.isAt(new Vector2d(0, 0)));
    }
}
