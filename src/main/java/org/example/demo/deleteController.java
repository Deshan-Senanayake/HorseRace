package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class deleteController {
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField horseIDTextField;

    @FXML
    private Label horseName;

    @FXML
    private Label jockeyName;

    @FXML
    private Label age;

    @FXML
    private Label breed;

    @FXML
    private Label raceRecord;

    @FXML
    private Label group;

    @FXML
    private Label imagePath;

    private final String horseDetailsFilePath = "horse details.txt";

    @FXML
    public void loadHorseDetails(ActionEvent event) {
        String searchID = horseIDTextField.getText();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(horseDetailsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String horseID = parts[0];
                if (horseID.equals(searchID)) {
                    horseName.setText(parts[1]);
                    jockeyName.setText(parts[2]);
                    age.setText(parts[3]);
                    breed.setText(parts[4]);
                    raceRecord.setText(parts[5]);
                    group.setText(parts[6]);
                    imagePath.setText(parts[7]);
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            showAlert("Error loading data: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            showAlert("Error parsing data: " + e.getMessage());
        }

        if (!found) {
            showAlert("No data found for the entered horse ID.");
        }
    }

    @FXML
    public void deleteHorse(ActionEvent event) {
        String searchID = horseIDTextField.getText();
        StringBuilder updatedData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(horseDetailsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String horseID = parts[0];
                if (!horseID.equals(searchID)) {
                    updatedData.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            showAlert("Error updating data: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(horseDetailsFilePath))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            showAlert("Error saving changes: " + e.getMessage());
            return;
        }

        clearFields();

        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Horse deleted successfully");
        successAlert.showAndWait();
    }

    @FXML
    protected void onMainMenuClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Second menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        horseIDTextField.clear();
        horseName.setText("");
        jockeyName.setText("");
        age.setText("");
        breed.setText("");
        raceRecord.setText("");
        group.setText("");
        imagePath.setText("");
    }
}
