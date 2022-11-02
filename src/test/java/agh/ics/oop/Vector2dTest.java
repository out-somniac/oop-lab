package agh.ics.oop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Vector2dTest {
    private Vector2d vec1, vec2, vec3, vec4, vec5, vec6;

    @BeforeAll
    public void setup() {
        this.vec1 = new Vector2d(7, 2);
        this.vec2 = new Vector2d(-4, 2);
        this.vec3 = new Vector2d(17, -7);
        this.vec4 = new Vector2d(7, 2);
        this.vec5 = new Vector2d(-4, 2);
        this.vec6 = new Vector2d(17, -7);
    }

    @Test
    public void test_toString() {
        assertEquals(vec1.toString(), "(7, 2)");
        assertEquals(vec2.toString(), "(-4, 2)");
        assertEquals(vec3.toString(), "(17, -7)");
        assertEquals(vec4.toString(), "(7, 2)");
        assertEquals(vec5.toString(), "(-4, 2)");
        assertEquals(vec6.toString(), "(17, -7)");
    }

    @Test
    public void test_precedes() {
        assertTrue(vec2.precedes(vec1));
        assertFalse(vec2.precedes(vec3));
        assertTrue(vec2.precedes(vec2));
    }

    @Test
    public void test_follows() {
        assertTrue(vec1.follows(vec2));
        assertFalse(vec3.follows(vec2));
        assertTrue(vec4.follows(vec4));
    }

    @Test
    public void test_upperRight() {
        assertEquals(vec1.upperRight(vec2), new Vector2d(7, 2));
        assertEquals(vec2.upperRight(vec3), new Vector2d(17, 2));
        assertEquals(vec3.upperRight(vec4), new Vector2d(17, 2));
        assertEquals(vec3.upperRight(vec3), vec3);
        assertEquals(vec5.upperRight(vec5), vec5);
        assertEquals(vec6.upperRight(vec6), vec6);
    }

    @Test
    public void test_lowerLeft() {
        assertEquals(vec1.lowerLeft(vec2), new Vector2d(-4, 2));
        assertEquals(vec2.lowerLeft(vec3), new Vector2d(-4, -7));
        assertEquals(vec3.lowerLeft(vec4), new Vector2d(7, -7));
        assertEquals(vec3.lowerLeft(vec3), vec3);
        assertEquals(vec5.lowerLeft(vec5), vec5);
        assertEquals(vec6.lowerLeft(vec6), vec6);
    }

    @Test
    public void test_add() {
        assertEquals(vec1.add(vec2), new Vector2d(3, 4));
        assertEquals(vec2.add(vec3), new Vector2d(13, -5));
        assertEquals(vec3.add(vec4), new Vector2d(24, -5));
        assertEquals(vec1.add(vec1), new Vector2d(7 + 7, 2 + 2));
    }

    @Test
    public void test_subtract() {
        assertEquals(vec1.subtract(vec2), new Vector2d(11, 0));
        assertEquals(vec2.subtract(vec3), new Vector2d(-21, 9));
        assertEquals(vec3.subtract(vec4), new Vector2d(10, -9));
        assertEquals(vec1.subtract(vec1), new Vector2d(0, 0));
        assertEquals(vec2.subtract(vec2), new Vector2d(0, 0));
    }

    @Test
    public void test_equals() {
        assertEquals(vec1, vec4);
        assertEquals(vec2, vec5);
        assertEquals(vec3, vec6);
        assertNotEquals(vec1, vec2);
        assertNotEquals(vec2, vec3);
        assertNotEquals(vec3, vec4);
    }

    @Test
    public void test_opposite() {
        assertEquals(vec1.opposite(), new Vector2d(-7, -2));
        assertEquals(vec2.opposite(), new Vector2d(4, -2));
        assertEquals(vec3.opposite(), new Vector2d(-17, 7));
    }
}
