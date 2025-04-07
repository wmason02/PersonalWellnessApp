package fitnessapp.physical.exercise.calisthenics;

import fitnessapp.fileio.CSVData;
import fitnessapp.fileio.data.CalisthenicsStats;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;

public class CalisthenicsExercise extends Exercise {
    private int repetitions;
    private int sets;
    
    public void setRepetitions(int repetitions) {
        if (repetitions <= 0) {
            throw new InvalidExercisePropertyException(this, "Repetitions", repetitions, "Repetitions can't be less than 0 or equal to 0");
        }
        this.repetitions = repetitions;
    }
    
    public int getRepetitions() {
        return repetitions;
    }
    
    public int getSets() {
        return sets;
    }
    
    public void setSets(int sets) {
        if (sets <= 0) {
            throw new InvalidExercisePropertyException(this, "Sets", sets, "Sets can't be less than 0 or equal to 0");
        }
        this.sets = sets;
    }

    public double getMET(){
        return CSVData.getExercisesStats()
            .stream()
            .filter(a -> a instanceof CalisthenicsStats)
            .map(a -> (CalisthenicsStats)a)
            .filter(a -> a.getIntensity().equals(getIntensity()))
            .findFirst()
            .get()
            .getMETValue();
    }

    @Override
    public String getName() {
        return "Calisthenics";
    }
}