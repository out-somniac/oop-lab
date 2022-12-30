package gui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import simulation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapVisualisation extends GridPane {

    private final int rowCount;
    private final int colCount;
    private final double cellSize;
    private final double waterWidth;

    private final SimulationEngine simulation;

    private Configuration config;

    private Map<Vector2d, Tile> tileMap = new HashMap<>();


    MapVisualisation(Configuration config, double cellSize, SimulationEngine simulation) {
        this.config = config;
        this.rowCount = config.getHeight();
        this.colCount = config.getHeight();
        this.cellSize = cellSize;
        this.simulation = simulation;
        this.waterWidth = 20;


        createSimulationGrid();
        addMouseEvent();
        setBorder(new Border(new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(waterWidth, waterWidth, waterWidth, waterWidth))));
    }

    void addMouseEvent() {
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            if (x <= waterWidth || y <= waterWidth || x - waterWidth >= colCount * cellSize || y - waterWidth >= rowCount * cellSize)
                return;

            // Calculate the row and column indices of the clicked cell
            int row = (int) ((y - waterWidth) / cellSize);
            int col = (int) ((x - waterWidth) / cellSize);

//            drawCircleWithText(col, row, String.valueOf(row * colCount + col), 1);
            Vector2d position = new Vector2d(col, row);
            if(this.tileMap.containsKey(position)) {
                List<Animal> animalList = this.tileMap.get(position).animalList();
                if(animalList != null && animalList.size() > 0) {
                    Animal animal = animalList.get(0);
                    System.out.println(animal);
                    System.out.println(animal.getGenotype());
                }
            }
        });
    }
    Color noEnergyColor = Color.valueOf("#ff0000");
    Color maxEnergyColor = Color.valueOf("#13b6ff");


    private void createSimulationGrid() {
//         create x-axis column
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

    public void drawCircleWithText(int x, int y, String str, double colorRatio) {
        StackPane stackPane = (StackPane) getChildren().get(y * colCount + x);

        int r = (int) cellSize/2;
        Circle circle = new Circle(r, r, r - 2);
        circle.setFill(noEnergyColor.interpolate(maxEnergyColor, colorRatio));
        Text text = new Text(str);
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font-size: 12pt");
        stackPane.getChildren().addAll(circle, text);
        stackPane.setAlignment(Pos.CENTER);
    }

    public void draw(Map<Vector2d, Tile> tileMap) {
        this.tileMap = tileMap;

        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                StackPane stackPane = (StackPane) getChildren().get(i * colCount + j);
                Node node = stackPane.getChildren().get(0);
                stackPane.getChildren().clear();
                stackPane.getChildren().add(node);
                Tile tile = tileMap.get(new Vector2d(j, i));
                if (tile != null) {
                    if (tile.hasPlant()) {
                        Rectangle rect = new Rectangle(0, 100, cellSize - 2, cellSize / 2);
                        rect.setFill(Color.GREEN);
                        stackPane.getChildren().add(rect);
                    }
                    List<Animal> animalList = tile.animalList();
                    if (animalList != null) {
                        drawCircleWithText(j, i,
                                animalList.size() > 1 ? String.valueOf(animalList.size()) : "",
                                animalList.stream().mapToInt(animal -> animal.energy).average().orElse(0f)
                                        / config.getMaxEnergy());
                    }
                }
            }
        }
    }
}
