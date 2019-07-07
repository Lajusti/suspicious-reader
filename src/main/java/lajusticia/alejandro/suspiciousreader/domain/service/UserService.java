package lajusticia.alejandro.suspiciousreader.domain.service;

import lajusticia.alejandro.suspiciousreader.domain.entity.EnergyReading;
import lajusticia.alejandro.suspiciousreader.domain.entity.SuspiciousReading;
import lajusticia.alejandro.suspiciousreader.domain.entity.User;
import lajusticia.alejandro.suspiciousreader.domain.sorter.EnergyReadingSorter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<SuspiciousReading> obtainSuspiciousReadingFromUsers(List<User> users) {
        List<SuspiciousReading> suspiciousReadings = new ArrayList<>();

        users.forEach(user ->
                suspiciousReadings.addAll(obtainSuspiciousReadingFromUser(user))
        );

        return suspiciousReadings;
    }

    private List<SuspiciousReading> obtainSuspiciousReadingFromUser(User user) {
        List<SuspiciousReading> suspiciousReadings = new ArrayList<>();
        float median = obtainMedianFromUser(user);
        float lowerSuspiciousValue = median * 0.5f;
        float upperSuspiciousValue = median * 1.5f;

        user.getEnergyReadings().forEach(energyReading -> {
            if ((float) energyReading.getValue() <= lowerSuspiciousValue ||
                    (float) energyReading.getValue() >= upperSuspiciousValue) {
                suspiciousReadings.add(new SuspiciousReading(
                        user.getId(),
                        energyReading.getDate(),
                        energyReading.getValue(),
                        median)
                );
            }
        });

        return suspiciousReadings;
    }

    private float obtainMedianFromUser(User user) {
        List<EnergyReading> energyReadings = new ArrayList<>(user.getEnergyReadings());
        energyReadings.sort(new EnergyReadingSorter());

        if (energyReadings.size() == 1) {
            return (float) energyReadings.get(0).getValue();
        }

        int middle = energyReadings.size() / 2;
        long sumOfMiddleValues = energyReadings.get(middle - 1).getValue() + energyReadings.get(middle).getValue();
        return (float) sumOfMiddleValues / (float) 2;
    }

}
