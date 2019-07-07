package lajusticia.alejandro.suspiciousreader.application.validate;

import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;
import lajusticia.alejandro.suspiciousreader.domain.entity.SuspiciousReading;
import lajusticia.alejandro.suspiciousreader.domain.service.RawEnergyReadingService;
import lajusticia.alejandro.suspiciousreader.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = { ValidateRawEnergyReadings.class, RawEnergyReadingService.class, UserService.class })
@ExtendWith(SpringExtension.class)
class ValidateRawEnergyReadingsTest {

    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ValidateRawEnergyReadings validateRawEnergyReadings;

    @Test
    @DisplayName("Test 2 users none of them with suspicious readings")
    void validateEnergyReading_2Users_0Suspicious() {
        String client1 = "client1";
        String client2 = "client2";
        List<RawEnergyReading> rawEnergyReadings = Arrays.asList(
                new RawEnergyReading(client1, "2019-10", 10),
                new RawEnergyReading(client1, "2019-11", 9),
                new RawEnergyReading(client1, "2019-12", 11),
                new RawEnergyReading(client2, "2019-10", 110),
                new RawEnergyReading(client2, "2019-11", 120),
                new RawEnergyReading(client2, "2019-12", 100)
        );

        List<SuspiciousReading> suspiciousReadings = validateRawEnergyReadings.validateEnergyReading(rawEnergyReadings);

        assertNotNull(suspiciousReadings);
        assertTrue(suspiciousReadings.isEmpty());
    }

    @Test
    @DisplayName("Test 2 users, every user with 2 suspicious readings")
    void validateEnergyReading_2Users_2and2Suspicious() {
        String client1 = "client1";
        String client2 = "client2";
        List<RawEnergyReading> rawEnergyReadings = Arrays.asList(
                new RawEnergyReading(client1, "2019-09", 10),
                new RawEnergyReading(client1, "2019-10", 5),
                new RawEnergyReading(client1, "2019-11", 15),
                new RawEnergyReading(client1, "2019-12", 10),
                new RawEnergyReading(client2, "2019-09", 5),
                new RawEnergyReading(client2, "2019-10", 220),
                new RawEnergyReading(client2, "2019-11", 100),
                new RawEnergyReading(client2, "2019-12", 100)
        );

        List<SuspiciousReading> suspiciousReadings = validateRawEnergyReadings.validateEnergyReading(rawEnergyReadings);

        assertNotNull(suspiciousReadings);
        assertEquals(4, suspiciousReadings.size());

        validateSameSuspicious(
                new SuspiciousReading(
                        client1,
                        LocalDate.of(2019, 10, 1),
                        5,
                        10f
                ),
                suspiciousReadings.get(0)
        );

        validateSameSuspicious(
                new SuspiciousReading(
                        client1,
                        LocalDate.of(2019, 11, 1),
                        15,
                        10f
                ),
                suspiciousReadings.get(1)
        );

        validateSameSuspicious(
                new SuspiciousReading(
                        client2,
                        LocalDate.of(2019, 9, 1),
                        5,
                        100f
                ),
                suspiciousReadings.get(2)
        );

        validateSameSuspicious(
                new SuspiciousReading(
                        client2,
                        LocalDate.of(2019, 10, 1),
                        220,
                        100f
                ),
                suspiciousReadings.get(3)
        );
    }

    @Test
    @DisplayName("Test 2 users, user 1 4 suspicious")
    void validateEnergyReading_2Users_4and0Suspicious() {
        String client1 = "client1";
        String client2 = "client2";
        List<RawEnergyReading> rawEnergyReadings = Arrays.asList(
                new RawEnergyReading(client1, "2019-07", 1),
                new RawEnergyReading(client1, "2019-08", 100),
                new RawEnergyReading(client1, "2019-09", 10),
                new RawEnergyReading(client1, "2019-10", 5),
                new RawEnergyReading(client1, "2019-11", 15),
                new RawEnergyReading(client1, "2019-12", 10),
                new RawEnergyReading(client2, "2019-10", 110),
                new RawEnergyReading(client2, "2019-11", 120),
                new RawEnergyReading(client2, "2019-12", 100)
        );

        List<SuspiciousReading> suspiciousReadings = validateRawEnergyReadings.validateEnergyReading(rawEnergyReadings);

        assertNotNull(suspiciousReadings);
        assertEquals(4, suspiciousReadings.size());

        validateSameSuspicious(
                new SuspiciousReading(
                        client1,
                        LocalDate.of(2019, 7, 1),
                        1,
                        10f
                ),
                suspiciousReadings.get(0)
        );

        validateSameSuspicious(
                new SuspiciousReading(
                        client1,
                        LocalDate.of(2019, 8, 1),
                        100,
                        10f
                ),
                suspiciousReadings.get(1)
        );

        validateSameSuspicious(
                new SuspiciousReading(
                        client1,
                        LocalDate.of(2019, 10, 1),
                        5,
                        10f
                ),
                suspiciousReadings.get(2)
        );

        validateSameSuspicious(
                new SuspiciousReading(
                        client1,
                        LocalDate.of(2019, 11, 1),
                        15,
                        10f
                ),
                suspiciousReadings.get(3)
        );
    }

    @Test
    @DisplayName("Test 0 users")
    void validateEnergyReading_0Users() {
        List<RawEnergyReading> rawEnergyReadings = Collections.emptyList();

        List<SuspiciousReading> suspiciousReadings = validateRawEnergyReadings.validateEnergyReading(rawEnergyReadings);

        assertNotNull(suspiciousReadings);
        assertTrue(suspiciousReadings.isEmpty());
    }

    @Test
    @DisplayName("Test 2 users with 1 readings")
    void validateEnergyReading_2Users_1Readings() {
        String client1 = "client1";
        String client2 = "client2";
        List<RawEnergyReading> rawEnergyReadings = Arrays.asList(
                new RawEnergyReading(client1, "2019-07", 1),
                new RawEnergyReading(client2, "2019-12", 10)
        );

        List<SuspiciousReading> suspiciousReadings = validateRawEnergyReadings.validateEnergyReading(rawEnergyReadings);

        assertNotNull(suspiciousReadings);
        assertTrue(suspiciousReadings.isEmpty());
    }

    private void validateSameSuspicious(SuspiciousReading expected, SuspiciousReading obtained) {
        assertNotNull(obtained);
        assertEquals(expected.getClient(), obtained.getClient());
        assertEquals(expected.getDate(), obtained.getDate());
        assertEquals(expected.getMedian(), obtained.getMedian());
        assertEquals(expected.getSuspicious(), obtained.getSuspicious());
    }

}
