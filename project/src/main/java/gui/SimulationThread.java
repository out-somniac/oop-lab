package gui;

import simulation.SimulationEngine;
import simulation.Tile;
import simulation.Vector2d;

import java.util.Map;

public class SimulationThread extends Thread {
    private volatile boolean paused  = false;
    private volatile boolean running  = true;
    private final Object lock = new Object();
    private final SimulationEngine simulationEngine;
    private final App application;

    private final int stepTime;

    SimulationThread(SimulationEngine simulationEngine, App application, int stepTime) {
        this.simulationEngine = simulationEngine;
        this.application = application;
        this.stepTime = stepTime;
    }

    public void setPaused(boolean shouldPause) {
        synchronized (lock) {
            paused = shouldPause;
            if (!paused) {
                lock.notify();
            }
        }
    }

    public void killThread() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                while (paused) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {}
                }
            }
            doCycle();
        }

    }
    void doCycle() {
        simulationEngine.simulateDay();
        Map<Vector2d, Tile> tiles = simulationEngine.getTiles();
        application.renderMap(tiles);
        try {
            Thread.sleep(stepTime);
        } catch (InterruptedException e) {
            System.out.println("simulationEngine Thread Interrupted!");
            throw new RuntimeException(e);
        }
    }
}
