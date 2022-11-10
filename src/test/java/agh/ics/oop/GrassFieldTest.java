package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassFieldTest {
    @Test
    public void test_bounds() {
        GrassField map = new GrassField(10);
        Animal animal_a = new Animal(map, new Vector2d(0, 0));
        Animal animal_b = new Animal(map, new Vector2d(2, 2));
        map.place(animal_a);
        map.place(animal_b);
        animal_b.move(MoveDirection.RIGHT);
        for (int i = 0; i < 20; i++) {
            animal_a.move(MoveDirection.FORWARD);
            animal_b.move(MoveDirection.FORWARD);
        }
        assertEquals(new Vector2d(0, 20), animal_a.getPosition());
        assertEquals(new Vector2d(22, 2), animal_b.getPosition());
    }
}