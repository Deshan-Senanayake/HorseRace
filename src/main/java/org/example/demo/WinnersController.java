package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WinnersController {

    private Stage stage;
    private Scene scene;

    @FXML
    private Label firstPlaceLabel;

    @FXML
    private Label secondPlaceLabel;

    @FXML
    private Label thirdPlaceLabel;

    private List<selectHorseController.Horse> semiFinalists; // List to hold the semi-finalists

    public void initialize(List<selectHorseController.Horse> semiFinalists) {
        this.semiFinalists = semiFinalists;
        // Assign random times to the semi-finalists
        assignRandomTimes(semiFinalists);
        // Select 3 horses with the least time
        List<selectHorseController.Horse> winners = selectWinners(semiFinalists);
        // Display the winners
        displayWinners(winners);
    }

    private void assignRandomTimes(List<selectHorseController.Horse> horses) {
        Random random = new Random();
        for (selectHorseController.Horse horse : horses) {
            // Generate random time between 60 and 180 seconds
            int time = random.nextInt(91);
            horse.setTime(time);
        }
    }

    private List<selectHorseController.Horse> selectWinners(List<selectHorseController.Horse> horses) {
        // Sort the horses based on time
        horses.sort((h1, h2) -> Integer.compare(h1.getTime(), h2.getTime()));
        // Select the top 3 horses
        return horses.subList(0, Math.min(3, horses.size()));
    }

    private void displayWinners(List<selectHorseController.Horse> winners) {
        // Display the information of the winners
        if (winners.size() >= 1) {
            firstPlaceLabel.setText("1st Place: " + winners.get(0).getHorseName() + "  Horse ID: " + winners.get(0).getHorseID() + ",  Group: " + winners.get(0).getGroup() + ",  Time: " + winners.get(0).getTime() + " seconds");
        }
        if (winners.size() >= 2) {
            secondPlaceLabel.setText("2nd Place: " + winners.get(1).getHorseName() + "  Horse ID: " + winners.get(1).getHorseID() + ",  Group: " + winners.get(1).getGroup() + ",  Time: " + winners.get(1).getTime() + " seconds");
        }
        if (winners.size() >= 3) {
            thirdPlaceLabel.setText("3rd Place: " + winners.get(2).getHorseName() + "  Horse ID: " + winners.get(2).getHorseID() + ",  Group: " + winners.get(2).getGroup() + ",  Time: " + winners.get(2).getTime() + " seconds");
        }
    }

    @FXML
    protected void menu(ActionEvent event) throws IOException {
        // Navigate back to the main menu
        Parent root = FXMLLoader.load(getClass().getResource("Main menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void visualize1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("visualize.fxml"));
        Parent root = loader.load();
        visualizeController barChartController = loader.getController();

        // Pass the winning horses' data to the visualizeController
        barChartController.setWinningHorses(
                semiFinalists.get(0), // First place
                semiFinalists.get(1), // Second place
                semiFinalists.get(2)  // Third place
        );
        barChartController.initData();

        // Show the new scene
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
