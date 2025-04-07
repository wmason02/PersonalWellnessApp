package fitnessapp.physical.exercise;

/**
 * An exception thrown when attempting to set a property of an exercise
 * with an invalid value
 *  
 * @author David Choo
 * @version 1
 */
public class InvalidExercisePropertyException extends IllegalArgumentException {

    private final Exercise exercise;
    private final String property;
    private final Object invalidValue;

    public InvalidExercisePropertyException(Exercise exercise, String property, Object invalidValue, String message) {
        super(message);
        this.exercise = exercise;
        this.property = property;
        this.invalidValue = invalidValue;
    }
    
    /**
     * @return the erroring exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * @return the property that was attempted to change
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return the invalid value
     */
    public Object getInvalidValue() {
        return invalidValue;
    }
}
