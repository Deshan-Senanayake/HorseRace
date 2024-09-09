package org.example.demo;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.application.Platform;

public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Main menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void nextOnMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Second menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void ahd(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void delete(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("delete.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void update(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("update.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void view(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*public void save(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Horse details successfully saved to the text file");

        // Remove the default OK button and add a custom OK button
        alert.getButtonTypes().clear();
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().add(okButton);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            // Do nothing; just stay in the current GUI
        }
    }*/

    public void save(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("save.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void start(ActionEvent event) throws IOException {
        // Create a new alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Question");
        alert.setHeaderText("Has the Race started?");

        // Customize the buttons to "Yes" and "No"
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonYes) {
                // Load another GUI
                Parent root = FXMLLoader.load(getClass().getResource("selectHorses.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if (result.get() == buttonNo) {
                // User clicked "No" or closed the dialog
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Race should be started to do this");
                infoAlert.showAndWait();
            }
        } else {
            // User closed the dialog without clicking any button
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Information");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("");
            infoAlert.showAndWait();
        }
    }

    public void win(ActionEvent event) throws IOException {
        // Create a new alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("To Do This You Need To Navigate From Semi Finals");

        // Customize the buttons to "Yes" and "No"
        ButtonType buttonYes = new ButtonType("Go To Semi Finals");
        ButtonType buttonNo = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonYes) {
                // Load another GUI
                Parent root = FXMLLoader.load(getClass().getResource("selectHorses.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if (result.get() == buttonNo) {
                // User clicked "No" or closed the dialog
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("At least Semi Finals Should Be Started To Perform This");
                infoAlert.showAndWait();
            }
        } else {
            // User closed the dialog without clicking any button
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Information");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("");
            infoAlert.showAndWait();
        }
    }

    public void finale(ActionEvent event) throws IOException {
        // Create a new alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("To Do This You Need To Navigate From Semi Finals");

        // Customize the buttons to "Yes" and "No"
        ButtonType buttonYes = new ButtonType("Go To Semi Finals");
        ButtonType buttonNo = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        // Show the alert and wait for a response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonYes) {
                // Load another GUI
                Parent root = FXMLLoader.load(getClass().getResource("selectHorses.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if (result.get() == buttonNo) {
                // User clicked "No" or closed the dialog
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("At least Semi Finals Should Be Started To Perform This");
                infoAlert.showAndWait();
            }
        } else {
            // User closed the dialog without clicking any button
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Information");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("");
            infoAlert.showAndWait();
        }
    }

    public void exit(ActionEvent event) {
        Platform.exit();
    }

}

