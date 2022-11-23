package agh.ics.oop.gui;

import java.util.Vector;

import agh.ics.oop.core.SimulationEngine;
import agh.ics.oop.core.Vector2d;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.utils.OptionsParser;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {
    private final int WIDTH = 400;
    private final int HEIGHT = 400;
    private final String title = "Window";

    private GridPane createGridPane(AbstractWorldMap map) {
        GridPane grid_pane = new GridPane();
        grid_pane.setGridLinesVisible(true);
        grid_pane.setMinWidth(WIDTH);
        grid_pane.setMinHeight(HEIGHT);
        grid_pane.setAlignment(Pos.CENTER);

        {
            Label label = new Label("y/x");
            GridPane.setHalignment(label, HPos.CENTER);
            grid_pane.add(label, 0, 0);
            grid_pane.getColumnConstraints().add(new ColumnConstraints(30));
            grid_pane.getRowConstraints().add(new RowConstraints(30));

        }
        Vector2d lower_left = map.lowerLeft();
        Vector2d upper_right = map.upperRight();
        for (int i = 1; i <= upper_right.x - lower_left.x + 1; i++) {
            Label label = new Label(Integer.toString(lower_left.x + i - 1));
            grid_pane.getColumnConstraints().add(new ColumnConstraints(30));
            GridPane.setHalignment(label, HPos.CENTER);
            grid_pane.add(label, i, 0);
        }

        for (int i = 1; i <= upper_right.y - lower_left.y + 1; i++) {
            Label label = new Label(Integer.toString(upper_right.y - i + 1));
            grid_pane.getRowConstraints().add(new RowConstraints(30));
            GridPane.setHalignment(label, HPos.CENTER);
            grid_pane.add(label, 0, i);
        }

        for (int i = lower_left.x; i <= upper_right.x; i++) {
            for (int j = lower_left.y; j <= upper_right.y; j++) {
                int screen_x = i - lower_left.x + 1;
                int screen_y = upper_right.y - j + 1;
                if (map.isOccupied(new Vector2d(i, j))) {
                    Label label = new Label("#");
                    GridPane.setHalignment(label, HPos.CENTER);
                    grid_pane.add(label, screen_x, screen_y);
                }
            }
        }
        return grid_pane;
    }

    @Override
    public void start(Stage primary_stage) {
        String[] args = this.getParameters().getRaw().toArray(String[]::new);
        MoveDirection[] directions = OptionsParser.parse(args);
        Vector2d[] positions = { new Vector2d(3, -1), new Vector2d(1, 2) };
        GrassField map = new GrassField(10);
        // IEngine engine = new SimulationEngine(directions, map, positions);
        // Thread engine_thread = new Thread(engine::run);
        // engine_thread.start();

        GridPane pane = createGridPane(map);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primary_stage.setTitle(this.title);
        primary_stage.setScene(scene);
        primary_stage.show();
    }
}
