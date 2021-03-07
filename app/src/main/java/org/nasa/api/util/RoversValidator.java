package org.nasa.api.util;

import org.nasa.api.model.RoverCamerasEnum;
import org.nasa.api.model.RoversEnum;

/**
 * Custom validation helper class
 *
 * @author Nenad Cincovski
 */
public class RoversValidator {
    /**
     * Checks for valid rover name
     *
     * @param roverName the rover name
     * @return true, if name is valid, otherwise false
     */
    public static boolean ifRoverPresent(String roverName) {
        return RoversEnum.checkRoverName(roverName);
    }

    /**
     * Checks for valid camera name
     *
     * @param cameraName the camera name
     * @return true, if name is valid, otherwise false
     */
    public static boolean ifCameraPresent(String cameraName) {
        return RoverCamerasEnum.checkCameraName(cameraName);
    }

    /**
     * Checks whether rover name and camera name exist and if so, then cheks whether rover is equipped that camera
     *
     * @param roverName  the rover name
     * @param cameraName the camera name
     * @return true, if names exist and rover is equipped with the given camera, otherwise false
     */
    public static boolean ifRoverHasCamera(String roverName, String cameraName) {
        if (ifRoverPresent(roverName) && ifCameraPresent(cameraName)) {
            RoversEnum rover = RoversEnum.valueOf(roverName.toUpperCase());
            RoverCamerasEnum camera = RoverCamerasEnum.valueOf(cameraName.toUpperCase());
            return rover.checkCamera(camera);
        }
        return false;
    }
}
