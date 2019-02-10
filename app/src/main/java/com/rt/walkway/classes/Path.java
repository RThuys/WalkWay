package com.rt.walkway.classes;

public class Path {
    private int id;
    private String cityName;
    private double distance;
    private String description;
    private String difficulty;

    public Path() {
    }

    public Path(int id, String cityName, double distance, String description, String difficulty) {
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

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return getCityName() + " | " + getDistance() + " | " + getDescription() + " | " + getDifficulty();
    }
}
