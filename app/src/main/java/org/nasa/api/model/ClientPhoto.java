package org.nasa.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mapper model object for nasa api photo entity
 *
 * @author Nenad Cincovski
 */
public class ClientPhoto {
    private int id;
    private int sol;
    private ClientCamera camera;
    private String imageSource;
    private String earthDate;
    private ClientRover rover;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public ClientCamera getCamera() {
        return camera;
    }

    public void setCamera(ClientCamera camera) {
        this.camera = camera;
    }

    @JsonProperty("img_src")
    public String getImageSource() {
        return imageSource;
    }

    @JsonProperty("img_src")
    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    @JsonProperty("earth_date")
    public String getEarthDate() {
        return earthDate;
    }

    @JsonProperty("earth_date")
    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public ClientRover getRover() {
        return rover;
    }

    public void setRover(ClientRover rover) {
        this.rover = rover;
    }
}
