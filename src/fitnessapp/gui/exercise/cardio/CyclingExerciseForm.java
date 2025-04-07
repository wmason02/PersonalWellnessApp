package fitnessapp.gui.exercise.cardio;

import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.Incline;
import fitnessapp.physical.exercise.cardio.CyclingExercise;
import javafx.scene.control.ChoiceBox;

public class CyclingExerciseForm extends CardioExerciseForm {
    private ChoiceBox<Incline> inclineCB;

    public CyclingExerciseForm() {
        inclineCB = new ChoiceBox<>();
        inclineCB.getItems().addAll(Incline.values());

        addRow("Incline:", inclineCB);
    }

    @Override
    protected void fillDetails(Exercise exercise) {
        if (!(exercise instanceof CyclingExercise)) {
            throw new IllegalArgumentException("Expected a CyclingExercise to fill out");
        }

        super.fillDetails(exercise);

        CyclingExercise cyclingExercise = (CyclingExercise) exercise;
        cyclingExercise.setIncline(inclineCB.getValue());
    }

    @Override
    protected Exercise createExercise() {
        return new CyclingExercise();
    }
}
