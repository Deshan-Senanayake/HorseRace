package org.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class selectHorseController {

    private Stage stage;
    private Scene scene;

    @FXML
    private TableView<Horse> tableView;

    @FXML
    private TableColumn<Horse, String> horseIDColumn;

    @FXML
    private TableColumn<Horse, String> horseNameColumn;

    @FXML
    private TableColumn<Horse, String> jockeyNameColumn;

    @FXML
    private TableColumn<Horse, String> ageColumn;

    @FXML
    private TableColumn<Horse, String> breedColumn;

    @FXML
    private TableColumn<Horse, String> raceRecordColumn;

    @FXML
    private TableColumn<Horse, String> groupColumn;

    @FXML
    private TableColumn<Horse, String> imagePathColumn;

    @FXML
    private TableColumn<Horse, String> timeColumn; // New column for displaying time

    private String filePath = "horse details.txt";

    @FXML
    void initialize() {
        // Initialize table columns
        horseIDColumn.setCellValueFactory(new PropertyValueFactory<>("horseID"));
        horseNameColumn.setCellValueFactory(new PropertyValueFactory<>("horseName"));
        jockeyNameColumn.setCellValueFactory(new PropertyValueFactory<>("jockeyName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        raceRecordColumn.setCellValueFactory(new PropertyValueFactory<>("raceRecord"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        imagePathColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time")); // Bind the time column to the Horse class

        // Set up custom cell factory for the imagePathColumn to display thumbnail images
        imagePathColumn.setCellFactory(img -> new javafx.scene.control.TableCell<Horse, String>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);

                if (empty || imagePath == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    try {
                        // Load the thumbnail image from the imagePath
                        Image image = new Image(new FileInputStream(imagePath));
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Load data into the table
        load();
    }


    @FXML
    void load() {
        List<Horse> horseList = read();
        // Assign random times to horses
        assignRandomTimes(horseList);
        // Sort the list of horses based on time
        bubbleSort(horseList);
        // Find the horse with the least time from each group
        List<Horse> leastTimeHorses = findLeastTimeHorses(horseList);
        ObservableList<Horse> observableList = FXCollections.observableArrayList(leastTimeHorses);
        tableView.setItems(observableList);
    }

    private void assignRandomTimes(List<Horse> horseList) {
        Random random = new Random();
        for (Horse horse : horseList) {
            // Generate random time between 60 and 180 seconds
            int time = random.nextInt(91);
            horse.setTime(time);
        }
    }

    private List<Horse> read() {
        List<Horse> horseList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 8) {
                    Horse horse = new Horse(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
                    horseList.add(horse);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return horseList;
    }

    // Bubble sort implementation based on time
    private void bubbleSort(List<Horse> list) {
        int n = list.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (list.get(j).getTime() > list.get(j+1).getTime()) {
                    // Swap the horses
                    Horse temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }
    }

    // Find the horse with the least time from each group
    private List<Horse> findLeastTimeHorses(List<Horse> list) {
        List<Horse> leastTimeHorses = new ArrayList<>();
        // Assuming there are only 4 groups: A, B, C, D
        for (char group = 'A'; group <= 'D'; group++) {
            for (Horse horse : list) {
                if (horse.getGroup().charAt(0) == group) {
                    leastTimeHorses.add(horse);
                    break;
                }
            }
        }
        return leastTimeHorses;
    }

    @FXML
    protected void onMainMenuClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Second menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void onSemiFinalClicked(ActionEvent event) throws IOException {
        // Assuming you have a list of all horses available in the scope
        List<Horse> allHorses = read(); // Assuming read() method reads all horses from the file

        // Assuming you have a way to select semi-finalists from all horses (for example, randomly selecting 4 horses)
        List<Horse> semiFinalists = selectSemiFinalists(allHorses);

        // Load the winners GUI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("displayWin.fxml"));
        Parent root = loader.load();
        WinnersController winnersController = loader.getController();

        // Pass the semi-finalists to the winners controller
        winnersController.initialize(semiFinalists);

        // Show the winners GUI
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Example method to select semi-finalists (you can implement your own logic here)
    private List<Horse> selectSemiFinalists(List<Horse> allHorses) {
        // For simplicity, let's assume we randomly select 4 horses as semi-finalists
        Collections.shuffle(allHorses);
        return allHorses.subList(0, Math.min(4, allHorses.size()));
    }


    public class Horse {

        private String horseID;
        private String horseName;
        private String jockeyName;
        private String age;
        private String breed;
        private String raceRecord;
        private String group;
        private String imagePath;
        private int time; // Time in seconds

        public Horse(String horseID, String horseName, String jockeyName, String age, String breed, String raceRecord, String group, String imagePath) {
            this.horseID = horseID;
            this.horseName = horseName;
            this.jockeyName = jockeyName;
            this.age = age;
            this.breed = breed;
            this.raceRecord = raceRecord;
            this.group = group;
            this.imagePath = imagePath;
        }

        public String getHorseID() {
            return horseID;
        }

        public String getHorseName() {
            return horseName;
        }

        public String getJockeyName() {
            return jockeyName;
        }

        public String getAge() {
            return age;
        }

        public String getBreed() {
            return breed;
        }

        public String getRaceRecord() {
            return raceRecord;
        }

        public String getGroup() {
            return group;
        }

        public String getImagePath() {
            return imagePath;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
