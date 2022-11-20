package agh.ics.oop.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import agh.ics.oop.enums.MoveDirection;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] result = new MoveDirection[0]; // Possible unwanted allocation. Still better than returning null
        try {
            result = argsToDirections(args).stream()
                    .toArray(MoveDirection[]::new);

        } catch (IllegalArgumentException ex) {
            System.err.println(ex);
        }
        return result;
    }

    private static MoveDirection stringToDirection(String str) throws IllegalArgumentException {
        return switch (str.toLowerCase()) {
            case "f", "forward" -> MoveDirection.FORWARD;
            case "b", "backward" -> MoveDirection.BACKWARD;
            case "l", "left" -> MoveDirection.LEFT;
            case "r", "right" -> MoveDirection.RIGHT;
            default -> throw new IllegalArgumentException(str + " is not a valid argument!");
        };
    }

    private static List<MoveDirection> argsToDirections(String[] args) {
        return Arrays.stream(args)
                .map(OptionsParser::stringToDirection)
                .collect(Collectors.toList());
    }
}
