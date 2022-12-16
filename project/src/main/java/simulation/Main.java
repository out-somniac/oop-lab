package simulation;

import simulation.exceptions.InvalidConfiguration;

public class Main {
    public static void main(String[] args) {
        try {
            Configuration config = new Configuration("src/main/resources/correct.conf"); // TODO: currently a hard coded
            Simulation sim = new Simulation(config);
            sim.run();
        } catch (InvalidConfiguration ex) {
            System.err.println(ex.getMessage());
        }

    }
}