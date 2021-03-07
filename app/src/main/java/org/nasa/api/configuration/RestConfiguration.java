package org.nasa.api.configuration;


import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.nasa.api.controller.RoversPhotosController;

/**
 * Rest configuration class
 *
 * @author Nenad Cincovski
 */
@ApplicationPath("/api")
public class RestConfiguration extends ResourceConfig {
    public RestConfiguration() {
        register(RoversPhotosController.class);
    }
}
