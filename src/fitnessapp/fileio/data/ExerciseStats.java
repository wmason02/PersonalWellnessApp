package fitnessapp.fileio.data;

import fitnessapp.physical.exercise.Activity;
import fitnessapp.physical.exercise.Intensity;

/**
 *  Represents the an abstract, overarching attributes shared by all stats from CSV
 */
public abstract class ExerciseStats {
    private final Activity activity;
    private final Intensity intensity;
    private final double METValue;

    public ExerciseStats(Activity activity, String intensity, double METValue){
        this.activity = activity;
        this.intensity = Intensity.valueOf(intensity.strip().toUpperCase());
        this.METValue = METValue;
    }

    /**
     * @return activity name of the exercise data point
     */
    public Activity getActivity(){
        return activity;
    }

    /**
     * @return intensity of the exercise data point
     */
    public Intensity getIntensity(){
        return intensity;
    }

    /**
     * @return METValue of the exercise data point
     */
    public double getMETValue(){
        return METValue;
    }
}
