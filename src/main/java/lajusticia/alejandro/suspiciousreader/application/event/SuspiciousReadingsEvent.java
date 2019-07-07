package lajusticia.alejandro.suspiciousreader.application.event;

import lajusticia.alejandro.suspiciousreader.domain.entity.SuspiciousReading;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class SuspiciousReadingsEvent extends ApplicationEvent {

    private final List<SuspiciousReading> suspiciousReadings;

    public SuspiciousReadingsEvent(Object source, List<SuspiciousReading> suspiciousReadings) {
        super(source);
        this.suspiciousReadings = suspiciousReadings;
    }

}
