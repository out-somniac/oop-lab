package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.maps.GrassField;

public class GrassFieldTest {
    @Test
    public void test_AnimalToAnimalCollision() {
        GrassField map = new GrassField(1);
        Animal animal_a = new Animal(map, new Vector2d(3, 0));
        Animal animal_b = new Animal(map, new Vector2d(0, 3));
        animal_b.move(MoveDirection.RIGHT);
        for (int i = 0; i < 4; i++) {
            animal_a.move(MoveDirection.FORWARD);
            animal_b.move(MoveDirection.FORWARD);
        }

        assertEquals(new Vector2d(3, 4), animal_a.getPosition());
        assertEquals(new Vector2d(3, 3), animal_b.getPosition());
    }

    /*
     * This test checks if an animal can move in a spiral pattern essentialy
     * covering a large portion of the map
     */
    @Test
    public void test_UnboundMovement() {
        GrassField map = new GrassField(0);
        Animal animal = new Animal(map, new Vector2d(0, 0));
        for (int i = 0; i < 100; i++) {
            int stride = (int) Math.ceil(((double) i + 1) / 2);
            for (int j = 0; j < stride; j++) {
                animal.move(MoveDirection.FORWARD);
            }
            animal.move(MoveDirection.RIGHT);
        }
        assertEquals(new Vector2d(-25, -25), animal.getPosition());
    }
}
