package agh.ics.oop.lab1;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        run(argsToDirections(args));
        System.out.println("Stop");
    }

    private static void run(List<Direction> directions) {
        directions.stream()
                .filter(direction -> direction != null)
                .forEach(direction -> System.out.println(directionToMessage(direction)));
    }
    private static List<Direction> argsToDirections(String[] args) {
        return Arrays.stream(args)
                .map(World::stringToDirection)
                .collect(Collectors.toList());
    }
    private static Direction stringToDirection(String str) {
        return switch(str) {
            case "f" -> Direction.FORWARD;
            case "b" -> Direction.BACKWARD;
            case "l" -> Direction.LEFT;
            case "r" -> Direction.RIGHT;
            default -> null;
        };
    }

    private static String directionToMessage(Direction dir) {
        return switch(dir) {
            case FORWARD -> "Szynszyl idzie do przodu";
            case BACKWARD -> "Szynszyl idzie do tyłu";
            case LEFT -> "Szynszyl idzie w lewo";
            case RIGHT -> "Szynszyl idzie w prawo";
        };
    }
}
