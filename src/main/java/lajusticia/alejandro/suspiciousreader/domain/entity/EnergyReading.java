package lajusticia.alejandro.suspiciousreader.domain.entity;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class EnergyReading {

    private final LocalDate date;
    private final long value;

    public EnergyReading(final LocalDate date, final long value) {
        this.date = date;
        this.value = value;
    }

}
