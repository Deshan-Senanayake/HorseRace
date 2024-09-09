package org.example.demo;

import javafx.beans.property.*;

public class HorseRace {
    private final IntegerProperty horseID = new SimpleIntegerProperty();
    private final StringProperty horseName = new SimpleStringProperty();
    private final StringProperty jockeyName = new SimpleStringProperty();
    private final IntegerProperty age = new SimpleIntegerProperty();
    private final StringProperty breed = new SimpleStringProperty();
    private final StringProperty raceRecord = new SimpleStringProperty();
    private final StringProperty group = new SimpleStringProperty();
    private final StringProperty imagePath = new SimpleStringProperty();


    public HorseRace() {
    }

    public HorseRace(int horseID, String horseName, String jockeyName, int age, String breed, String raceRecord, String group, String imagePath) {
        setHorseID(horseID);
        setHorseName(horseName);
        setJockeyName(jockeyName);
        setAge(age);
        setBreed(breed);
        setRaceRecord(raceRecord);
        setGroup(group);
        setImagePath(imagePath);
    }

    public final int getHorseID() {
        return horseID.get();
    }

    public final void setHorseID(int value) {
        horseID.set(value);
    }

    public IntegerProperty horseIDProperty() {
        return horseID;
    }

    public final String getHorseName() {
        return horseName.get();
    }

    public final void setHorseName(String value) {
        horseName.set(value);
    }

    public StringProperty horseNameProperty() {
        return horseName;
    }

    public final String getJockeyName() {
        return jockeyName.get();
    }

    public final void setJockeyName(String value) {
        jockeyName.set(value);
    }

    public StringProperty jockeyNameProperty() {
        return jockeyName;
    }

    public final int getAge() {
        return age.get();
    }

    public final void setAge(int value) {
        age.set(value);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public final String getBreed() {
        return breed.get();
    }

    public final void setBreed(String value) {
        breed.set(value);
    }

    public StringProperty breedProperty() {
        return breed;
    }

    public final String getRaceRecord() {
        return raceRecord.get();
    }

    public final void setRaceRecord(String value) {
        raceRecord.set(value);
    }

    public StringProperty raceRecordProperty() {
        return raceRecord;
    }

    public final String getGroup() {
        return group.get();
    }

    public final void setGroup(String value) {
        group.set(value);
    }

    public StringProperty groupProperty() {
        return group;
    }

    public final String getImagePath() {
        return imagePath.get();
    }

    public final void setImagePath(String value) {
        imagePath.set(value);
    }


}
