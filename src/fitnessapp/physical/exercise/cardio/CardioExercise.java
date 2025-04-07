package fitnessapp.physical.exercise.cardio;

//import fitnessapp.Person;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;

public abstract class CardioExercise extends Exercise {
    /**
     * Distance in meters
     */
    private double distance;

    /**
     * Set the distance traveled in meters
     * @param distance the distance in meters
     */
    public void setDistance(double distance) {
        if (distance < 0) {
            throw new InvalidExercisePropertyException(this, "Distance", distance, "Distance can't be negative");
        }
        this.distance = distance;
    }

    /**
     * @return the distance traveled in meters
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return the average speed in meters per second
     */
    public double getAverageSpeed() {
        return distance / getDuration().getSeconds();
    }
}
