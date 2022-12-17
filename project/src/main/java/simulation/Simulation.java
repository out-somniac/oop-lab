package simulation;

import java.util.ArrayList;

public class Simulation {
    private final Configuration config;
    private final MapActionsHandler mapActionsHandler;


    public Simulation(Configuration config) {
        this.config = config;
        this.mapActionsHandler = new MapActionsHandler(this.config, new PortalMap(config), new LushEquatorsVegetation(config));
    }

    public void run() {
        System.out.println(config.getInitialAnimalsTotal());
        mapActionsHandler.createInitialAnimals(config.getInitialAnimalsTotal());
        int n = 100;
        while (n-->0) {
            mapActionsHandler.simulateDay();
            Statistics dayStats = mapActionsHandler.generateDaySummary();
            System.out.println(dayStats);
        }
    }
}
