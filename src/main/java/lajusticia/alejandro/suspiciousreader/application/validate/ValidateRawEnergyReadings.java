package lajusticia.alejandro.suspiciousreader.application.validate;

import lajusticia.alejandro.suspiciousreader.application.event.SuspiciousReadingsEvent;
import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;
import lajusticia.alejandro.suspiciousreader.domain.entity.SuspiciousReading;
import lajusticia.alejandro.suspiciousreader.domain.entity.User;
import lajusticia.alejandro.suspiciousreader.domain.service.RawEnergyReadingService;
import lajusticia.alejandro.suspiciousreader.domain.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidateRawEnergyReadings {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final RawEnergyReadingService rawEnergyReadingService;
    private final UserService userService;

    public ValidateRawEnergyReadings(
            final ApplicationEventPublisher applicationEventPublisher,
            final RawEnergyReadingService rawEnergyReadingService,
            final UserService userService
    ) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.rawEnergyReadingService = rawEnergyReadingService;
        this.userService = userService;
    }

    public List<SuspiciousReading> validateEnergyReading(List<RawEnergyReading> rawEnergyReadings) {
        List<User> users = rawEnergyReadingService.convertToUsers(rawEnergyReadings);
        List<SuspiciousReading> suspiciousReadings = userService.obtainSuspiciousReadingFromUsers(users);

        if (!suspiciousReadings.isEmpty()) {
            applicationEventPublisher.publishEvent(
                    new SuspiciousReadingsEvent(
                            this,
                            suspiciousReadings
                    )
            );
        }

        return suspiciousReadings;
    }

}
