package fitnessapp.gui.exercise.calisthenics;

import fitnessapp.gui.TextFormatterFilters;
import fitnessapp.gui.exercise.ExerciseForm;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;
import fitnessapp.physical.exercise.calisthenics.CalisthenicsExercise;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class CalisthenicsExerciseForm extends ExerciseForm {
    private TextField repetitionsField;
    private TextField setsField;

    public CalisthenicsExerciseForm() {
        repetitionsField = new TextField();
        repetitionsField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.INT_FILTER));
        addRow("Repetitions:", repetitionsField);
        
        setsField = new TextField();
        setsField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.INT_FILTER));
        addRow("Sets:", setsField);
    }

    @Override
    protected void fillDetails(Exercise exercise) {
        if (!(exercise instanceof CalisthenicsExercise)) {
            throw new IllegalArgumentException("Expected a CalisthenicsExercise to fill out");
        }

        super.fillDetails(exercise);

        CalisthenicsExercise calisthenicsExercise = (CalisthenicsExercise) exercise;
        try {
            calisthenicsExercise.setRepetitions(Integer.valueOf(repetitionsField.getText()));
        } catch (NumberFormatException e) {
            throw new InvalidExercisePropertyException(calisthenicsExercise, "Repetitions", repetitionsField.getText(), "Repetitions must be a number.");
        }
        try {
            calisthenicsExercise.setSets(Integer.valueOf(setsField.getText()));
        } catch (NumberFormatException e) {
            throw new InvalidExercisePropertyException(calisthenicsExercise, "Sets", setsField.getText(), "Sets must be a number.");
        }
        
    }

    @Override
    protected Exercise createExercise() {
        return new CalisthenicsExercise();
    }
}
