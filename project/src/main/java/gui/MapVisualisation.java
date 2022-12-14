package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import simulation.*;

import java.util.List;

public class MapVisualisation extends GridPane {

    private final int rowCount;
    private final int colCount;
    private final double cellSize;
    private final double waterWidth;

    private final App app;

    private final Configuration config;

    private final Animal[][] animals;

    private final int r;


    private Animal trackedAnimal = null;

    MapVisualisation(Configuration config, App app) {
        this.config = config;
        this.rowCount = config.getHeight();
        this.colCount = config.getWidth();
        this.cellSize = calculateCellSize(this.rowCount, this.colCount);
        this.app = app;
        this.waterWidth = 20;
        this.r = (int) (cellSize / 2);
        this.animals = new Animal[colCount][rowCount];

        createSimulationGrid();
        addMouseEvent();

    }

    private double calculateCellSize(int rowCount, int colCount) {
        int maxVal = Math.max(rowCount, colCount);
        return switch ((int) maxVal / 10) {
            case 0 -> 60;
            case 1 -> 45;
            case 2 -> 30;
            case 3 -> 20;
            case 4, 5 -> 15;
            default -> 10;
        };
    }

    void addMouseEvent() {
        setCursor(Cursor.HAND);
        setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            if (x <= waterWidth || y <= waterWidth || x - waterWidth >= colCount * cellSize
                    || y - waterWidth >= rowCount * cellSize)
                return;

            int row = (int) ((y - waterWidth) / cellSize);
            int col = (int) ((x - waterWidth) / cellSize);


            if(this.animals[col][row] != null) {
                if(trackedAnimal != null) {
                    Vector2d position = trackedAnimal.getPosition();
                    StackPane stackPane = (StackPane) getChildren().get(position.y * colCount + position.x);
                    stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
                }
                app.trackAnimal(this.animals[col][row]);

                trackedAnimal = this.animals[col][row];
                Vector2d position = trackedAnimal.getPosition();
                highlightAnimal(position.x, position.y);
            }
        });
    }

    Color noEnergyColor = Color.valueOf("#ff0000");
    Color maxEnergyColor = Color.valueOf("#13b6ff");

    private void createSimulationGrid() {
        for (int i = 0; i < colCount; ++i) {
            getColumnConstraints().add(new ColumnConstraints(cellSize));
        }
        for (int i = 0; i < rowCount; ++i) {
            getRowConstraints().add(new RowConstraints(cellSize));

        }

        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < colCount; ++j) {
                StackPane stackPane = new StackPane();
                stackPane.setBackground(
                        new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                stackPane.setAlignment(Pos.CENTER);
                add(stackPane, j, i, 1, 1);
            }
        }
        setBorder(new Border(new BorderStroke(Color.DARKRED,
                BorderStrokeStyle.SOLID, null,
                new BorderWidths(waterWidth))));
    }

    public void drawCircleWithText(int x, int y, String str, double colorRatio) {
        StackPane stackPane = (StackPane) getChildren().get(y * colCount + x);

        Circle circle = new Circle(r, r, r - 2);
        circle.setFill(noEnergyColor.interpolate(maxEnergyColor, colorRatio));

        stackPane.getChildren().add(circle);
        if (!str.equals("") && cellSize > 15) {
            Text text = new Text(str);
            text.setFill(Color.BLACK);
            text.setStyle("-fx-font-size: 12pt");
            stackPane.getChildren().add(text);
        }
    }

    public void draw(Tile[][] tileMap) {
        for (int y = 0; y < rowCount; ++y) {
            for (int x = 0; x < colCount; ++x) {
                StackPane stackPane = (StackPane) getChildren().get(y * colCount + x);
                stackPane.getChildren().clear();
                if (tileMap[y][x].hasPlant()) {
                    Rectangle rect = new Rectangle(cellSize / 2, cellSize / 2);
                    rect.setFill(Color.GREEN);
                    stackPane.getChildren().add(rect);
                }
                List<Animal> animalList = tileMap[y][x].animalList();
                animals[x][y] = (animalList != null && animalList.size() > 0) ? animalList.get(0) : null;
                if (animalList != null) {
                    drawCircleWithText(x, y,
                            animalList.size() > 1 ? String.valueOf(animalList.size()) : "",
                            animalList.stream().mapToInt(animal -> animal.energy).average().orElse(0f)
                                    / config.getMaxEnergy());
                }
            }
        }
        if(trackedAnimal != null) {
            Vector2d position = trackedAnimal.getPosition();
            highlightAnimal(position.x, position.y);
        }
    }

    private void highlightAnimal(int x, int y) {
        StackPane stackPane = (StackPane) getChildren().get(y * colCount + x);
        Circle circle = new Circle(r, r, r - 2);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(cellSize/10);
        stackPane.getChildren().add(circle);
    }
}
