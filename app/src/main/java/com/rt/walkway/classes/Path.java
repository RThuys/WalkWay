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

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }


    public double getDistance() {
        return distance;
    }


    public String getDescription() {
        return description;
    }


    public String getDifficulty() {
        return difficulty;
    }


    @Override
    public String toString() {
        return getCityName() + " | " + getDistance() + " | " + getDescription() + " | " + getDifficulty();
    }
}
