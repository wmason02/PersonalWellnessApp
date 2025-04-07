package fitnessapp.physical.exercise.calisthenics;

//import fitnessapp.Person;
import fitnessapp.fileio.CSVData;
import fitnessapp.fileio.data.WeightLiftingStats;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;

public class WeightLiftingExercise extends CalisthenicsExercise {
    /**
     * Weight in kg lifted
     */
    private double weight;

    @Override
    public String getName() {
        return "Weight Lifting";
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new InvalidExercisePropertyException(this, "Weight", weight, "Weight can't be less than or equal to 0");
        }
        this.weight = weight;
    }
    
    @Override
    public double getMET(){
        return CSVData.getExercisesStats()
            .stream()
            .filter(a -> a instanceof WeightLiftingStats)
            .map(a -> (WeightLiftingStats)a)
            .filter(a -> a.getIntensity().equals(getIntensity()))
            .findFirst()
            .get()
            .getMETValue();
    }

}
