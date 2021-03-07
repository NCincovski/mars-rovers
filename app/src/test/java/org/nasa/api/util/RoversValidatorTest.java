package org.nasa.api.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.nasa.api.util.RoversValidator.*;

public class RoversValidatorTest {
    private static final String CURIOSITY = "curiosity";
    private static final String NAVCAM = "navcam";
    private static final String UNKNOWN = "unknown";

    @Test
    public void testCheckRoverExistence() {
        assertTrue(ifRoverPresent(CURIOSITY));
        assertFalse(ifRoverPresent(UNKNOWN));
    }

    @Test
    public void testCheckCameraExistence() {
        assertTrue(ifCameraPresent(NAVCAM));
        assertFalse(ifCameraPresent(UNKNOWN));
    }

    @Test
    public void testCheckRoverCameraEquipment() {
        assertTrue(ifRoverHasCamera(CURIOSITY, NAVCAM));
        assertFalse(ifRoverHasCamera(CURIOSITY, UNKNOWN));
        assertFalse(ifRoverHasCamera(UNKNOWN, NAVCAM));
    }
}