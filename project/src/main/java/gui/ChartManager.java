package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartManager {


    int nrOfDays;
    int curIndex = 0;

    private final LineChart<Number, Number> lineChart;

    XYChart.Series<Number, Number> animalSeries = new XYChart.Series<>();
    XYChart.Series<Number, Number> plantsSeries = new XYChart.Series<>();

    ObservableList<XYChart.Data<Number, Number>> animalsData = FXCollections.observableArrayList();
    ObservableList<XYChart.Data<Number, Number>> plantsData = FXCollections.observableArrayList();

    public ChartManager(int n) {

        this.nrOfDays = n;

        lineChart = createLineChart();
        lineChart.setAnimated(false);

        animalSeries.setName("Number of animals");
        plantsSeries.setName("Number of plants");

        for (int i = 0; i < nrOfDays; i++) {
            animalsData.add(new XYChart.Data<>(i, 0));
            plantsData.add(new XYChart.Data<>(i, 0));
        }

        animalSeries.setData(animalsData);
        plantsSeries.setData(plantsData);

        lineChart.getData().add(animalSeries);
        lineChart.getData().add(plantsSeries);
        lineChart.setCreateSymbols(false);

    }

    private LineChart<Number, Number> createLineChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setMinorTickVisible(false);

        NumberAxis yAxis = new NumberAxis();
//        yAxis.setTickLabelsVisible(false);
        yAxis.setMinorTickVisible(false);

        return new LineChart<>(xAxis, yAxis);
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }

    public void updateGraph(int plantCount, int animalCount) {
        plantsData.get(curIndex).setYValue(plantCount);
        animalsData.get(curIndex).setYValue(animalCount);
        curIndex = (curIndex + 1) % nrOfDays;
    }


}
