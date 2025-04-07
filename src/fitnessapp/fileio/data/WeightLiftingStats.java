package fitnessapp.fileio.data;
import fitnessapp.physical.exercise.Activity;

/**
 *  Represents the stats (individual row) for weightlifting activity within the CSV
 */
public class WeightLiftingStats extends ExerciseStats{
    public WeightLiftingStats(String intensity, double METValue) {
        super(Activity.WEIGHTS, intensity, METValue);
    }
}
