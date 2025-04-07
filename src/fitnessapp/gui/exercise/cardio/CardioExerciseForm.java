package fitnessapp.gui.exercise.cardio;

import fitnessapp.gui.TextFormatterFilters;
import fitnessapp.gui.exercise.ExerciseForm;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;
import fitnessapp.physical.exercise.cardio.CardioExercise;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public abstract class CardioExerciseForm extends ExerciseForm {
    private TextField distanceField;

    public CardioExerciseForm() {
        distanceField = new TextField();
        distanceField.setPromptText("miles");
        distanceField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.DOUBLE_FILTER));
        addRow("Distance:", distanceField);
    }

    public TextField getDistanceField() {
        return distanceField;
    }

    @Override
    protected void fillDetails(Exercise exercise) {
        if (!(exercise instanceof CardioExercise)) {
            throw new IllegalArgumentException("Expected a CardioExercise to fill out");
        }

        super.fillDetails(exercise);

        CardioExercise cardioExercise = (CardioExercise) exercise;
        try {
            cardioExercise.setDistance(1609.34 * Double.parseDouble(distanceField.getText()));
        } catch (NumberFormatException e) {
            throw new InvalidExercisePropertyException(cardioExercise, "Distance", distanceField.getText(), "Distance must be a number.");
        }
    }
}
