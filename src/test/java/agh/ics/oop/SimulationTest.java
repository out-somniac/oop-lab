package agh.ics.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import agh.ics.oop.core.SimulationEngine;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.enums.MapDirection;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.RectangularMap;
import agh.ics.oop.utils.OptionsParser;

/*
 * Integration tests to check functionality of the entire simulation
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimulationTest {

    @Test
    public void test_movement() {
        String[] args = "f b r l f f r r f f f f f f f f".split(" ");
        MoveDirection[] directions = OptionsParser.parse(args);
        AbstractWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(3, 4) };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        Animal animal_a = engine.getAnimal(0);
        assertEquals(new Vector2d(2, 0), animal_a.getPosition());
        assertEquals(MapDirection.SOUTH, animal_a.getOrientation());

        Animal animal_b = engine.getAnimal(1);
        assertEquals(new Vector2d(3, 4), animal_b.getPosition());
        assertEquals(MapDirection.NORTH, animal_b.getOrientation());
    }
}