package lajusticia.alejandro.suspiciousreader.infrastructure.converter;

import lajusticia.alejandro.suspiciousreader.domain.converter.RawEnergyReadingConverter;
import lajusticia.alejandro.suspiciousreader.infrastructure.entity.EnergyReadingAsXML;
import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;
import org.springframework.stereotype.Service;

@Service
public class RawEnergyReadingXMLConverter implements RawEnergyReadingConverter<EnergyReadingAsXML> {

    @Override
    public RawEnergyReading convert(EnergyReadingAsXML energyReadingAsXML) {
        return new RawEnergyReading(
                energyReadingAsXML.getClientID(),
                energyReadingAsXML.getPeriod(),
                energyReadingAsXML.getReading()
        );
    }

}
