package com.rt.walkway.classes;

public class Path {
    private int id;
    private String cityName;
    private double distance;
    private String description;
    private int difficulty;

    public Path() {
    }

    public Path(int id, String cityName, double distance, String description, int difficulty) {
        this.id = id;
        this.cityName = cityName;
        this.distance = distance;
        this.description = description;
        this.difficulty = difficulty;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
