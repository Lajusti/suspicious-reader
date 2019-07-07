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
import org.springframework.http.*;
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
public class XMLControllerTest {

    private final String XML_URL = "http://localhost:%d/xml";

    private final int port;

    public XMLControllerTest(@LocalServerPort final int port) {
        this.port = port;
    }

    @Test
    @DisplayName("Test controller AnimalsController get to end point /animals")
    void callController() throws IOException {
        File file = ResourceUtils.getFile("classpath:2016-readings.xml");
        String xml = new String(Files.readAllBytes(file.toPath()));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_XML);

        HttpEntity<String> entity = new HttpEntity<>(xml, header);

        ResponseEntity<List<SuspiciousReadingResponse>> response = new TestRestTemplate()
                .exchange(
                        String.format(XML_URL, port),
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<List<SuspiciousReadingResponse>>() {}
                );


        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<SuspiciousReadingResponse> responseBody = response.getBody();
        assertNotNull(responseBody);

        List<SuspiciousReadingResponse> expectedResponse = Arrays.asList(
                buildResponse("583ef6329e237", "2016-11", 1379, 30132.5f),
                buildResponse("583ef6329e271", "2016-10", 121208, 21661f),
                buildResponse("583ef6329e3ab", "2016-11", 6440, 27867.5f),
                buildResponse("583ef6329e41b", "2016-05", 133369, 32790.5f)
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