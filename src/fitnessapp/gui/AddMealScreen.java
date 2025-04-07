package fitnessapp.gui;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import fitnessapp.account.Account;
import fitnessapp.fileio.CSVData;
import fitnessapp.physical.foodstuff.Food;
import fitnessapp.physical.foodstuff.MealType;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A screen for creating and adding foods
 */
public class AddMealScreen extends Screen {

    private ChoiceBox<Food> foodCB;
    private ChoiceBox<MealType> mealTypeCB;
    
    private Spinner<Integer> quantitySpinner;

    private DatePicker datePicker;

    private Label errorLabel;

    private Button returnButton;
    private Button submitButton;

    private ObjectProperty<Account> account;

    private static final Logger LOGGER = Logger.getLogger(AddMealScreen.class.getName());

    public AddMealScreen(Consumer<String> switchScreen, ObjectProperty<Account> account) {
        super(switchScreen);

        this.account = account;

        BorderPane mainPane = new BorderPane();

        VBox contentVBox = new VBox();
        contentVBox.setAlignment(Pos.CENTER);
        contentVBox.setSpacing(10);

        Label promptLabel = new Label("Add your meal below");
        contentVBox.getChildren().add(promptLabel);

        FormGridPane gridPane = new FormGridPane();

        foodCB = new ChoiceBox<>();
        CSVData.getFoods().values().stream().sorted(Comparator.comparing(Food::getName)).forEach(foodCB.getItems()::add);
        gridPane.addRow("Food:", foodCB);
        
        mealTypeCB = new ChoiceBox<>();
        mealTypeCB.getItems().addAll(MealType.values());
        gridPane.addRow("Meal Type:", mealTypeCB);

        quantitySpinner = new Spinner<>();
        quantitySpinner.setEditable(true);
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
        quantitySpinner.getEditor().setTextFormatter(new TextFormatter<>(TextFormatterFilters.INT_FILTER));
        gridPane.addRow("Quantity:", quantitySpinner);

        datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        gridPane.addRow("Date:", datePicker);

        errorLabel = new Label();
        errorLabel.setWrapText(true);
        errorLabel.setTextFill(Color.RED);
        gridPane.add(errorLabel, 0, gridPane.getRowCount(), 2, 1);

        //Submit button
        int buttonRow = gridPane.getRowCount();

        returnButton = new Button("Return");
        returnButton.setOnAction(e -> switchScreen("mainHealth"));
        gridPane.add(returnButton, 0, buttonRow);

        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> onSubmit());
        gridPane.add(submitButton, 1, buttonRow);

        contentVBox.getChildren().add(gridPane);

        mainPane.setTop(new HeaderVBox());
        mainPane.setCenter(contentVBox);
        getScene().setRoot(mainPane);
    }

    @Override
    public void onEnterScreen() {
        if (account.get() == null) {
            LOGGER.severe("Expected an account when entering AddMealScreen!");
            switchScreen("welcome");
        }
    }

    private void onSubmit() {
        try {
            Food selectedFood = foodCB.getValue();
            if (selectedFood == null) {
                errorLabel.setText("Please select a food");
                return;
            }
            if (datePicker.getValue() == null) {
                errorLabel.setText("Please select a date");
                return;
            }

            Food food = selectedFood.createEntry(datePicker.getValue(), mealTypeCB.getValue(), quantitySpinner.getValue());
            account.get().getPerson().getPhysicalHealth().getFoods().add(food);

            errorLabel.setText("");
            switchScreen("mainHealth");
        } catch (Throwable throwable) {
            errorLabel.setText(throwable.getMessage());
            LOGGER.log(Level.WARNING, "Food form failed validation!", throwable);
        }
    }
    
    @Override
    public String getTitle() {
        return "Add Meal Entry";
    }
}