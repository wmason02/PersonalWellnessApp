package fitnessapp.gui.exercise.calisthenics;

import fitnessapp.gui.TextFormatterFilters;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;
import fitnessapp.physical.exercise.calisthenics.WeightLiftingExercise;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class WeightLiftingExerciseForm extends CalisthenicsExerciseForm {
    private TextField weightField;

    public WeightLiftingExerciseForm() {
        weightField = new TextField();
        weightField.setPromptText("lb.");
        weightField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.DOUBLE_FILTER));
        addRow("Weight:", weightField);
    }

    @Override
    protected void fillDetails(Exercise exercise) {
        if (!(exercise instanceof WeightLiftingExercise)) {
            throw new IllegalArgumentException("Expected a WeightLiftingExercise to fill out");
        }

        super.fillDetails(exercise);

        WeightLiftingExercise weightLiftingExercise = (WeightLiftingExercise) exercise;
        try {
            weightLiftingExercise.setWeight(0.453592 * Double.parseDouble(weightField.getText()));
        } catch (NumberFormatException e) {
            throw new InvalidExercisePropertyException(weightLiftingExercise, "Weight", weightField.getText(), "Weight must be a number.");
        }
    }

    @Override
    protected Exercise createExercise() {
        return new WeightLiftingExercise();
    }
}
