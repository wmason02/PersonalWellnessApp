package fitnessapp.gui.exercise.cardio;

import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.Incline;
import fitnessapp.physical.exercise.cardio.WalkingExercise;
import javafx.scene.control.ChoiceBox;

public class WalkingExerciseForm extends CardioExerciseForm {
    private ChoiceBox<Incline> inclineCB;

    public WalkingExerciseForm() {
        inclineCB = new ChoiceBox<>();
        inclineCB.getItems().addAll(Incline.values());

        addRow("Incline:", inclineCB);
    }

    @Override
    protected void fillDetails(Exercise exercise) {
        if (!(exercise instanceof WalkingExercise)) {
            throw new IllegalArgumentException("Expected a WalkingExercise to fill out");
        }

        super.fillDetails(exercise);

        WalkingExercise walkingExercise = (WalkingExercise) exercise;
        walkingExercise.setIncline(inclineCB.getValue());
    }

    @Override
    protected Exercise createExercise() {
        return new WalkingExercise();
    }
}
