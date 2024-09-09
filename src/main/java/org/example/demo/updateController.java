package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class updateController {

    private Stage stage;
    private Scene scene;

    @FXML private Label horseName;
    @FXML private Label jockeyName;
    @FXML private Label age;
    @FXML private Label breed;
    @FXML private Label raceRecord;
    @FXML private Label group;
    @FXML private Label imagePath;

    @FXML private TextField newHorseNameTextField;
    @FXML private TextField newJockeyNameTextField;
    @FXML private TextField newAgeTextField;
    @FXML private TextField newBreedTextField;
    @FXML private TextField newRaceRecordTextField;
    @FXML private TextField newGroupTextField;
    @FXML private ImageView horseImageView;
    @FXML private TextField horseIDTextField;
    @FXML private BorderPane borderPane;
    @FXML private Button saveChangesButton;
    @FXML private Button resetButton;
    @FXML private Button mainMenuButton;
    @FXML private Button loadDataButton;

    @FXML private Label imageNameLabel;

    // Set to store unique horse IDs
    private Set<String> uniqueHorseIDs = new HashSet<>();

    @FXML
    void imageSaver(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files", "*.jpg", "*.png")
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imagePath.setText(selectedFile.getPath()); // Update image path text
            imageNameLabel.setText(selectedFile.getName());
            try {
                Image image = new Image(selectedFile.toURI().toString());
                horseImageView.setImage(image);
            } catch (Exception e) {
                showAlert("Error loading image: " + e.getMessage());
            }
        } else {
            showAlert("Please insert a picture of the horse.");
        }
    }

    @FXML
    public void saveChanges() {
        // Get the updated data from text fields
        String newHorseName = newHorseNameTextField.getText();
        String newJockeyName = newJockeyNameTextField.getText();
        String newAge = newAgeTextField.getText();
        String newBreed = newBreedTextField.getText();
        String newRaceRecord = newRaceRecordTextField.getText();
        String newGroup = newGroupTextField.getText();

        // Validate group input (must be A, B, C, or D) only if a value is entered
        if (!newGroup.isEmpty() && !newGroup.matches("[ABCD]")) {
            showAlert("Group must be A, B, C, or D.");
            return;
        }

        // Get the horse ID for which updates are being made
        String searchID = horseIDTextField.getText();

        // Read the existing file and store updated data
        StringBuilder updatedData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("horse details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                String horseID = parts[0];
                if (horseID.equals(searchID)) {
                    // Update the line with new data or old data if new data is skipped
                    newHorseName = newHorseName.isEmpty() ? parts[1] : newHorseName;
                    newJockeyName = newJockeyName.isEmpty() ? parts[2] : newJockeyName;

                    int updatedAge;
                    try {
                        updatedAge = newAge.isEmpty() ? Integer.parseInt(parts[3]) : Integer.parseInt(newAge);
                    } catch (NumberFormatException e) {
                        showAlert("Age must be an integer.");
                        return;
                    }

                    newBreed = newBreed.isEmpty() ? parts[4] : newBreed;
                    newRaceRecord = newRaceRecord.isEmpty() ? parts[5] : newRaceRecord;
                    newGroup = newGroup.isEmpty() ? parts[6] : newGroup;

                    // Update the line with new or old data
                    line = String.join(", ", searchID, newHorseName, newJockeyName, String.valueOf(updatedAge), newBreed, newRaceRecord, newGroup, imagePath.getText());

                    // Update labels with the new data
                    String[] updatedParts = line.split(", ");
                    horseName.setText(updatedParts[1]);
                    jockeyName.setText(updatedParts[2]);
                    age.setText(updatedParts[3]);
                    breed.setText(updatedParts[4]);
                    raceRecord.setText(updatedParts[5]);
                    group.setText(updatedParts[6]);
                    imagePath.setText(updatedParts[7]);
                }
                updatedData.append(line).append("\n");
            }
        } catch (IOException e) {
            showAlert("Error updating data: " + e.getMessage());
            return;
        }

        // Write the updated data back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("horse details.txt"))) {
            writer.write(updatedData.toString());
        } catch (IOException e) {
            showAlert("Error saving changes: " + e.getMessage());
            return;
        }

        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText("Horse details updated");
        successAlert.showAndWait();

        // Now, reload the data to update UI with the latest changes
        loadData();

        clearFields();
    }





    @FXML
    public void resetFields() {
        newHorseNameTextField.clear();
        newJockeyNameTextField.clear();
        newAgeTextField.clear();
        newBreedTextField.clear();
        newRaceRecordTextField.clear();
        newGroupTextField.clear();
        horseImageView.setImage(null);
    }

    @FXML
    public void loadData() {
        clearLabels(); // Clear labels before loading new data
        String searchID = horseIDTextField.getText();
        boolean found = false; // Flag to track if data is found for the given ID
        try (BufferedReader reader = new BufferedReader(new FileReader("horse details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse the line to extract horse details
                String[] parts = line.split(", ");

                // Assuming the format is: horseID, horseName, jockeyName, age, breed, raceRecord, group, imagePath
                String horseID = parts[0];
                if (horseID.equals(searchID)) {
                    String horseNameStr = parts[1];
                    String jockeyNameStr = parts[2];
                    int horseAge = Integer.parseInt(parts[3]);
                    String horseBreed = parts[4];
                    String horseRaceRecord = parts[5];
                    String horseGroup = parts[6];
                    String horseImagePath = parts[7];

                    // Populate the labels with the loaded data
                    horseName.setText(horseNameStr);
                    jockeyName.setText(jockeyNameStr);
                    age.setText(String.valueOf(horseAge));
                    breed.setText(horseBreed);
                    raceRecord.setText(horseRaceRecord);
                    group.setText(horseGroup);
                    imagePath.setText(horseImagePath);

                    // You may also load and display the image
                    // horseImageView.setImage(new Image(new File(horseImagePath).toURI().toString()));

                    // Set found flag to true since data is found for the given horse ID
                    found = true;

                    // Stop searching once data is found for the given horse ID
                    break;
                }
            }
        } catch (IOException e) {
            showAlert("Error loading data: " + e.getMessage());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            showAlert("Error parsing data: " + e.getMessage());
        }

        // If no data is found for the given horse ID, show an alert
        if (!found) {
            showAlert("No data found for the entered horse ID.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearLabels() {
        horseName.setText("");
        jockeyName.setText("");
        age.setText("");
        breed.setText("");
        raceRecord.setText("");
        group.setText("");
        imagePath.setText("");
    }

    private void clearFields() {
        newHorseNameTextField.clear();
        newJockeyNameTextField.clear();
        newAgeTextField.clear();
        newBreedTextField.clear();
        newRaceRecordTextField.clear();
        newGroupTextField.clear();
        horseIDTextField.clear();
        horseName.setText("");
        jockeyName.setText("");
        age.setText("");
        breed.setText("");
        raceRecord.setText("");
        group.setText("");
        imagePath.setText("");
        imageNameLabel.setText("");
        horseImageView.setImage(null);
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
