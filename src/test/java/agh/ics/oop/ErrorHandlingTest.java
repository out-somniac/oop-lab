package agh.ics.oop;

import org.junit.jupiter.api.Test;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.maps.RectangularMap;
import agh.ics.oop.utils.OptionsParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class ErrorHandlingTest {
    @Test
    public void test_OptionParserIllegalString() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parse("Szynszyl Wypadek drogowy f f b r".split(" "));
        });
        String expectedMessage = "Szynszyl is not a valid argument!";
        String actualMessage = ex.getMessage();
        assertEquals(expectedMessage, actualMessage); // Using assertThrows
    }

    @Test
    public void test_OptionParserEmptyString() {
        try {
            OptionsParser.parse("".split(" "));
            fail("Method didn't throw exception when expected to!"); // Using fail() when exception not thrown
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex);
        }
    }

    @Test
    public void test_AnimalPlace() {
        RectangularMap map = new RectangularMap(1, 1);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            map.place(animal);
        });
        assertEquals("Can not move entity to (2, 2)", ex.getMessage());
    }
}
