package agh.ics.oop.lab1;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Finalna wersja programu która przyjmuje argumenty jako stringin parsuje je do Listy enumów i zależnie od tej listy wyświetla wiadomości
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

//Wersja programu jedynie z run (bez enumów)
/*
public class World {
    public static void main(String args[])
    {
        System.out.println("Start");
        run(args);
        System.out.println("Stop");
    }

    public static void run(String[] args) {
        for(String argument : args) {
            String message = switch(argument) {
                case "f" -> "Żółw idzie do przodu";
                case "b" -> "Żółw idzie do do tylu";
                case "r" -> "Żółw idzie w prawo";
                case "l" -> "Żółw idzie w lewo";
                default -> "";
            };
            if (!message.equals("")) {
                System.out.println(message);
            }
        }
    }
}
*/