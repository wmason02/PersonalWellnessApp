package fitnessapp;

import java.time.LocalDateTime;

/**
 * Classes that have a start and end timestamp
*/
public interface TimestampedPeriod {
    LocalDateTime getStartTimestamp();
    LocalDateTime getEndTimestamp();

    /**
     * Check if two periods overlap 
     * 
     * @param otherPeriod the other period
     * 
     * @return true if time periods collide, otherwise false
     */
    default boolean overlaps(TimestampedPeriod otherPeriod) {
        return getStartTimestamp().isBefore(otherPeriod.getEndTimestamp()) && getEndTimestamp().isAfter(otherPeriod.getStartTimestamp());
    }
}
