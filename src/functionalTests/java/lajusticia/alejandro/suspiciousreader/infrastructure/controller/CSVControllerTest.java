package lajusticia.alejandro.suspiciousreader.infrastructure.controller;

import lajusticia.alejandro.suspiciousreader.SuspiciousReaderApplication;
import lajusticia.alejandro.suspiciousreader.infrastructure.controller.response.SuspiciousReadingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        classes = { SuspiciousReaderApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CSVControllerTest {

    private final String CSV_URL = "http://localhost:%d/csv";

    private final int port;

    public CSVControllerTest(@LocalServerPort final int port) {
        this.port = port;
    }

    @Test
    @DisplayName("Test controller AnimalsController get to end point /animals")
    void callController() throws IOException {
        File file = ResourceUtils.getFile("classpath:2016-readings.csv");
        String csv = new String(Files.readAllBytes(file.toPath()));

        RequestEntity<String> requestEntity = new RequestEntity<String>(
                csv,
                HttpMethod.POST,
                URI.create(String.format(CSV_URL, port))
        );

        ResponseEntity<List<SuspiciousReadingResponse>> response = new TestRestTemplate()
                .exchange(
                        requestEntity,
                        new ParameterizedTypeReference<List<SuspiciousReadingResponse>>() {}
                        );


        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<SuspiciousReadingResponse> responseBody = response.getBody();
        assertNotNull(responseBody);

        List<SuspiciousReadingResponse> expectedResponse = Arrays.asList(
                buildResponse("583ef6329d7b9", "2016-09", 3564, 42798.5f),
                buildResponse("583ef6329d89b", "2016-09", 162078, 59606.5f),
                buildResponse("583ef6329d89b", "2016-10", 7759, 59606.5f),
                buildResponse("583ef6329d916", "2016-09", 2479, 40956.0f)
        );

        assertEquals(expectedResponse, responseBody);
    }

    private SuspiciousReadingResponse buildResponse(String client, String month, long suspicious, float median) {
        SuspiciousReadingResponse response = new SuspiciousReadingResponse();
        response.setClient(client);
        response.setMonth(month);
        response.setSuspicious(suspicious);
        response.setMedian(median);
        return response;
    }

}
