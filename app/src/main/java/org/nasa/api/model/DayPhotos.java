package org.nasa.api.model;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mapper model object part of final response entity
 *
 * @author Nenad Cincovski
 */
public class DayPhotos {
    private String earthDate;
    private List<String> imageSources = new ArrayList<>();

    @JsonValue
    public Map<String, List<String>> createMap() {
        Map<String, List<String>> map = new HashMap<>();
        map.put(earthDate, imageSources);
        return map;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public List<String> getImageSources() {
        return imageSources;
    }

    public void setImageSources(List<String> imageSources) {
        this.imageSources = imageSources;
    }

    public void addImageSource(String imageSource) {
        if (StringUtils.isNotBlank(imageSource)) {
            this.imageSources.add(imageSource);
        }
    }
}
