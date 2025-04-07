package fitnessapp.gui.exercise.cardio;

import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.SwimStyle;
import fitnessapp.physical.exercise.cardio.SwimmingExercise;
import javafx.scene.control.ChoiceBox;

public class SwimmingExerciseForm extends CardioExerciseForm {
    private ChoiceBox<SwimStyle> swimStyleCB;

    public SwimmingExerciseForm() {
        swimStyleCB = new ChoiceBox<>();
        swimStyleCB.getItems().addAll(SwimStyle.values());

        addRow("Swim Style:", swimStyleCB);
    }

    @Override
    protected void fillDetails(Exercise exercise) {
        if (!(exercise instanceof SwimmingExercise)) {
            throw new IllegalArgumentException("Expected a SwimingExercise to fill out");
        }

        super.fillDetails(exercise);

        SwimmingExercise swimmingExercise = (SwimmingExercise) exercise;
        swimmingExercise.setSwimStyle(swimStyleCB.getValue());
    }

    @Override
    protected Exercise createExercise() {
        return new SwimmingExercise();
    }
}
