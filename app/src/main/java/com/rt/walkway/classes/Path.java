package com.rt.walkway.classes;

public class Path {
    private int id;
    private String cityName;
    private double distance;
    private String description;
    private String difficulty;
    private double latidudeBegin;
    private double longitudeBegin;
    private double latitudeEnd;
    private double longitudeEnd;

    public Path() {
    }

    public Path(int id, String cityName, double distance, String description, String difficulty, double latidudeBegin, double longitudeBegin, double latitudeEnd, double longitudeEnd) {
        this.id = id;
        this.cityName = cityName;
        this.distance = distance;
        this.description = description;
        this.difficulty = difficulty;
        this.latidudeBegin = latidudeBegin;
        this.longitudeBegin = longitudeBegin;
        this.latitudeEnd = latitudeEnd;
        this.longitudeEnd = longitudeEnd;
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

    public double getLatidudeBegin() {
        return latidudeBegin;
    }

    public double getLongitudeBegin() {
        return longitudeBegin;
    }

    public double getLatitudeEnd() {
        return latitudeEnd;
    }

    public double getLongitudeEnd() {
        return longitudeEnd;
    }

    @Override
    public String toString() {
        return getCityName() + " | " + getDistance() + " | " + getDescription() + " | " + getDifficulty();
    }
}
