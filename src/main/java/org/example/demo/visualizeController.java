package org.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class visualizeController {

    private Stage stage;
    private Scene scene;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    private selectHorseController.Horse firstPlace;
    private selectHorseController.Horse secondPlace;
    private selectHorseController.Horse thirdPlace;

    public void setWinningHorses(selectHorseController.Horse firstPlace, selectHorseController.Horse secondPlace, selectHorseController.Horse thirdPlace) {
        this.firstPlace = firstPlace;
        this.secondPlace = secondPlace;
        this.thirdPlace = thirdPlace;
    }

    public void initData() {
        if (firstPlace != null && secondPlace != null && thirdPlace != null) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Horse 1", firstPlace.getTime()));
            series.getData().add(new XYChart.Data<>("Horse 2", secondPlace.getTime()));
            series.getData().add(new XYChart.Data<>("Horse 3", thirdPlace.getTime()));

            barChart.getData().add(series);
        } else {
            System.out.println("Error: Winning horses data is null.");
        }
    }

    @FXML
    protected void onMainMenuClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Second menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void exit(ActionEvent event) {
        Platform.exit();
    }
}

