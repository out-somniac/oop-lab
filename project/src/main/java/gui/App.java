package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulation.*;

import java.util.Map;

public class App extends Application {

    private double appWidthPx = 1400;
    private double appHeightPx = 900;

    private final Border SIMPLE_BORDER = new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, null,
            new BorderWidths(1)));

    private MapVisualisation simulationGrid;



    private SimulationThread simulationThread;
    private Button pauseButton;
    private Button startButton;

    String windowName;

    // delay between day simulation
    private int stepTime = 100;

    private Configuration config;

    private AnimalVisualisation animalVisualisation = new AnimalVisualisation();

    private ChartManager chartManager;

    App(String windowName, Configuration config) {
        super();
        this.windowName = windowName;
        this.config = config;
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(generateScene());
        primaryStage.setTitle(windowName);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> simulationThread.killThread());

    }

    private Scene generateScene() {
        VBox stats = new VBox(5);
        pauseButton = new Button("Pause");
        pauseButton.setPrefWidth(150);
        pauseButton.setOnAction(event -> {
            if (pauseButton.getText().equals("Pause")) {
                pauseButton.setText("Unpause");
                simulationThread.setPaused(true);
            } else {
                pauseButton.setText("Pause");
                simulationThread.setPaused(false);
            }
        });

        startButton = new Button("Start Simulation");
        startButton.setPrefWidth(150);
        startButton.setOnAction(event -> {
            startButton.setDisable(true);
            simulationThread.start();
        });

        animalVisualisation.setBorder(SIMPLE_BORDER);

        HBox buttonsBox = new HBox(10, startButton, pauseButton);
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        stats.getChildren().addAll(buttonsBox, animalVisualisation);
        stats.setMinWidth(400);
        stats.setMaxWidth(600);
        stats.setPadding(new Insets(10));
        stats.setAlignment(Pos.TOP_CENTER);

        int displayNDays = 100;

        chartManager = new ChartManager(displayNDays);
        chartManager.getLineChart().setBorder(SIMPLE_BORDER);
        chartManager.getLineChart().setTitle("Animal and plant population graph");
        stats.getChildren().add(chartManager.getLineChart());


        SimulationEngine simulation;
        simulation = new SimulationEngine(config, new PortalMap(config), new LushEquatorsVegetation(config));


        simulationGrid = new MapVisualisation(config, 30, simulation, this);

        HBox hBox = new HBox(stats, simulationGrid);

        simulationThread = new SimulationThread(simulation, this, stepTime);


//        TableView<Animal> animalTable = createTable();
//        animalTable.setItems(new ObservableListWrapper<>(simulation.getAnimals()));
//        stats.getChildren().add(animalTable);


        return new Scene(hBox);
    }

    private TableView<Animal> createTable() {
        TableView<Animal> animalTable = new TableView<>();

        animalTable.setStyle("-fx-font-weight: bold");
        TableColumn<Animal, String> col1 = new TableColumn<>("Position");
        col1.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getPosition().toString()));

        TableColumn<Animal, String> col2 = new TableColumn<>("Direction");
        col2.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getDirection().toString()));

        TableColumn<Animal, Integer> col3 = new TableColumn<>("Energy");
        col3.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().energy));

        TableColumn<Animal, Integer> col4 = new TableColumn<>("Day of birth");
        col4.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().dayOfBirth));

        TableColumn<Animal, Integer> col5 = new TableColumn<>("Children Count");
        col5.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue().getNrOfChildren()));

        return animalTable;
    }

    // the app pauses the simulation engine until it finishes rendering the current frame
    public void renderMap(Map<Vector2d, Tile> tileMap) {
        simulationThread.setPaused(true);
        Platform.runLater(() -> {
            simulationGrid.draw(tileMap);
            simulationThread.setPaused(false);
            animalVisualisation.update();
        });
    }

    public void trackAnimal(Animal animal) {
        Platform.runLater(() -> {
            animalVisualisation.visualizeAnimal(animal);
        });

    }


}
