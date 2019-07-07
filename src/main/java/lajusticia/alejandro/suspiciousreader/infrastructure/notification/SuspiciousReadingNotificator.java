package lajusticia.alejandro.suspiciousreader.infrastructure.notification;

import lajusticia.alejandro.suspiciousreader.application.event.SuspiciousReadingsEvent;
import lajusticia.alejandro.suspiciousreader.domain.entity.SuspiciousReading;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SuspiciousReadingNotificator {

    private final String CLIENT_HEADER = "Client";
    private final String MONTH_HEADER = "Month";
    private final String SUSPICIOUS_HEADER = "Suspicious";
    private final String MEDIAN_HEADER = "Median";

    @EventListener
    public void handlerSuspiciousReadingsEvent(SuspiciousReadingsEvent suspiciousReadingsEvent) {
        int clientLength = CLIENT_HEADER.length();
        int monthLength = 8;
        int suspiciousLength = SUSPICIOUS_HEADER.length();
        int medianLength = MEDIAN_HEADER.length();

        for (SuspiciousReading suspiciousReading : suspiciousReadingsEvent.getSuspiciousReadings()) {
            clientLength = Math.max(clientLength, suspiciousReading.getClient().length());
            suspiciousLength = Math.max(suspiciousLength, String.valueOf(suspiciousReading.getSuspicious()).length());
            medianLength = Math.max(medianLength, String.valueOf(suspiciousReading.getMedian()).length());
        }

        System.out.println();
        System.out.println(buildSeparator(clientLength, monthLength, suspiciousLength, medianLength));
        System.out.println(buildHeader(clientLength, monthLength, suspiciousLength, medianLength));
        System.out.println(buildSeparator(clientLength, monthLength, suspiciousLength, medianLength));
        for (SuspiciousReading suspiciousReading : suspiciousReadingsEvent.getSuspiciousReadings()) {
            System.out.println(
                    buildLine(
                            suspiciousReading,
                            clientLength,
                            monthLength,
                            suspiciousLength,
                            medianLength
                    )
            );
        }
        System.out.println(buildSeparator(clientLength, monthLength, suspiciousLength, medianLength));
        System.out.println();
    }

    private String buildSeparator(int clientLength, int monthLength, int suspiciousLength, int medianLength) {
        StringBuilder separator = new StringBuilder("+-");
        fillBuilder(separator, 0, clientLength, '-');
        separator.append("-+-");
        fillBuilder(separator, 0, monthLength, '-');
        separator.append("-+-");
        fillBuilder(separator, 0, suspiciousLength, '-');
        separator.append("-+-");
        fillBuilder(separator, 0, medianLength, '-');
        separator.append("-+");
        return separator.toString();
    }

    private String buildHeader(int clientLength, int monthLength, int suspiciousLength, int medianLength) {
        StringBuilder header = new StringBuilder("| " + CLIENT_HEADER);
        fillBuilder(header, CLIENT_HEADER.length(), clientLength, ' ');
        header.append(" | " + MONTH_HEADER);
        fillBuilder(header, MONTH_HEADER.length(), monthLength, ' ');
        header.append(" | " + SUSPICIOUS_HEADER);
        fillBuilder(header, SUSPICIOUS_HEADER.length(), suspiciousLength, ' ');
        header.append(" | " + MEDIAN_HEADER);
        fillBuilder(header, MEDIAN_HEADER.length(), medianLength, ' ');
        header.append(" |");
        return header.toString();
    }

    private String buildLine(
            SuspiciousReading suspiciousReading,
            int clientLength,
            int monthLength,
            int suspiciousLength,
            int medianLength)
    {
        StringBuilder line = new StringBuilder("| " + suspiciousReading.getClient());
        fillBuilder(line, suspiciousReading.getClient().length(), clientLength, ' ');
        String date = parseDate(suspiciousReading.getDate());
        line.append(" | ").append(date);
        fillBuilder(line, date.length(), monthLength, ' ');
        String suspicious = String.valueOf(suspiciousReading.getSuspicious());
        line.append(" | ").append(suspicious);
        fillBuilder(line, suspicious.length(), suspiciousLength, ' ');
        String median = String.valueOf(suspiciousReading.getMedian());
        line.append(" | ").append(median);
        fillBuilder(line, median.length(), medianLength, ' ');
        line.append(" |");
        return line.toString();
    }

    private String parseDate(LocalDate date) {
        String dateAsString = String.valueOf(date.getYear());
        dateAsString += "-";
        if (date.getMonthValue() < 10) {
            dateAsString += "0";
        }
        dateAsString += date.getMonthValue();
        return dateAsString;
    }

    private void fillBuilder(StringBuilder builder, int initial, int end, char charToFill) {
        for(int i = initial; i < end; i++) {
            builder.append(charToFill);
        }
    }
}
