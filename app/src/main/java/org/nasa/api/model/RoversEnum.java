package org.nasa.api.model;

import java.util.*;

import static org.nasa.api.model.RoverCamerasEnum.*;

/**
 * Enumeration for all known rover's and cameras they are equipped with
 *
 * @author Nenad Cincovski
 */
public enum RoversEnum {
    CURIOSITY(Arrays.asList(FHAZ, RHAZ, MAST, CHEMCAM, MAHLI, MARDI, NAVCAM)),//wrap
    OPPORTUNITY(Arrays.asList(FHAZ, RHAZ, NAVCAM, PANCAM, MINITES)),//wrap
    SPIRIT(Arrays.asList(FHAZ, RHAZ, NAVCAM, PANCAM, MINITES));

    private final List<RoverCamerasEnum> cameras;

    public List<RoverCamerasEnum> getCameras() {
        return cameras;
    }

    RoversEnum(List<RoverCamerasEnum> cameras) {
        this.cameras = Collections.unmodifiableList(cameras);
    }

    /**
     * Checks for name existence in the enum
     *
     * @param name the rover name to check
     * @return true, if name is found, otherwise false
     */
    public static boolean checkRoverName(final String name) {
        if (Objects.nonNull(name)) {
            for (RoversEnum r : RoversEnum.values()) {
                if (r.name().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for camera existence in the enum list of cameras
     *
     * @param camera the camera to check
     * @return true, if camera is found, otherwise false
     */
    public boolean checkCamera(final RoverCamerasEnum camera) {
        return this.cameras.contains(camera);
    }
}
