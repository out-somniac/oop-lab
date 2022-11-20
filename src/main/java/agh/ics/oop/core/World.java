package agh.ics.oop.core;

import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.utils.OptionsParser;

public class World {
    public static void main(String[] args) {
        MoveDirection[] directions = OptionsParser.parse("f b r l f f r r f f f f f f f f".split(" "));
        AbstractWorldMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(3, 4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        try {
            engine.run();
        } catch (IllegalArgumentException ex) {
            System.err.println(ex);
        }
    }
}
