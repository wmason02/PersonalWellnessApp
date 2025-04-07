package fitnessapp.mental;

import fitnessapp.TimestampedPeriod;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class SleepLogEntry implements TimestampedPeriod, Serializable {
    private Quality quality = Quality.NEUTRAL;
    private LocalDateTime sleepTimestamp = LocalDateTime.now();
    private LocalDateTime wakeupTimestamp = sleepTimestamp;

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setStartTimestamp(LocalDateTime startTimestamp) {
        if (startTimestamp.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Sleep timestamp can't be in the future");
        }
        this.sleepTimestamp = startTimestamp;
    }

    @Override
    public LocalDateTime getStartTimestamp() {
        return sleepTimestamp;
    }

    public void setEndTimestamp(LocalDateTime endTimestamp) {
        if (endTimestamp.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Wake up timestamp can't be in the future");
        } else if (endTimestamp.isBefore(sleepTimestamp)) {
            throw new IllegalArgumentException("Wake up timestamp can't be before the sleep timestamp");
        }
        this.wakeupTimestamp = endTimestamp;
    }

    @Override
    public LocalDateTime getEndTimestamp() {
        return wakeupTimestamp;
    }

    public Duration getDuration() {
        return Duration.between(sleepTimestamp, wakeupTimestamp);
    }
}
