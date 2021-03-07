package org.nasa.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Mapper model object for final response entity
 *
 * @author Nenad Cincovski
 */
public class PhotosResponse {
    // by default empty list
    private List<DayPhotos> photos = new ArrayList<>();

    @JsonProperty("photos")
    public List<DayPhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<DayPhotos> photos) {
        this.photos = photos;
    }

    public void addDayPhotos(DayPhotos photos) {
        if (Objects.nonNull(photos)) {
            this.photos.add(photos);
        }
    }
}
