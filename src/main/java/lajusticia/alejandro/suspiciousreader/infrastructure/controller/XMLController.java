package lajusticia.alejandro.suspiciousreader.infrastructure.controller;

import lajusticia.alejandro.suspiciousreader.application.validate.ValidateRawEnergyReadings;
import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;
import lajusticia.alejandro.suspiciousreader.infrastructure.controller.response.SuspiciousReadingResponse;
import lajusticia.alejandro.suspiciousreader.infrastructure.converter.RawEnergyReadingXMLConverter;
import lajusticia.alejandro.suspiciousreader.infrastructure.entity.EnergyReadingsAsXML;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/xml")
public class XMLController {

    private final RawEnergyReadingXMLConverter rawEnergyReadingXMLConverter;
    private final ValidateRawEnergyReadings validateRawEnergyReadings;

    public XMLController(
            final RawEnergyReadingXMLConverter rawEnergyReadingXMLConverter,
            final ValidateRawEnergyReadings validateRawEnergyReadings)
    {
        this.rawEnergyReadingXMLConverter = rawEnergyReadingXMLConverter;
        this.validateRawEnergyReadings = validateRawEnergyReadings;
    }

    @PostMapping(consumes = MediaType.TEXT_XML_VALUE)
    public ResponseEntity processXML(@RequestBody EnergyReadingsAsXML energyReadingsAsXML) {
        List<RawEnergyReading> rawEnergyReadings = energyReadingsAsXML.getReadings().stream()
                .map(rawEnergyReadingXMLConverter::convert)
                .collect(Collectors.toList());

        return new ResponseEntity<List<SuspiciousReadingResponse>>(
                validateRawEnergyReadings.validateEnergyReading(rawEnergyReadings).stream()
                        .map(SuspiciousReadingResponse::convertFromSuspiciousReading)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

}
