package lajusticia.alejandro.suspiciousreader.domain.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class User {

    private final String id;
    private final List<EnergyReading> energyReadings;

    public User(final String id) {
        this.id = id;
        this.energyReadings = new ArrayList<>();
    }

    public void addEnergyReading(EnergyReading energyReading) {
        energyReadings.add(energyReading);
    }

}
