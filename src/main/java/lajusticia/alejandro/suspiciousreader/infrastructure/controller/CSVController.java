package lajusticia.alejandro.suspiciousreader.infrastructure.controller;

import lajusticia.alejandro.suspiciousreader.application.validate.ValidateRawEnergyReadings;
import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;
import lajusticia.alejandro.suspiciousreader.infrastructure.controller.response.SuspiciousReadingResponse;
import lajusticia.alejandro.suspiciousreader.infrastructure.converter.RawEnergyReadingStringConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/csv")
public class CSVController {

    private final RawEnergyReadingStringConverter rawEnergyReadingStringConverter;
    private final ValidateRawEnergyReadings validateRawEnergyReadings;

    public CSVController(
            final RawEnergyReadingStringConverter rawEnergyReadingStringConverter,
            final ValidateRawEnergyReadings validateRawEnergyReadings)
    {
        this.rawEnergyReadingStringConverter = rawEnergyReadingStringConverter;
        this.validateRawEnergyReadings = validateRawEnergyReadings;
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<List<SuspiciousReadingResponse>> processXML(HttpServletRequest request) throws IOException {
        List<RawEnergyReading> rawEnergyReadings = convertContentToRawEnergyReadings(request);

        return new ResponseEntity<List<SuspiciousReadingResponse>>(
                validateRawEnergyReadings.validateEnergyReading(rawEnergyReadings).stream()
                        .map(SuspiciousReadingResponse::convertFromSuspiciousReading)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    private List<RawEnergyReading> convertContentToRawEnergyReadings(HttpServletRequest request) throws IOException {
        InputStreamReader reader = new InputStreamReader(request.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<RawEnergyReading> rawEnergyReadings = new ArrayList<>();
        while(bufferedReader.ready()) {
            RawEnergyReading rawEnergyReading = rawEnergyReadingStringConverter.convert(bufferedReader.readLine());
            if (rawEnergyReading != null) {
                rawEnergyReadings.add(rawEnergyReading);
            }
        }
        return rawEnergyReadings;
    }

}
