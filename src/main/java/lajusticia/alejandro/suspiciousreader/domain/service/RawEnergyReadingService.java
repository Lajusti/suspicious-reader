package lajusticia.alejandro.suspiciousreader.domain.service;

import lajusticia.alejandro.suspiciousreader.domain.entity.EnergyReading;
import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;
import lajusticia.alejandro.suspiciousreader.domain.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RawEnergyReadingService {

    public List<User> convertToUsers(List<RawEnergyReading> rawEnergyReadings) {
        List<User> users = new ArrayList<>();
        Map<String, User> usersAsMap = new HashMap<>();

        rawEnergyReadings.forEach(rawEnergyReading -> {
            String userId = rawEnergyReading.getClientID();
            User user = usersAsMap.get(userId);
            if (user == null) {
                user = new User(userId);
                users.add(user);
                usersAsMap.put(userId, user);
            }
            user.addEnergyReading(
                    new EnergyReading(
                            convertStringToLocalDate(rawEnergyReading.getPeriod()),
                            rawEnergyReading.getReading()
                    )
            );
        });

        return users;
    }

    private LocalDate convertStringToLocalDate(String rawDate) {
        String[] rawDateAsArray = rawDate.split("-");
        return LocalDate.of(
                Integer.valueOf(rawDateAsArray[0]),
                Integer.valueOf(rawDateAsArray[1]),
                1
        );
    }

}
