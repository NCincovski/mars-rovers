package org.nasa.api.controller;

import org.nasa.api.service.RoversPhotosService;
import org.nasa.api.util.RoversValidator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Rest controller class accepting all incoming request for retrieval of rovers photos
 *
 * @author Nenad Cincovski
 */
@Path("rovers")
public class RoversPhotosController {
    @Inject
    RoversPhotosService service;

    @GET
    @Path("/{roverName}/photos")
    @Produces(APPLICATION_JSON)
    public Response getPhotos(@PathParam("roverName") String roverName, //wrap
                              @QueryParam("earth-date") String earthDate, //wrap
                              @QueryParam("camera") String camera) {
        if (!RoversValidator.ifRoverHasCamera(roverName, camera)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(service.getPhotosForDate(roverName, earthDate, camera)).build();
    }

    @GET
    @Path("/{roverName}/photos/recent")
    @Produces(APPLICATION_JSON)
    public Response getPhotos(@PathParam("roverName") String roverName, //wrap
                              @QueryParam("camera") String camera, //wrap
                              @QueryParam("days") int days) {
        if (!RoversValidator.ifRoverHasCamera(roverName, camera) || days > 10) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK) //wrap
                .entity(service.getPhotosForDays(roverName.toLowerCase(), camera.toLowerCase(), days)).build();
    }
}
