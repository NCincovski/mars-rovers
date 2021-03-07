package org.nasa.api.model;

import java.util.Objects;

/**
 * Enumeration for all known rover's cameras
 *
 * @author Nenad Cincovski
 */
public enum RoverCamerasEnum {
    FHAZ("Front Hazard Avoidance"),//wrap
    RHAZ("Rear Hazard Avoidance"),//wrap
    MAST("Mast"),//wrap
    CHEMCAM("Chemistry and Camera Complex"),//wrap
    MAHLI("Mars Hand Lens Imager"),//wrap
    MARDI("Mars Descent Imager"),//wrap
    NAVCAM("Navigation"),//wrap
    PANCAM("Panoramic"),//wrap
    MINITES("Miniature Thermal Emission Spectrometer (Mini-TES)");

    private final String longName;

    RoverCamerasEnum(String name) {
        this.longName = name;
    }

    /**
     * Checks for name existance in the enum
     *
     * @param name the rover name to check
     * @return true, if name is found, otherwise false
     */
    public static boolean checkCameraName(String name) {
        if (Objects.nonNull(name)) {
            for (RoverCamerasEnum c : RoverCamerasEnum.values()) {
                if (c.name().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }
}
