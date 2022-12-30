package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartManager {

    NumberAxis xAxis;
    NumberAxis yAxis;
    int nrOfDays;

    private final LineChart<Number, Number> lineChart;

    public ChartManager(int n) {
        xAxis = new NumberAxis();
        xAxis.setTickLabelsVisible(false);
        xAxis.setMinorTickVisible(false);

        yAxis = new NumberAxis();
        yAxis.setTickLabelsVisible(false);
        yAxis.setMinorTickVisible(false);

        lineChart = new LineChart<>(xAxis, yAxis);
        this.nrOfDays = n;


        // Create the data for the first series
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Number of animals");

        ObservableList<XYChart.Data<Number, Number>> series1Data = FXCollections.observableArrayList();
        for (int i = 0; i < nrOfDays; i++) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(i, i*i);
            series1Data.add(dataPoint);
        }
        series1.setData(series1Data);
        // Add the series to the line chart
        lineChart.getData().add(series1);
        lineChart.setCreateSymbols(false);

    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }
}
