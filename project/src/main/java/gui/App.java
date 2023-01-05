package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simulation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class App extends Application {

    private double appWidthPx = 1400;
    private double appHeightPx = 900;

    private final Border SIMPLE_BORDER = new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, null,
            new BorderWidths(1)));

    private MapVisualisation simulationGrid;

    private SimulationThread simulationThread;
    private final Button pauseButton = new Button("Pause");
    private final Button startButton = new Button("Start Simulation");

    private final Label dayLabel = new Label("Current Day: ");
    private final Label avgLifespanLabel = new Label("Average Lifespan: ");
    private final Label avgEnergy = new Label("Average energy: ");

    String windowName;

    // delay between day simulation
    private int stepTime = 200;
    private Configuration config;
    private AnimalVisualisation animalVisualisation = new AnimalVisualisation();
    private ChartManager chartManager;
    private SimulationEngine simulation;
    private volatile boolean pausedByUser = false;

    App(String windowName, Configuration config) {
        super();
        this.windowName = windowName;
        this.config = config;
        fileName = "src/main/resources/SimulationStats-%s-%d.csv".formatted(windowName, System.currentTimeMillis());
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(generateScene());
        primaryStage.setTitle(windowName);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> this.simulationThread.killThread());

    }

    private Scene generateScene() {
        VBox stats = new VBox(5);

        pauseButton.setPrefWidth(150);
        pauseButton.setOnAction(event -> {
            if (pauseButton.getText().equals("Pause")) {
                pauseButton.setText("Unpause");
                simulationThread.setPaused(true);
                pausedByUser = true;
            } else {
                pauseButton.setText("Pause");
                simulationThread.setPaused(false);
                pausedByUser = false;
            }
        });

        startButton.setPrefWidth(150);
        startButton.setOnAction(event -> {
            startButton.setDisable(true);
            saveToFile(header);
            simulationThread.start();
        });

        animalVisualisation.setBorder(SIMPLE_BORDER);

        HBox buttonsBox = new HBox(10, startButton, pauseButton);
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        stats.setMinWidth(200);
        stats.setMaxWidth(600);
        stats.setPadding(new Insets(10));
        stats.setAlignment(Pos.TOP_CENTER);

        int displayNDays = 100;

        chartManager = new ChartManager(displayNDays);
        chartManager.getLineChart().setBorder(SIMPLE_BORDER);
        chartManager.getLineChart().setTitle("Animal and plant population graph");

        stats.getChildren().addAll(buttonsBox, chartManager.getLineChart(), animalVisualisation, new HBox(10, dayLabel, avgLifespanLabel, avgEnergy));

        createSimulation();

        simulationGrid = new MapVisualisation(config, this);
        simulationGrid.setPrefWidth(0);
        VBox simBox = new VBox(simulationGrid);
        simBox.setPadding(new Insets(10));
        simBox.setAlignment(Pos.TOP_CENTER);
        HBox hBox = new HBox(stats, simBox);

        simulationThread = new SimulationThread(simulation, this, config.getFrameRate());
        return new Scene(hBox);
    }


    private String header = "dayNr; nrOfAnimals; animalsBorn; averageEnergy; plantCount; lifespanOfDead";
    private String fileName;

    private void createSimulation() {
        simulation = new SimulationEngine(config, new PortalMap(config), new LushEquatorsVegetation(config));
    }

    public void renderFrame(Tile[][] tileMap) {
        simulationThread.setPaused(true);
        Platform.runLater(() -> {
            simulationGrid.draw(tileMap);
            animalVisualisation.update();
            Statistics dayStats = simulation.generateDaySummary();
            chartManager.updateGraph(dayStats.plantCount(), dayStats.nrOfAnimals());
            dayLabel.setText("Current Day: " + dayStats.dayNr());
            avgLifespanLabel.setText("Average Lifespan: %.2f".formatted(dayStats.lifespanOfDead()));
            avgEnergy.setText("Average energy: %.2f".formatted(dayStats.averageEnergy()));
            saveToFile(dayStats.getCSVFormat());
            if (!pausedByUser) {
                simulationThread.setPaused(false);
            }
        });
    }

    public void trackAnimal(Animal animal) {
        Platform.runLater(() -> {
            animalVisualisation.visualizeAnimal(animal);
        });

    }

    private void saveToFile(String str) {
        try( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.newLine();
            writer.write(str);
        } catch (IOException e) {
            System.err.println("Failed to write to file!");
        } ;
    }

}
