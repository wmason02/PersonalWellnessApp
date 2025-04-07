package fitnessapp.physical.exercise;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import fitnessapp.Person;
import fitnessapp.TimestampedPeriod;

/**
 * An exercise
 *  
 * @author Nick Kurth
 * @version 1
 */
public abstract class Exercise implements TimestampedPeriod, Serializable {
    private LocalDateTime timestamp = LocalDateTime.now();
    private Duration duration = Duration.ZERO;
    private Intensity intensity;

    /**
     * Set the starting timestamp
     * @param timestamp the starting time
     */
    public void setStartTimestamp(LocalDateTime timestamp) {
        if (timestamp.isAfter(LocalDateTime.now())) {
            throw new InvalidExercisePropertyException(this, "Start Timestamp", timestamp, "Timestamp can't be in the future");
        }
        this.timestamp = timestamp;
    }

    /**
     * @return the start timestamp
     */
    public LocalDateTime getStartTimestamp() {
        return timestamp;
    }

    /**
     * Set the duration of this exercise with a period
     * @param duration the duration of the exercise
     */
    public void setDuration(Duration duration) {
        if (duration.isNegative()) {
            throw new InvalidExercisePropertyException(this, "Duration", duration, "Duration can't be negative.");
        }
        if (duration.isZero()) {
            throw new InvalidExercisePropertyException(this, "Duration", duration, "Duration can't be zero.");
        }
        this.duration = duration;
    }

    /**
     * Set the duration of this exercise with an end timestamp
     * @param endTimestamp the ending timestamp
     */
    public void setDuration(LocalDateTime endTimestamp) {
        setDuration(Duration.between(timestamp, endTimestamp));
    }
    
    /**
     * @return the duration of the exercise
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * @return the exercise's ending timestamp
     */
    public LocalDateTime getEndTimestamp() {
        return timestamp.plus(duration); // Returns a copy
    }

    /**
     * @return the exercise's name
     */
    public abstract String getName();

    /**
     * @param person the person who did the exercise
     * @return the number of calories burnt
     */
    public int getCaloriesBurnt(Person person){
        return (int) (getDuration().toMinutes() * getMET() * 3.5 * person.getWeight() / 200.0);
    }

    protected abstract double getMET();

    /**
     * Set this cardio excersise's intensity
     * @param intensity the intensity
     */
    public void setIntensity(Intensity intensity) {
        if (intensity == null) {
            throw new InvalidExercisePropertyException(this, "Intensity", intensity, "Intensity can't be null");
        }
        this.intensity = intensity;
    }

    /**
     * @return the intensity of this cardio excersise
     */
    public Intensity getIntensity() {
        return intensity;
    }
}
