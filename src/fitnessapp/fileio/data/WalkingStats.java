package fitnessapp.fileio.data;
import fitnessapp.physical.exercise.Activity;
import fitnessapp.physical.exercise.Incline;

/**
 *  Represents the stats (individual row) for running activity within the CSV
 */
public class WalkingStats extends ExerciseStats{
    private final Incline incline;

    public WalkingStats(String incline, String intensity, double METValue){
        super(Activity.WALKING, intensity, METValue);
        this.incline = Incline.valueOf(incline.strip().toUpperCase());
    }

    /**
     * @return incline of the exercise data point
     */
    public Incline getIncline(){
        return incline;
    }
}
