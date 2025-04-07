package fitnessapp.mental;

import fitnessapp.TimestampedPeriod;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MentalHealth implements Serializable {
    private Map<LocalDate, Quality> moodQuality = new HashMap<>();
    private List<SleepLogEntry> sleepLogEntries = new ArrayList<>();

    public boolean isOverlapping(TimestampedPeriod period) {
        return sleepLogEntries.stream().anyMatch(period::overlaps);
    }

    public boolean addSleepLogEntry(SleepLogEntry sleepLogEntry) {
        if (isOverlapping(sleepLogEntry)) {
            return false;
        }
        return sleepLogEntries.add(sleepLogEntry);
    }

    public List<SleepLogEntry> getSleepLogEntries() {
        return sleepLogEntries;
    }

    /**
     * Get all sleep entries that start or end on a date
     * @param date the date in question
     * @return a list of log entries for that date
     */
    public List<SleepLogEntry> getSleepLogEntries(LocalDate date) {
        return sleepLogEntries.stream()
                              .filter(entry -> entry.getStartTimestamp().toLocalDate().equals(date) || entry.getEndTimestamp().toLocalDate().equals(date))
                              .collect(Collectors.toList());
    }

    /**
     * Get the total hours slept in a day
     * @param date the date in question
     * @return the number of hours slept
     */
    public float getTotalHoursOfSleep(LocalDate date) {
        return getSleepLogEntries(date).stream()
                                       .map(entry -> entry.getDuration().toMinutes())
                                       .reduce(Long::sum)
                                       .orElse(0L) / 60f;
    }

    public void putMood(LocalDate date, Quality mood) {
        moodQuality.put(date, mood);
    }

    public Quality getMood(LocalDate date) {
        return moodQuality.get(date);
    }

    /**
     * Get the average mood for a month in a year
     * @param month the month
     * @param year the year
     * @return the average mood
     */
    public Quality getAverageMood(Month month, int year) {
        double averageMood = moodQuality.entrySet().stream()
                                              .filter(entry -> entry.getKey().getMonth() == month && entry.getKey().getYear() == year)
                                              .mapToInt(entry -> entry.getValue().ordinal())
                                              .summaryStatistics().getAverage();
        return Quality.values()[(int) Math.round(averageMood)];
    }
}
