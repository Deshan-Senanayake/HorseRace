package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class saveController {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button categorizeButton;

    @FXML
    public void initialize() {
        categorizeButton.setOnAction(event -> categorizeAndRewriteFile());
    }

    private void categorizeAndRewriteFile() {
        File inputFile = new File("horse details.txt");
        File outputFile = new File("categorized_horse_details.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String group = parts[6].trim(); // Assuming group is at index 6
                    String horseID = parts[0].trim(); // Assuming horse ID is at index 0
                    String categorizedLine = "Group " + group + " - " + line;
                    bw.write(categorizedLine);
                    bw.newLine();
                }
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Horse details saved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    protected void onMainMenuClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Second menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


}
