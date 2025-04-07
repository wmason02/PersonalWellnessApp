package fitnessapp.fileio.data;
import fitnessapp.physical.exercise.Activity;
import fitnessapp.physical.exercise.SwimStyle;

/**
 *  Represents the stats (individual row) for swimming activity within the CSV
 */
public class SwimmingStats extends ExerciseStats{
    private final SwimStyle swimStyle;

    public SwimmingStats(String swimstyle, String intensity, double METValue){
        super(Activity.SWIMMING, intensity, METValue);
        this.swimStyle = SwimStyle.valueOf(swimstyle.strip().toUpperCase());
    }

    /**
     * @return the swim style for an exercise data point
     */
    public SwimStyle getSwimStyle(){
        return swimStyle;
    }
}
