package org.nasa.api.model;

import java.util.List;

/**
 * Mapper model object for nasa api response entity (list of photos)
 *
 * @author Nenad Cincovski
 */
public class ClientPhotosList {
    private List<ClientPhoto> photos;

    public List<ClientPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ClientPhoto> photos) {
        this.photos = photos;
    }
}
