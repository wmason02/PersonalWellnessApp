package fitnessapp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import fitnessapp.account.Account;
import fitnessapp.gui.AddExerciseScreen;
import fitnessapp.gui.AddMealScreen;
import fitnessapp.gui.MainHealthScreen;
import fitnessapp.gui.PersonalInformationScreen;
import fitnessapp.gui.Screen;
import fitnessapp.gui.SignInScreen;
import fitnessapp.gui.StatsScreen;
import fitnessapp.gui.WelcomeScreen;
import fitnessapp.gui.exercise.ExerciseForm;
import fitnessapp.gui.exercise.calisthenics.CalisthenicsExerciseForm;
import fitnessapp.gui.exercise.calisthenics.WeightLiftingExerciseForm;
import fitnessapp.gui.exercise.cardio.CyclingExerciseForm;
import fitnessapp.gui.exercise.cardio.SwimmingExerciseForm;
import fitnessapp.gui.exercise.cardio.WalkingExerciseForm;
import fitnessapp.physical.exercise.Activity;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;

public class FitnessGuiApp extends Application {
    private Map<String, Screen> screens = new HashMap<>();
    private Stage primaryStage;
    private ObjectProperty<Account> account;

    public static void main(String[] args) {
        FitnessGuiApp.launch(args);
    }

    /**
     * Create and initialize all available screens
     */
    private void createScreens() {
        Consumer<String> switchScreen = screen -> switchScreen(screen);

        screens.put("welcome", new WelcomeScreen(switchScreen));
        screens.put("signIn", new SignInScreen(switchScreen, account));
        screens.put("personalInfo", new PersonalInformationScreen(switchScreen, account));
        screens.put("mainHealth", new MainHealthScreen(switchScreen, account));
        screens.put("addMeal", new AddMealScreen(switchScreen, account));
        screens.put("stats", new StatsScreen(switchScreen, account));

        Map<Activity, ExerciseForm> exerciseForms = new HashMap<>();
        exerciseForms.put(Activity.WALKING, new WalkingExerciseForm());
        exerciseForms.put(Activity.BICYCLING, new CyclingExerciseForm());
        exerciseForms.put(Activity.CALISTHENICS, new CalisthenicsExerciseForm());
        exerciseForms.put(Activity.SWIMMING, new SwimmingExerciseForm());
        exerciseForms.put(Activity.WEIGHTS, new WeightLiftingExerciseForm());
        AddExerciseScreen addExerciseScreen = new AddExerciseScreen(switchScreen, exerciseForms, account);
        screens.put("addExercise", addExerciseScreen);
    }

    /**
     * Switch the currently displayed screen
     * @param name the name of the screen
     * @throws IllegalArgumentException if the screen name does not correspond with an actual screen
     */
    private void switchScreen(String name) {
        if (!screens.containsKey(name)) {
            throw new IllegalArgumentException("Screen " + name + " does not exist!");
        }
        Screen screen = screens.get(name);
        primaryStage.setScene(screen.getScene());
        primaryStage.setTitle(screen.getTitle());
        screen.onEnterScreen();
    }

    /**
     * Called when the application is started
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.account = new SimpleObjectProperty<Account>();
        createScreens();
        
        switchScreen("welcome");
        primaryStage.show();
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
    }

    /**
     * Called when the application is closing.
     * Saves the open account
     */
    @Override
    public void stop() throws Exception {
        if (account.get() != null) {
            account.get().save();
        }
    }
    
}
