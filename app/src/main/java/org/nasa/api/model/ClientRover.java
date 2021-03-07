package org.nasa.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Mapper model object for nasa api rover entity
 *
 * @author Nenad Cincovski
 */
@JsonRootName(value = "rover")
public class ClientRover {
    private int id;
    private String name;
    private String landingDate; //date
    private String launchDate; //date
    private String status; //enum

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("landing_date")
    public String getLandingDate() {
        return landingDate;
    }

    @JsonProperty("landing_date")
    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    @JsonProperty("launch_date")
    public String getLaunchDate() {
        return launchDate;
    }

    @JsonProperty("launch_date")
    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
