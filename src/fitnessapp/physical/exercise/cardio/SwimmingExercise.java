package fitnessapp.physical.exercise.cardio;

import fitnessapp.fileio.CSVData;
import fitnessapp.fileio.data.SwimmingStats;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;
import fitnessapp.physical.exercise.SwimStyle;

public class SwimmingExercise extends CardioExercise{
    private SwimStyle swimStyle;

    public String getName() {
        return "Swimming";
    }

    public void setSwimStyle(SwimStyle swimStyle){
        if (swimStyle == null) {
            throw new InvalidExercisePropertyException(this, "Swim Style", swimStyle, "Swim style can't be null");
        }
        this.swimStyle = swimStyle;
    }

    public SwimStyle getSwimStyle(){
        return swimStyle;
    }

    public double getMET(){
        return CSVData.getExercisesStats()
            .stream()
            .filter(a -> a instanceof SwimmingStats)
            .map(a -> (SwimmingStats)a)
            .filter(a -> a.getIntensity().equals(getIntensity()))
            .filter(a -> a.getSwimStyle().equals(getSwimStyle()))
            .findFirst()
            .get()
            .getMETValue();
    }
}