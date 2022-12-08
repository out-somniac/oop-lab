package agh.ics.oop.gui;

import java.util.Vector;

import agh.ics.oop.core.SimulationEngine;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.enums.MapDirection;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.utils.OptionsParser;
import agh.ics.oop.utils.TextureManager;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private final int TILE_SIZE = 40;
    private final String title = "Window";
    private MapDirection orientation = MapDirection.NORTH;
    private final GridPane grid_pane = new GridPane();
    private final TextureManager texture_manager = new TextureManager();

    private void setupGridPane(AbstractWorldMap map) {
        grid_pane.setGridLinesVisible(true);
        grid_pane.setMinWidth(WIDTH);
        grid_pane.setMinHeight(HEIGHT);
        grid_pane.setAlignment(Pos.CENTER);

        {
            Label label = new Label("y/x");
            GridPane.setHalignment(label, HPos.CENTER);
            grid_pane.add(label, 0, 0);
            grid_pane.getColumnConstraints().add(new ColumnConstraints(TILE_SIZE));
            grid_pane.getRowConstraints().add(new RowConstraints(TILE_SIZE));

        }
        Vector2d lower_left = map.lowerLeft();
        Vector2d upper_right = map.upperRight();
        for (int i = 1; i <= upper_right.x - lower_left.x + 1; i++) {
            Label label = new Label(Integer.toString(lower_left.x + i - 1));
            grid_pane.getColumnConstraints().add(new ColumnConstraints(TILE_SIZE));
            GridPane.setHalignment(label, HPos.CENTER);
            grid_pane.add(label, i, 0);
        }

        for (int i = 1; i <= upper_right.y - lower_left.y + 1; i++) {
            Label label = new Label(Integer.toString(upper_right.y - i + 1));
            grid_pane.getRowConstraints().add(new RowConstraints(TILE_SIZE));
            GridPane.setHalignment(label, HPos.CENTER);
            grid_pane.add(label, 0, i);
        }

        for (int i = lower_left.x; i <= upper_right.x; i++) {
            for (int j = lower_left.y; j <= upper_right.y; j++) {
                int screen_x = i - lower_left.x + 1;
                int screen_y = upper_right.y - j + 1;
                Vector2d world_position = new Vector2d(i, j);
                if (map.isOccupied(world_position)) {
                    GuiElementBox element = new GuiElementBox(map.objectAt(world_position), this.texture_manager);
                    VBox v_box = element.getVBox();
                    GridPane.setHalignment(v_box, HPos.CENTER);
                    grid_pane.add(v_box, screen_x, screen_y);
                }
            }
        }
    }

    public void renderMap(AbstractWorldMap map) {
        grid_pane.setGridLinesVisible(false);
        grid_pane.getColumnConstraints().clear();
        grid_pane.getRowConstraints().clear();
        grid_pane.getChildren().clear();
        grid_pane.setGridLinesVisible(true);
        setupGridPane(map);
    }

    @Override
    public void start(Stage primary_stage) {
        try {
            TextField text_field = new TextField();
            Button start_button = getStartButton(text_field);
            Button direction_button = getDirectionButton();
            HBox hBox = new HBox(grid_pane, text_field, start_button, direction_button);
            Scene scene = new Scene(hBox, WIDTH, HEIGHT);
            primary_stage.setScene(scene);
            primary_stage.setTitle(this.title);
            primary_stage.show();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public Button getStartButton(TextField inputTextField) {
        Button startButton = new Button("Start");
        startButton.setOnAction((action) -> {
            String text = inputTextField.getText();
            MoveDirection[] directions = OptionsParser.parse(text.split(" "));
            Vector2d[] positions = { new Vector2d(1, 3), new Vector2d(2, -1) };
            GrassField map = new GrassField(10);
            IEngine engine = new SimulationEngine(directions, map, positions, this);
            Thread engineThread = new Thread(engine::run);
            engineThread.start();
        });
        return startButton;
    }

    public Button getDirectionButton() {
        Button directionButton = new Button(orientation.toString());
        directionButton.setOnAction((action) -> {
            this.orientation = orientation.next();
            directionButton.setText(this.orientation.toString());
        });
        return directionButton;
    }
}
