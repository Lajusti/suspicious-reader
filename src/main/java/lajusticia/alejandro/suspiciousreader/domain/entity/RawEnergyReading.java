package lajusticia.alejandro.suspiciousreader.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RawEnergyReading {

    private final String clientID;
    private final String period;
    private final long reading;

}
