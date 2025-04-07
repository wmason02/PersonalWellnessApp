package fitnessapp.gui.exercise;

import java.time.LocalDate;
import java.time.LocalDateTime;

import fitnessapp.gui.FormGridPane;
import fitnessapp.gui.TimeInputHBox;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.Intensity;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

/**
 * A form for creating a exercise
 */
public abstract class ExerciseForm extends FormGridPane {
    private DatePicker datePicker;
    private TimeInputHBox startTimeHBox;
    private TimeInputHBox durationHBox;
    private ChoiceBox<Intensity> intensityCB;

    public ExerciseForm() {
        datePicker = new DatePicker(LocalDate.now());
        addRow("Date:", datePicker);

        startTimeHBox = new TimeInputHBox(true);
        addRow("Start:", startTimeHBox);

        durationHBox = new TimeInputHBox(false);
        addRow("Duration:", durationHBox);

        intensityCB = new ChoiceBox<>();
        intensityCB.getItems().addAll(Intensity.values());
        addRow("Intensity:", intensityCB);
    }

    /**
     * Fill out an exercise object with information from this form
     * @param exercise the exercise
     */
    protected void fillDetails(Exercise exercise) {
        if (datePicker.getValue() == null) {
            throw new NullPointerException("Please select a date");
        }

        exercise.setStartTimestamp(LocalDateTime.of(datePicker.getValue(), startTimeHBox.toTime()));
        exercise.setDuration(durationHBox.toDuration());
        exercise.setIntensity(intensityCB.getValue());
    }

    /**
     * @return a new empty exercise
     */
    protected abstract Exercise createExercise();

    /**
     * @return a new exercise with information from this form
     */
    public Exercise getExercise() {
        Exercise exercise = createExercise();
        fillDetails(exercise);

        return exercise;
    }
}
