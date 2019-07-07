package lajusticia.alejandro.suspiciousreader.domain.converter;

import lajusticia.alejandro.suspiciousreader.domain.entity.RawEnergyReading;

public interface RawEnergyReadingConverter<T> {

    RawEnergyReading convert(T t);

}
