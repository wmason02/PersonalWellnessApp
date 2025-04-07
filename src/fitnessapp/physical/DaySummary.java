package fitnessapp.physical;

import java.time.Duration;
import java.time.LocalDate;

/**
 * A summary with various statistics for a single day
 * @author David Choo
 * @version 1.0
 */
public class DaySummary {
    private final LocalDate date;
    private final int caloriesBurnt;
    private final int caloriesConsumed;
    private final int numFoodsConsumed;
    private final Duration totalExerciseDuration;

    public DaySummary(LocalDate date, int caloriesBurnt, int caloriesConsumed, int numFoodsConsumed, Duration totalExerciseDuration) {
        this.date = date;
        this.caloriesBurnt = caloriesBurnt;
        this.caloriesConsumed = caloriesConsumed;
        this.numFoodsConsumed = numFoodsConsumed;
        this.totalExerciseDuration = totalExerciseDuration;        
    }

    /**
     * @return the day this summary refers to
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return the total calories burnt on this day
     */
    public int getCaloriesBurnt() {
        return caloriesBurnt;
    }

    /**
     * @return the total calories consumed on this day
     */
    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }
    
    /**
     * @return the net calories for this day
     */
    public int getNetCalories() {
        return caloriesConsumed - caloriesBurnt;
    }

    /**
     * @return the estmated weight change for this day
     */
    public double getEstNetWeightLB() {
        return getNetCalories() / 3500f;
    }

    /**
     * @return the total number of foods consumed
     */
    public int getNumFoodsConsumed() {
        return numFoodsConsumed;
    }

    /**
     * @return the total time spent exercising
     */
    public Duration getTotalExerciseDuration() {
        return totalExerciseDuration;
    }
}
