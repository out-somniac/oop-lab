package gui;

import simulation.SimulationEngine;
import simulation.Tile;

public class SimulationThread extends Thread {
    private volatile boolean paused = false;
    private volatile boolean running = true;
    private final Object lock = new Object();
    private final SimulationEngine simulationEngine;
    private final App application;
    private final int daysPerSecond;


    SimulationThread(SimulationEngine simulationEngine, App application, int daysPerSecond) {
        this.simulationEngine = simulationEngine;
        this.application = application;
        this.daysPerSecond = daysPerSecond;
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
        long frameStartTime;
        long frameFinishedTime;
        // time per frame in milliseconds
        final long timePerUpdate = 1000 / daysPerSecond;
        while (running) {
            frameStartTime = System.currentTimeMillis();

            executeCycle();

            synchronized (lock) {
                while (paused) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            frameFinishedTime = System.currentTimeMillis();


            long frameDuration = frameFinishedTime - frameStartTime;

            if (timePerUpdate > frameDuration) {
                try {
                    Thread.sleep(timePerUpdate - frameDuration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    void executeCycle() {
        simulationEngine.simulateDay();
        Tile[][] tiles = simulationEngine.getTiles();
        application.renderFrame(tiles);
    }
}
