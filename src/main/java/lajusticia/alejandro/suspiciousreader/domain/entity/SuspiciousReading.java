package lajusticia.alejandro.suspiciousreader.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class SuspiciousReading {

    private final String client;
    private final LocalDate date;
    private final long suspicious;
    private final float median;

}
