package com.pocketgarden;

import java.io.Serializable;

//Created a plant object for storing plant information easily
public class Plant implements Serializable {
    String plantName, exactPlantName;
    int wateringDays;
    double tempMin, tempMax;
    boolean outside, inside;

    public Plant(String plantName, String exactPlantName, int wateringDays, double tempMin, double tempMax, boolean inside, boolean outside) {
        this.plantName = plantName;
        this.exactPlantName = exactPlantName;
        this.wateringDays = wateringDays;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.outside = outside;
        this.inside = inside;
    }

    //Get methods for each attribute
    public String getName()
    {
        return this.plantName;
    }

    public String getExactName()
    {
        return this.plantName;
    }

    public int getWateringDays()
    {
        return this.wateringDays;
    }

    public double getTempMin()
    {
        return this.tempMin;
    }

    public double getTempMax()
    {
        return this.tempMax;
    }


    //set functions to set new values for the plant
    public void setPlantName(String name)
    {
        this.plantName = name;
    }

    public void setExactPlantName(String name)
    {
        this.exactPlantName = name;
    }

    public void setWateringDays(int days)
    {
        this.wateringDays = days;
    }

    public void setTempMin(int minTemp)
    {
        this.tempMin = minTemp;
    }

    public void setTempMax(int maxTemp)
    {
        this.tempMax = maxTemp;
    }

    public boolean isInside()
    {
        return inside;
    }

    public boolean isOutside()
    {
        return outside;
    }

    public void setInside()
    {
        this.inside = true;
        this.outside = false;
    }

    public void setOutside()
    {
        this.inside = false;
        this.outside = true;
    }
}
