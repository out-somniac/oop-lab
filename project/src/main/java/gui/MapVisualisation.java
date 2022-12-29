package gui;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import simulation.Animal;
import simulation.SimulationEngine;
import simulation.Tile;
import simulation.Vector2d;

import java.util.List;
import java.util.Map;

public class MapVisualisation extends GridPane {

    private final int rowCount;
    private final int colCount;
    private final double cellSize;
    private final double waterWidth;

    private final SimulationEngine simulation;

    MapVisualisation(int rowCount, int colCount, double cellSize, SimulationEngine simulation) {

        this.rowCount = rowCount;
        this.colCount = colCount;
        this.cellSize = cellSize;
        this.simulation = simulation;
        createSimulationGrid();
        addMouseEvent();
        this.waterWidth = 20;
        setBorder(new Border(new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(waterWidth, waterWidth, waterWidth, waterWidth))));
    }

    private void addMouseEvent() {
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            if(x <= waterWidth || y <= waterWidth || x - waterWidth >= colCount * cellSize || y  - waterWidth >= rowCount * cellSize)
                return;

            // Calculate the row and column indices of the clicked cell
            int row = (int) ((y - waterWidth) / cellSize);
            int col = (int) ((x - waterWidth) / cellSize);

            drawCircleAndWriteNumber(col, row, row * colCount + col);

        });

    }

    private void createSimulationGrid() {
        // create x-axis column
        for(int i = 0; i < colCount; ++i) {
            getColumnConstraints().add(new ColumnConstraints(cellSize));
        }
        // create y-axis row
        for(int i = 0; i < rowCount; ++i) {
            getRowConstraints().add(new RowConstraints(cellSize));
        }
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                Rectangle rect = new Rectangle(cellSize, cellSize);
                rect.setFill(Color.GREENYELLOW);
                rect.setStrokeWidth(1);
                rect.setStroke(Color.BLACK);
                add(new StackPane(rect), j, i, 1, 1);
            }
        }
    }

    public void drawCircleAndWriteNumber( int x, int y, int number) {
        StackPane stackPane = (StackPane) getChildren().get(y * colCount + x);

        int z = (int) cellSize/2;
        Circle circle = new Circle(z, z, z - 2);
        circle.setFill(Color.BLANCHEDALMOND);
        Text text = new Text(Integer.toString(number));
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font-size: 12pt");
        stackPane.getChildren().addAll(circle, text);
        // Center the text inside the square
    }

    public void draw(Map<Vector2d, Tile> tileMap) {
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                StackPane stackPane = (StackPane) getChildren().get(i * colCount + j);
                Node node = stackPane.getChildren().get(0);
                stackPane.getChildren().clear();
                stackPane.getChildren().add(node);
                Tile tile = tileMap.get(new Vector2d(j, i));
                if (tile != null) {
                    if (tile.hasPlant()) {
                        Rectangle rect = new Rectangle(0, 100, cellSize, cellSize / 2);
                        rect.setFill(Color.GREEN);
                        stackPane.getChildren().add(rect);
                    }
                    if (tile.animalList() != null) {
                        drawCircleAndWriteNumber(j, i, tile.animalList().size());
                    }
                }
            }
        }
    }
}
