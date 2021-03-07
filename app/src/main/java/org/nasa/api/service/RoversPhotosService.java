package org.nasa.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nasa.api.cache.InMemoryCacheWithDelayQueue;
import org.nasa.api.model.ClientPhotosList;
import org.nasa.api.model.DayPhotos;
import org.nasa.api.model.PhotosResponse;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class to manage retrieval of rovers photos
 *
 * @author Nenad Cincovski
 */
@Stateless
public class RoversPhotosService {
    // TODO: replace java util logger with Apache Log4j 2
    public static final Logger LOGGER = Logger.getLogger(RoversPhotosService.class.getName());

    private static final String BASE_URI = "https://api.nasa.gov/mars-photos/api/v1/rovers";
    private static final String ROVER_NAME_PATH_PARAM_DEFAULT_VALUE = "curiosity";
    private static final String API_KEY_QUERY_PARAM_NAME = "api_key";
    private static final String API_KEY_QUERY_PARAM_DEFAULT_VALUE = "psJ0CvJNLg8K9MBhX0eoGhTdPCsTjnT0956P2Ttg";
    private static final String EARTH_DATE_QUERY_PARAM_NAME = "earth_date";
    private static final String CAMERA_QUERY_PARAM_NAME = "camera";
    private static final String CAMERA_QUERY_PARAM_DEFAULT_VALUE = "navcam";
    private static final long CACHE_PERIOD_DEFAULT_VALUE_MS = 100_000L; // 100sec
    // TODO: add a configuration and read above defined properties from it

    private Client client;
    @Inject
    private InMemoryCacheWithDelayQueue cache;

    /**
     * Initializer method
     */
    @PostConstruct
    public void initialize() {
        client = createClient();
        LOGGER.info("Rover's photos service initialized.");
    }

    /**
     * Operation to download rover photos for given rover name, earth date and camera name
     *
     * @param roverName the rover name
     * @param earthDate the earth date
     * @param camera    the rover camera name
     * @return object containing downloaded photos
     */
    public PhotosResponse getPhotosForDate(String roverName, String earthDate, String camera) {
        throw new UnsupportedOperationException();
        // TODO: add date validator to support this operation
        // return retrievePhotosForDate(roverName, camera, earthDate);
    }

    /**
     * Download photos for last X days (currently not allowed more than 10)
     *
     * @param roverName the rover name
     * @param days      the number of days
     * @return object containing downloaded photos
     */
    public PhotosResponse getPhotosForDays(final String roverName, final String camera, int days) {
        PhotosResponse photosResponse = new PhotosResponse();
        while (days-- > 0) {
            String formattedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now().minusDays(days));
            DayPhotos dayPhotos = retrievePhotosForDate(roverName, camera, formattedDate);
            dayPhotos.setEarthDate(formattedDate);
            photosResponse.addDayPhotos(dayPhotos);
        }

        return photosResponse;
    }

    private DayPhotos retrievePhotosForDate(final String roverName, final String camera, String formattedDate) {
        // first check the cache
        final DayPhotos cachedPhotos = (DayPhotos) cache.get(formattedDate);
        if (Objects.nonNull(cachedPhotos)) {
            LOGGER.log(Level.INFO, "Entry for date: {0} found in cache", formattedDate);
            return cachedPhotos;
        } else {
            cache.remove(formattedDate);
        }

        Response response = executeRequest(roverName, camera, formattedDate);

        ClientPhotosList clientPhotosList = mapClientResponse(response);
        if (clientPhotosList == null) {
            LOGGER.log(Level.WARNING, "Request failed for date: {0}", formattedDate);
        }
        final DayPhotos photos = extractPhotosForDate(clientPhotosList);
        cache.add(formattedDate, photos, CACHE_PERIOD_DEFAULT_VALUE_MS);
        return photos;
    }

    private DayPhotos extractPhotosForDate(ClientPhotosList clientPhotosList) {
        final DayPhotos dayPhotos = new DayPhotos();
        if (Objects.nonNull(clientPhotosList)) {
            clientPhotosList.getPhotos().stream().limit(3).forEach(p -> dayPhotos.addImageSource(p.getImageSource()));
        }
        return dayPhotos;
    }

    private ClientPhotosList mapClientResponse(Response response) {
        ClientPhotosList clientPhotosList = null;
        if (response.hasEntity()) {
            // reading directly as ClientPhotosList.class does not recognize @JsonProperty, so this why first to String
            String json = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                clientPhotosList = mapper.readValue(json, ClientPhotosList.class);
            } catch (JsonProcessingException e) {
                LOGGER.log(Level.WARNING, "Couldn't parse the response: {0}", e);
            }
        }
        return clientPhotosList;
    }

    private Response executeRequest(final String roverName, final String camera, final String date) {
        WebTarget target = buildUri(roverName, camera, date);
        LOGGER.log(Level.INFO, "Sending request for date: {0} and URI: {1}", new Object[]{date, target.getUri().toString()});
        return target.request(MediaType.APPLICATION_JSON).get();
    }

    private WebTarget buildUri(final String roverName, final String camera, String date) {
        if (Objects.isNull(client)) {
            client = createClient();
        }
        return client.target(BASE_URI) //wrap
                .path(roverName + "/photos") //wrap
                .queryParam(API_KEY_QUERY_PARAM_NAME, API_KEY_QUERY_PARAM_DEFAULT_VALUE) //wrap
                .queryParam(CAMERA_QUERY_PARAM_NAME, camera) //wrap
                .queryParam(EARTH_DATE_QUERY_PARAM_NAME, date);
    }

    private Client createClient() {
        return ClientBuilder.newClient();
    }

}
