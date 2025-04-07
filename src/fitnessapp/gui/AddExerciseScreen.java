package fitnessapp.gui;

import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import fitnessapp.account.Account;
import fitnessapp.gui.exercise.ExerciseForm;
import fitnessapp.physical.exercise.Activity;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.InvalidExercisePropertyException;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A screen for creating and adding exercises
 */
public class AddExerciseScreen extends Screen {
    private ChoiceBox<Activity> exerciseCB;

    private VBox exerciseFormVBox;
    private ExerciseForm currentExerciseForm;

    private Map<Activity, ExerciseForm> exerciseForms;

    private Label errorLabel;
    private Button returnButton = new Button("Back to Welcome Screen");
    private Button submitButton = new Button("Submit");

    private ObjectProperty<Account> account;

    private static final Logger LOGGER = Logger.getLogger(AddExerciseScreen.class.getName());

    public AddExerciseScreen(Consumer<String> switchScreen, Map<Activity, ExerciseForm> exerciseForms, ObjectProperty<Account> account) {
        super(switchScreen);

        this.exerciseForms = exerciseForms;
        this.account = account;

        //Prompt
        VBox formVBox = new VBox();
        formVBox.setAlignment(Pos.CENTER);
        Label promptLabel = new Label("Record your exercise");

        FormGridPane mainGridPane = new FormGridPane();

        // Exercise choice box
        exerciseCB = new ChoiceBox<Activity>();
        exerciseCB.getItems().addAll(exerciseForms.keySet()); // Only add exercises which have a form
        exerciseCB.setOnAction(e -> setExerciseForm(exerciseForms.get(exerciseCB.getValue())));
        mainGridPane.addRow("Exercise:", exerciseCB);

        exerciseFormVBox = new VBox();
        exerciseFormVBox.setAlignment(Pos.CENTER);

        FormGridPane buttonGridPane = new FormGridPane();

        errorLabel = new Label();
        errorLabel.setWrapText(true);
        errorLabel.setTextFill(Color.RED);
        buttonGridPane.add(errorLabel, 0, 0, 2, 1);

        returnButton = new Button("Return");
        returnButton.setOnAction(e -> switchScreen("mainHealth")); 
        buttonGridPane.add(returnButton, 0, 1);

        submitButton = new Button("Submit");
        submitButton.setDisable(true);
        submitButton.setOnAction(e -> {
            if (currentExerciseForm != null) {
                Exercise exercise = null;
                try {
                    exercise = currentExerciseForm.getExercise();
                } catch (InvalidExercisePropertyException exception) {
                    LOGGER.log(Level.WARNING, "Invalid exercise property value! Property: " + exception.getProperty() + " Value: " + exception.getInvalidValue(), exception);
                    errorLabel.setText(exception.getMessage());
                } catch (Throwable throwable) {
                    LOGGER.log(Level.SEVERE, "Exercise form threw!", throwable);
                    errorLabel.setText(throwable.getMessage());
                }
                if (exercise != null) {
                    LOGGER.info("Created exercise with name: " + exercise.getName());
                    boolean result = account.get().getPerson().getPhysicalHealth().addExercise(exercise);
                    if (result) {
                        errorLabel.setText("");
                        switchScreen("mainHealth");
                    } else {
                        LOGGER.warning("Exercise conflicts with another time period");
                        errorLabel.setText("Time period conflicts with another exercise.");
                    }
                }
            }
        });
        buttonGridPane.add(submitButton, 1, 1);

        //Set border pane and scene for pane 5
        formVBox.getChildren().addAll(promptLabel, mainGridPane, exerciseFormVBox, buttonGridPane);
        formVBox.setSpacing(10);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(new HeaderVBox());
        borderPane.setCenter(formVBox);

        getScene().setRoot(borderPane);
    }

    /**
     * Change the currently displayed exercise form
     * @param exerciseForm the new exercise form
     */
    private void setExerciseForm(ExerciseForm exerciseForm) {
        this.currentExerciseForm = exerciseForm;
        submitButton.setDisable(this.currentExerciseForm == null);
        errorLabel.setText("");

        exerciseFormVBox.getChildren().clear();
        exerciseFormVBox.getChildren().add(currentExerciseForm);
    }

    @Override
    public String getTitle() {
        return "Add Exercise Entry";
    }
}