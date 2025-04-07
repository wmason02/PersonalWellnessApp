package fitnessapp.fileio.data;
import fitnessapp.physical.exercise.Activity;

/**
 *  Represents the stats (individual row) for calisthenics activity within the CSV
 */
public class CalisthenicsStats extends ExerciseStats{
    public CalisthenicsStats(String intensity, double METValue) {
        super(Activity.CALISTHENICS, intensity, METValue);
    }
}
