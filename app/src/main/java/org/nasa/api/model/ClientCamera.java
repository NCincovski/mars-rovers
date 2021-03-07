package org.nasa.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Mapper model object for nasa api camera entity
 *
 * @author Nenad Cincovski
 */
@JsonRootName(value = "camera")
public class ClientCamera {
    private int id;
    private String name;
    private int roverId;
    private String fullName;

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

    @JsonProperty("rover_id")
    public int getRoverId() {
        return roverId;
    }

    @JsonProperty("rover_id")
    public void setRoverId(int roverId) {
        this.roverId = roverId;
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
