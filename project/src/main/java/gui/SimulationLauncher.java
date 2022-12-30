package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import simulation.Configuration;
import simulation.LushEquatorsVegetation;
import simulation.PortalMap;
import simulation.SimulationEngine;
import simulation.exceptions.InvalidConfiguration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationLauncher extends Application {

    private double appWidthPx = 500;
    private double appHeightPx = 500;
    private ArrayList<TextField> fields = new ArrayList<TextField>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create a button to launch the new window
        Button launchButton = new Button("Launch New Simulation");
        Button checkButton = new Button("Check configuration validity");

        AtomicInteger windowCount = new AtomicInteger();
        launchButton.setOnAction(event -> {
            // Increment the window count
            windowCount.getAndIncrement();
            String[] field_values = fields.stream().map(textField -> textField.getText()).toArray(String[]::new);
            Platform.runLater(() -> {
                try {
                    new App("Simulation #" + windowCount.get(),
                            new Configuration(field_values)).start(new Stage());
                } catch (InvalidConfiguration e) {
                    e.printStackTrace();
                }
            });
        });
        // Create a VBox layout container to arrange the elements vertically
        VBox formVbox = new VBox(5);
        formVbox.setAlignment(Pos.CENTER);
        formVbox.getChildren().add(new Label("Current configuration"));
        String[] fieldNames = { "width", "height", "plants_initial_total", "plant_energy", "plant_growth",
                "animals_initial_total", "starting_energy", "full_energy", "max_energy", "daily_energy_loss",
                "creation_energy", "min_mutations", "max_mutations", "genome_length", "energy_penalty" };
        for (int i = 0; i < fieldNames.length; i++) {
            // Create an HBox layout container to arrange the label and text field
            // horizontally
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(10);

            Label label = new Label(fieldNames[i]);
            label.setPrefWidth(200);
            this.fields.add(new TextField());
            hbox.getChildren().addAll(label, this.fields.get(i));

            // Add the HBox to the VBox
            formVbox.getChildren().add(hbox);
        }

        HBox controls = new HBox(10, launchButton, checkButton);
        controls.setAlignment(Pos.CENTER);

        launchButton.setAlignment(Pos.CENTER);
        formVbox.getChildren().add(controls);

        BorderPane mainPane = new BorderPane();
        mainPane.setPadding(new Insets(0, 0, 20, 0));
        mainPane.setTop(createNavBar());
        mainPane.setCenter(formVbox);
        mainPane.setBottom(controls);

        Scene scene = new Scene(mainPane, appWidthPx, appHeightPx);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Animals Simulation");
        primaryStage.show();
    }

    private MenuBar createNavBar() {
        Menu configMenu = new Menu("_Configuration");
        configMenu.setMnemonicParsing(true);

        // Create a submenu for the configuration menu
        MenuItem loadConfig = new MenuItem("Load Config");
        MenuItem saveConfig = new MenuItem("Save Config");
        configMenu.getItems().addAll(loadConfig, saveConfig);

        // Create a menu bar and add the three menus
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(configMenu);
        return menuBar;
    }
}