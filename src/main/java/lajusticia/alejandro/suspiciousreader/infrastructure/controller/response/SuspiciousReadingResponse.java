package lajusticia.alejandro.suspiciousreader.infrastructure.controller.response;

import lajusticia.alejandro.suspiciousreader.domain.entity.SuspiciousReading;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
public class SuspiciousReadingResponse {

    private String client;
    private String month;
    private long suspicious;
    private float median;

    public void setClient(String client) {
        this.client = client;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setMonth(LocalDate month) {
        this.month = month.getYear() + "-" + convertMonthToString(month.getMonthValue());
    }

    public void setSuspicious(long suspicious) {
        this.suspicious = suspicious;
    }

    public void setMedian(float median) {
        this.median = median;
    }

    private String convertMonthToString(int monthValue) {
        if (monthValue < 10) {
            return "0" + monthValue;
        }
        return "" + monthValue;
    }

    public static SuspiciousReadingResponse convertFromSuspiciousReading(SuspiciousReading suspiciousReading) {
        SuspiciousReadingResponse suspiciousReadingResponse = new SuspiciousReadingResponse();
        suspiciousReadingResponse.setClient(suspiciousReading.getClient());
        suspiciousReadingResponse.setMonth(suspiciousReading.getDate());
        suspiciousReadingResponse.setSuspicious(suspiciousReading.getSuspicious());
        suspiciousReadingResponse.setMedian(suspiciousReading.getMedian());
        return suspiciousReadingResponse;
    }

}
