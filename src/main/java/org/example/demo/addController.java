package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.File;
import javafx.scene.control.Alert.AlertType;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.*;

public class addController {

    private Stage stage;
    private Scene scene;
    private Parent root;


    private String imagePath; // Store image path

    @FXML private TextField horseIDField;
    @FXML private TextField horseNameField;
    @FXML private TextField jockeyNameField;
    @FXML private TextField ageField;
    @FXML private TextField breedField;
    @FXML private TextField raceRecordField;
    @FXML private TextField groupField;
    @FXML private ImageView horseImageView;
    @FXML private Label imageNameLabel;
    @FXML private Button mainMenuButton;

    private List<HorseRace> horseDetailsList = new ArrayList<HorseRace>();
    private Set<Integer> horseIDSets = new HashSet<>();
    private Map<String, Integer> groupCount = new HashMap<>();


    @FXML
    private void initialize() {
        // Initialize anything here if necessary
    }

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
            imagePath = selectedFile.getPath(); // Save image path
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
    protected void onSaveClicked() {
        try {
            int horseID = Integer.parseInt(horseIDField.getText());

            if (horseID <= 0) {
                showAlert("Please enter a valid Horse ID.");
                return;
            }

            if (horseIDSets.contains(horseID)) {
                showAlert("Entered Horse ID is in use. Enter another Horse ID.");
                return;
            }

            String horseName = horseNameField.getText();
            String jockeyName = jockeyNameField.getText();
            int age;
            try {
                age = Integer.parseInt(ageField.getText());
            } catch (NumberFormatException e) {
                showAlert("Error: Please enter a valid age.");
                return;
            }
            String breed = breedField.getText();
            String raceRecord = raceRecordField.getText();
            String group = groupField.getText();

            if (horseName.isEmpty() || jockeyName.isEmpty() || breed.isEmpty() || raceRecord.isEmpty() || group.isEmpty()) {
                showAlert("Please fill in all fields.");
                return;
            }

            if (imagePath == null || imagePath.isEmpty()) {
                showAlert("Please insert a picture of the horse.");
                return;
            }

            if (!group.equals("A") && !group.equals("B") && !group.equals("C") && !group.equals("D")) {
                showAlert("Invalid Group. Please enter a valid group.");
                return;
            }

            if (groupCount.getOrDefault(group, 0) >= 5) {
                showAlert("Group is full. Choose another group.");
                return;
            }

            horseIDSets.add(horseID);
            groupCount.put(group, groupCount.getOrDefault(group, 0) + 1);

            // Save horse details along with image path
            HorseRace newHorse = new HorseRace(horseID, horseName, jockeyName, age, breed, raceRecord, group, imagePath);
            horseDetailsList.add(newHorse);

            saveToFile(); // method that call saving text file

            clearFields(); // Clear fields after saving

            Alert successAlert = new Alert(AlertType.CONFIRMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Horse details added");
            successAlert.setContentText("Horse saved successfully. Total horses: " + horseDetailsList.size());
            successAlert.showAndWait();

        }

        catch (NumberFormatException e) {
            showAlert("Error: Please enter a valid Horse ID.");
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("horse details.txt", true))) {
            HorseRace latestHorse = horseDetailsList.get(horseDetailsList.size() - 1); // Get the latest added horse
            writer.println(((HorseRace) latestHorse).getHorseID() + ", " + latestHorse.getHorseName() + ", " + latestHorse.getJockeyName() + ", " + latestHorse.getAge() + ", " + latestHorse.getBreed() + ", " + latestHorse.getRaceRecord() + ", " + latestHorse.getGroup() + ", " + latestHorse.getImagePath());
            System.out.println("Your horse details have been added successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving horse details: " + e.getMessage());
        }
    }

    @FXML
    protected void onResetClicked() {
        clearFields();
    }

    private void clearFields() {
        horseIDField.clear();
        horseNameField.clear();
        jockeyNameField.clear();
        ageField.clear();
        breedField.clear();
        raceRecordField.clear();
        groupField.clear();
        horseImageView.setImage(null);
        imageNameLabel.setText("Image Name");
        imagePath = null; // Clear image path
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


}
