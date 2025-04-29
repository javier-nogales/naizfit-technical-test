package integration.com.naizfit.app;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CRUD operations on Tester Admin API.
 * Assumes the application is running in Docker at localhost:8080.
 * Uses an in-memory repository, so state is clean at start.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesterCRUDIntegrationIT {

    private static final String BASE_URL = "http://localhost:8080/naizfit-tester-api/api";
    private static HttpClient client;
    private static ObjectMapper mapper;
    private static String testerId;

    @BeforeAll
    public static void setup() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    public void createTester() throws IOException, InterruptedException {
        String json = "{\n" +
                      "  \"name\":\"Juan Pérez\",\n" +
                      "  \"email\":\"juan@example.com\",\n" +
                      "  \"rawPassword\":\"secret123\",\n" +
                      "  \"birthdate\":\"1990-05-15\",\n" +
                      "  \"sex\":\"MALE\"\n" +
                      "}";

        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/admin/testers"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<Void> res = client.send(req, HttpResponse.BodyHandlers.discarding());
        assertEquals(201, res.statusCode(), "Expected 201 Created");
        String location = res.headers().firstValue("Location").orElseThrow();
        // Location header of form /admin/testers/{id}
        assertTrue(location.matches("/admin/testers/[0-9a-fA-F\\-]+"));
        testerId = location.substring(location.lastIndexOf('/') + 1);
        assertNotNull(testerId, "Tester ID should be set");
    }

    @Test
    @Order(2)
    public void getTesterById() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/admin/testers/" + testerId))
            .GET()
            .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, res.statusCode(), "Expected 200 OK");
        JsonNode node = mapper.readTree(res.body());
        assertEquals(testerId, node.get("id").asText());
        assertEquals("Juan Pérez", node.get("name").asText());
        assertEquals("juan@example.com", node.get("email").asText());
    }

    @Test
    @Order(3)
    public void updateTester() throws IOException, InterruptedException {
        String json = "{\"name\":\"Juan X. Pérez\",\"email\":\"juanx@example.com\"}";
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/admin/testers/" + testerId))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();
        HttpResponse<Void> res = client.send(req, HttpResponse.BodyHandlers.discarding());
        assertEquals(204, res.statusCode(), "Expected 204 No Content");

        // verify update
        HttpRequest get = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/admin/testers/" + testerId))
            .GET()
            .build();
        HttpResponse<String> getRes = client.send(get, HttpResponse.BodyHandlers.ofString());
        JsonNode node = mapper.readTree(getRes.body());
        assertEquals("Juan X. Pérez", node.get("name").asText());
        assertEquals("juanx@example.com", node.get("email").asText());
    }

    @Test
    @Order(4)
    public void updatePassword() throws IOException, InterruptedException {
        String json = "{\"newPassword\":\"newSecret456\"}";
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/admin/testers/" + testerId + "/password"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();
        HttpResponse<Void> res = client.send(req, HttpResponse.BodyHandlers.discarding());
        assertEquals(204, res.statusCode(), "Expected 204 No Content on password update");
    }

    @Test
    @Order(5)
    public void deleteTester() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/admin/testers/" + testerId))
            .DELETE()
            .build();
        HttpResponse<Void> res = client.send(req, HttpResponse.BodyHandlers.discarding());
        assertEquals(204, res.statusCode(), "Expected 204 No Content on delete");

        // verify 404
        HttpRequest get = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/admin/testers/" + testerId))
            .GET()
            .build();
        HttpResponse<String> getRes = client.send(get, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, getRes.statusCode(), "Expected 404 after delete");
    }
}
