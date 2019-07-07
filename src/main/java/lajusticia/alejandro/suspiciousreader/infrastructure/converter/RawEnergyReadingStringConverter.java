package lajusticia.alejandro.suspiciousreader.infrastructure.converter;

import lajusticia.alejandro.suspiciousreader.domain.converter.RawEnergyReadingConverter;
import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;
import org.springframework.stereotype.Service;

@Service
public class RawEnergyReadingStringConverter implements RawEnergyReadingConverter<String> {

    private final String FIRST_LINE = "client,period,reading";

    @Override
    public RawEnergyReading convert(String rawEnergyReadyAsString) {
        if (FIRST_LINE.equals(rawEnergyReadyAsString)) {
            return null;
        }
        String[] rawEnergyReadyData = rawEnergyReadyAsString.split(",");
        return new RawEnergyReading(rawEnergyReadyData[0], rawEnergyReadyData[1], Long.valueOf(rawEnergyReadyData[2]));
    }

}
