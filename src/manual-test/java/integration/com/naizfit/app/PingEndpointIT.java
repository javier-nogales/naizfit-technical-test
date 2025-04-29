package integration.com.naizfit.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Integration test
 * 
 * execute with: mvn test -Dtest=PingEndpointIT
 * 
 * BEWARE! docker container must be running
 */
public class PingEndpointIT {

    @Test
    void pingShouldReturnPong() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/naizfit-tester-api/api/ping"))
							    					  .GET()
							    					  .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("pong", response.body());
    }
}
