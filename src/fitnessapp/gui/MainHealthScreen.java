package fitnessapp.gui;

import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import fitnessapp.account.Account;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.foodstuff.Food;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;

/**
 * A screen with a list of all exercises completed and foods consumed
 */
public class MainHealthScreen extends Screen {
    
    private TableView<Exercise> exerciseTable;
    private Button addExerciseButton;

    private TableView<Food> mealTable;
    private Button addMealButton;

    private Button statisticsButton;
    private Button logoutButton;

    private ObjectProperty<Account> account;

    private static final Logger LOGGER = Logger.getLogger(MainHealthScreen.class.getName());

    public MainHealthScreen(Consumer<String> switchScreen, ObjectProperty<Account> account) {
        super(switchScreen);
        this.account = account;

        BorderPane mainPane = new BorderPane();

        GridPane gridPane = new GridPane();
        BorderPane.setMargin(gridPane, new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label exerciseLabel = new Label("Exercises");
        exerciseLabel.setFont(Font.font("Lucida Sans Unicode", 20));
        gridPane.add(exerciseLabel, 0, 0);

        exerciseTable = new TableView<>();
        addExerciseColumn("Date", exercise -> exercise.getStartTimestamp().format(DateTimeFormatter.ISO_DATE));
        addExerciseColumn("Time", exercise -> exercise.getStartTimestamp().format(DateTimeFormatter.ISO_TIME));
        addExerciseColumn("Duration", exercise -> {
            Duration duration = exercise.getDuration();
            return String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
        });
        addExerciseColumn("Name", Exercise::getName);
        addExerciseColumn("Calories Burned", exercise -> exercise.getCaloriesBurnt(account.get().getPerson()));
        addExerciseColumn("Intensity", Exercise::getIntensity);

        exerciseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setHgrow(exerciseTable, Priority.ALWAYS);
        gridPane.add(exerciseTable, 0, 1);

        addExerciseButton = new Button("Add Exercise");
        addExerciseButton.setOnAction(e -> switchScreen("addExercise"));
        gridPane.add(addExerciseButton, 0, 2);

        Label foodLabel = new Label("Foods");
        foodLabel.setFont(Font.font("Lucida Sans Unicode", 20));
        gridPane.add(foodLabel, 1, 0);

        mealTable = new TableView<>();
        addMealColumn("Date", Food::getDate);
        addMealColumn("Name", Food::getName);
        addMealColumn("Calories", Food::getCalories);
        addMealColumn("Quantity", Food::getQuantity);
        addMealColumn("Serving Size", Food::getServingSize);

        mealTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setHgrow(mealTable, Priority.ALWAYS);
        gridPane.add(mealTable, 1, 1);

        addMealButton = new Button("Add Meal");
        addMealButton.setOnAction(e -> switchScreen("addMeal"));
        gridPane.add(addMealButton, 1, 2);

        ColumnConstraints exerciseColumnConstraints = new ColumnConstraints();
        exerciseColumnConstraints.setPercentWidth(100f / 2f);
        exerciseColumnConstraints.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(exerciseColumnConstraints, exerciseColumnConstraints);

        //HBox that holds the stats and logout button
        HBox bottomHBox = new HBox();
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setSpacing(10);
        bottomHBox.setPadding(new Insets(20));

        statisticsButton = new Button("Statistics");
        statisticsButton.setOnAction(e -> switchScreen("stats"));
        bottomHBox.getChildren().add(statisticsButton);

        logoutButton = new Button("Log out");
        logoutButton.setOnAction(e -> {
                try {
                    if (account.get() != null) {
                        account.get().save();
                        account.set(null);
                    }
                    switchScreen("welcome");
                } catch (IOException exception) {
                    LOGGER.log(Level.SEVERE, "Unable to save account!", exception);
                }
        });
        bottomHBox.getChildren().add(logoutButton);

        //Sets pane and the scene
        mainPane.setTop(new HeaderVBox()); 
        mainPane.setCenter(gridPane);
        mainPane.setBottom(bottomHBox); 
        getScene().setRoot(mainPane);
    }

    /**
     * Add a column of data for exercises
     * @param columnTitle the title of the column
     * @param propertyGetter a function that returns a value for each cell in this column
     */
    private void addExerciseColumn(String columnTitle, Function<Exercise, Object> propertyGetter) {
        TableColumn<Exercise, Object> column = new TableColumn<>(columnTitle);
        column.setCellValueFactory(cellDataFeatures -> {
            return new ReadOnlyObjectWrapper<Object>(propertyGetter.apply(cellDataFeatures.getValue()));
        });
        exerciseTable.getColumns().add(column);
    }

    /**
     * Add a column of data for meals
     * @param columnTitle the title of the column
     * @param propertyGetter a function that returns a value for each cell in this column
     */
    private void addMealColumn(String columnTitle, Function<Food, Object> propertyGetter) {
        TableColumn<Food, Object> column = new TableColumn<>(columnTitle);
        column.setCellValueFactory(cellDataFeatures -> {
            return new ReadOnlyObjectWrapper<Object>(propertyGetter.apply(cellDataFeatures.getValue()));
        });
        mealTable.getColumns().add(column);
    }

    @Override
    public void onEnterScreen() {
        exerciseTable.getItems().clear();
        exerciseTable.getItems().addAll(account.get().getPerson().getPhysicalHealth().getExercises());

        mealTable.getItems().clear();
        mealTable.getItems().addAll(account.get().getPerson().getPhysicalHealth().getFoods());
    }

    @Override
    public String getTitle() {
        return "Overview";
    }
}