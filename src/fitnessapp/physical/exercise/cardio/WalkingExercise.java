package fitnessapp.physical.exercise.cardio;

import fitnessapp.fileio.CSVData;
import fitnessapp.fileio.data.WalkingStats;
import fitnessapp.physical.exercise.Incline;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;

public class WalkingExercise extends CardioExercise {

    private Incline incline;

    @Override
    public String getName() {
        return "Walking";
    }

    public void setIncline(Incline incline){
        if (incline == null) {
            throw new InvalidExercisePropertyException(this, "Incline", incline, "Incline can't be null");
        }
        this.incline = incline;
    }

    public Incline getIncline(){
        return incline;
    }

    public double getMET(){
        return CSVData.getExercisesStats()
            .stream()
            .filter(a -> a instanceof WalkingStats)
            .map(a -> (WalkingStats) a)
            .filter(a -> a.getIntensity().equals(getIntensity()))
            .filter(a -> a.getIncline().equals(getIncline()))
            .findFirst()
            .get()
            .getMETValue();
    }
}
