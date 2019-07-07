package lajusticia.alejandro.suspiciousreader.domain.sorter;

import lajusticia.alejandro.suspiciousreader.domain.entity.EnergyReading;

import java.util.Comparator;

public class EnergyReadingSorter implements Comparator<EnergyReading> {

    @Override
    public int compare(EnergyReading energyReading1, EnergyReading energyReading2) {
        return Long.compare(energyReading1.getValue(), energyReading2.getValue());
    }

}
