package agh.ics.oop.lab3;

import agh.ics.oop.lab2.MoveDirection;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        return argsToDirections(args).stream()
                .filter(direction -> direction != null)
                .toArray(MoveDirection[]::new);
    }

    private static MoveDirection stringToDirection(String str) {
        return switch (str.toLowerCase()) {
            case "f", "forward" -> MoveDirection.FORWARD;
            case "b", "backward" -> MoveDirection.BACKWARD;
            case "l", "left" -> MoveDirection.LEFT;
            case "r", "right" -> MoveDirection.RIGHT;
            default -> null;
        };
    }

    private static List<MoveDirection> argsToDirections(String[] args) {
        return Arrays.stream(args)
                .map(OptionsParser::stringToDirection)
                .collect(Collectors.toList());
    }
}
