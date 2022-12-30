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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import simulation.Configuration;
import simulation.exceptions.InvalidConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationLauncher extends Application {

    private double appWidthPx = 500;
    private double appHeightPx = 800;
    private ArrayList<TextField> fields = new ArrayList<TextField>();
    private Label infoLabel = new Label("");
    private BorderPane mainPane = new BorderPane();

    private String toSpaced(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase().replaceAll("_", " ");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button launchButton = new Button("Launch New Simulation");

        AtomicInteger windowCount = new AtomicInteger();
        launchButton.setOnAction(event -> {
            windowCount.getAndIncrement();
            Platform.runLater(() -> {
                try {
                    new App("Simulation #" + windowCount.get(), getCurrentConfig()).start(new Stage());
                } catch (InvalidConfiguration e) {
                    this.infoLabel.setText(e.getMessage());
                }
            });
        });

        VBox formVbox = new VBox(5);
        formVbox.setAlignment(Pos.CENTER);
        formVbox.getChildren().add(new Label("Current configuration"));
        String[] fieldNames = Configuration.requiredKeys;
        for (int i = 0; i < fieldNames.length; i++) {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(10);

            Label label = new Label(toSpaced(fieldNames[i]));
            label.setPrefWidth(200);
            this.fields.add(new TextField());
            hbox.getChildren().addAll(label, this.fields.get(i));
            formVbox.getChildren().add(hbox);
        }

        HBox controls = new HBox(10, launchButton);
        controls.setAlignment(Pos.CENTER);
        launchButton.setAlignment(Pos.CENTER);
        formVbox.getChildren().add(controls);

        formVbox.getChildren().add(infoLabel);

        this.mainPane.setPadding(new Insets(0, 0, 20, 0));
        this.mainPane.setTop(createNavBar());
        this.mainPane.setCenter(formVbox);
        this.mainPane.setBottom(controls);

        Platform.setImplicitExit(true);

        Scene scene = new Scene(mainPane, appWidthPx, appHeightPx);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Animals Simulation");
        primaryStage.show();
    }

    private MenuBar createNavBar() {
        Menu configMenu = new Menu("_Configuration");
        configMenu.setMnemonicParsing(true);

        MenuItem loadConfig = new MenuItem("Load Config");
        loadConfig.setOnAction(event -> {
            try {
                File file = this.getUserFilePath();
                Configuration config = new Configuration(file.getAbsolutePath());
                String[] configFields = config.getAllFields();
                for (int i = 0; i < configFields.length; i++) {
                    this.fields.get(i).setText(configFields[i]);
                }
            } catch (FileNotFoundException ex) {
                this.infoLabel.setText("Can not open configuration file!");
            } catch (InvalidConfiguration ex) {
                this.infoLabel.setText("Invalid configuration");
            }
        });
        MenuItem saveConfig = new MenuItem("Save Config");
        saveConfig.setOnAction(event -> {
            try {
                File file = this.getUserFilePath();
                Configuration config = this.getCurrentConfig();
                config.saveToFile(file.getAbsolutePath());
            } catch (IOException ex) {
                this.infoLabel.setText("Can not open configuration file!");
            } catch (InvalidConfiguration ex) {
                this.infoLabel.setText("Invalid configuration");
            }
        });
        configMenu.getItems().addAll(loadConfig, saveConfig);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(configMenu);
        return menuBar;
    }

    private File getUserFilePath() throws FileNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Configuration filepath");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CONFIG", "*.conf"));

        File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
        if (file != null) {
            return file;
        }
        throw new FileNotFoundException("Configuration file not found");
    }

    private Configuration getCurrentConfig() throws InvalidConfiguration {
        String[] field_values = fields.stream().map(textField -> textField.getText()).toArray(String[]::new);
        return new Configuration(field_values);
    }
}
