package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import simulation.*;
import simulation.exceptions.InvalidConfiguration;

import java.util.Map;

public class App extends Application {

    private double appWidthPx = 1400;
    private double appHeightPx = 900;

    private MapVisualisation simulationGrid;

    private Menu startSimMenu;
    private Menu pauseSimMenu;

    private SimulationThread simulationThread;
    private Button pauseButton;
    private Button startButton;

    // delay between day simulation
    private int stepTime = 100;

    private Configuration config;

    @Override
    public void start(Stage primaryStage) {

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

        stats.getChildren().addAll(pauseButton,  startButton, new Text("Animal Details"), new Text("Simulation Data"));
        stats.setMinWidth(300);
        stats.setMaxWidth(300);
        SimulationEngine simulation;
        try {
            config = new Configuration("src/main/resources/correct.conf");
            simulation = new SimulationEngine(config, new PortalMap(config), new LushEquatorsVegetation(config));
        } catch (InvalidConfiguration e) {
            e.printStackTrace();
            return;
        }

        simulationGrid = new MapVisualisation(config.getHeight(), config.getWidth(), 30, simulation);

        HBox hBox = new HBox(stats, simulationGrid);

        simulationThread = new SimulationThread(simulation, this, stepTime);

        VBox mainElement = new VBox(createNavBar(), hBox);


        Scene scene = new Scene(mainElement, appWidthPx, appHeightPx);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Animals Simulation");
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> simulationThread.killThread());
    }

    public void renderMap(Map<Vector2d, Tile> tileMap) {
        Platform.runLater(() -> simulationGrid.draw(tileMap));
    }

    private MenuBar createNavBar() {
        Menu configMenu = new Menu("_Configuration");
        configMenu.setMnemonicParsing(true);
        startSimMenu = new Menu("_Start Simulation");
        startSimMenu.setMnemonicParsing(true);
        pauseSimMenu = new Menu("_Pause Simulation");
        pauseSimMenu.setMnemonicParsing(true);

        // Create a submenu for the configuration menu
        MenuItem loadConfig = new MenuItem("_Load Config");
        loadConfig.setMnemonicParsing(true);
        MenuItem saveConfig = new MenuItem("_Save Config");
        saveConfig.setMnemonicParsing(true);
        MenuItem createConfig = new MenuItem("_Create Config");
        createConfig.setMnemonicParsing(true);
        configMenu.getItems().addAll(loadConfig, saveConfig, createConfig);

        // Create a menu bar and add the three menus
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(configMenu, startSimMenu, pauseSimMenu);
        return menuBar;
    }




}

