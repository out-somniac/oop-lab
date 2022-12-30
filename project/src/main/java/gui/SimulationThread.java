package gui;

import simulation.SimulationEngine;
import simulation.Tile;
import simulation.Vector2d;

import java.util.Map;

public class SimulationThread extends Thread {
    private volatile boolean paused = false;
    private volatile boolean running = true;
    private final Object lock = new Object();
    private final SimulationEngine simulationEngine;
    private final App application;

    private final int frameRate;

    SimulationThread(SimulationEngine simulationEngine, App application, int frameRate) {
        this.simulationEngine = simulationEngine;
        this.application = application;
        this.frameRate = frameRate;
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
        long now;
        long updateTime;
        long wait;
        final long optimalTime = 1000000000 / frameRate;
        while (running) {
            now = System.nanoTime();

            synchronized (lock) {
                while (paused) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            executeCycle();

            updateTime = System.nanoTime() - now;
            wait = (optimalTime - updateTime) / 1000000;
            try {
                Thread.sleep((wait > 0) ? wait : 200);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    void executeCycle() {
        simulationEngine.simulateDay();
        Tile[][] tiles = simulationEngine.getTiles();
        application.renderFrame(tiles);
    }
}
