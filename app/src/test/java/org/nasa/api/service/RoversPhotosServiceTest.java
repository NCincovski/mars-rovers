package org.nasa.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.nasa.api.cache.InMemoryCacheWithDelayQueue;
import org.nasa.api.model.ClientPhoto;
import org.nasa.api.model.ClientPhotosList;
import org.nasa.api.model.PhotosResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RoversPhotosServiceTest {
    @Mock
    @SuppressWarnings("unused")
    private InMemoryCacheWithDelayQueue cache;

    @InjectMocks
    private RoversPhotosService service;

    @Test
    public void testGetPhotosForDays() {
        // TODO: test is using real api and invokes nasa api, but normally should mock it

        PhotosResponse photos = service.getPhotosForDays("curiosity", "navcam", 6);
        assertNotNull(photos);
        assertEquals(6, photos.getPhotos().size());

        photos = service.getPhotosForDays("curiosity", "navcam", 4);
        assertNotNull(photos);
        assertEquals(4, photos.getPhotos().size());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(photos);
            assertFalse(json.isBlank());
            System.out.println("Result: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeserializeClientResponse() throws IOException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("client_response.json");
        if (resource != null) {
            Stream<String> lines = Files.lines(Paths.get(resource.toURI()));
            String json = lines.collect(Collectors.joining("\n"));
            lines.close();

            ObjectMapper mapper = new ObjectMapper();
            ClientPhotosList clientPhotosList = mapper.readValue(json, ClientPhotosList.class);
            assertNotNull(clientPhotosList);
            assertNotNull(clientPhotosList.getPhotos());
            assertEquals(clientPhotosList.getPhotos().size(), 4);
            ClientPhoto clientPhoto = clientPhotosList.getPhotos().get(0);
            assertNotNull(clientPhoto);
            assertNotNull(clientPhoto.getImageSource());
            assertNotNull(clientPhoto.getEarthDate());
        } else {
            System.out.println("File not found!");
            fail();
        }
    }
}