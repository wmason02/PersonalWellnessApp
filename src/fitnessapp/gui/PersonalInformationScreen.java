package fitnessapp.gui;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import fitnessapp.Gender;
import fitnessapp.Person;
import fitnessapp.account.Account;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PersonalInformationScreen extends Screen {

    private TextField nameField;
    private DatePicker birthdayField;
    private TextField weightField;
    private TextField targetWeightField;
    private TextField heightFTField;
    private TextField heightINField;
    private ChoiceBox<Gender> genderCB;
    private Label errorLabel;
    private Button returnButton;
    private Button submitButton;

    private ObjectProperty<Account> account;

    private static final Logger LOGGER = Logger.getLogger(PersonalInformationScreen.class.getName());

    public PersonalInformationScreen(Consumer<String> switchScreen, ObjectProperty<Account> account) {
        super(switchScreen);
        this.account = account;

        BorderPane mainPane = new BorderPane();

        //creates a vbox that will hold all of the fields and labels for the entered data
        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.CENTER);
        centerVBox.setPadding(new Insets(0, 0, 20, 0));

        //prompt the user to enter information
        Label promptLabel = new Label("Please enter some personal information below. Then select submit");
        promptLabel.setFont(Font.font("Lucida Sans Unicode", 10));

        FormGridPane gridPane = new FormGridPane();

        nameField = new TextField();
        nameField.setPromptText("First and Last");
        gridPane.addRow("Name:", nameField);

        birthdayField = new DatePicker();
        gridPane.addRow("Birthday:", birthdayField);

        //Hbox to hold weight field and label
        weightField = new TextField();
        weightField.setPromptText("lbs.");
        weightField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.DOUBLE_FILTER));
        gridPane.addRow("Weight:", weightField);

        targetWeightField = new TextField();
        targetWeightField.setPromptText("lbs.");
        targetWeightField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.DOUBLE_FILTER));
        gridPane.addRow("Target Weight:", targetWeightField);

        //Hbox to hold height field and label
        HBox heightHBox = new HBox();
        heightHBox.setSpacing(10);
        heightHBox.setAlignment(Pos.CENTER);
        
        heightFTField = new TextField();
        heightFTField.setPromptText("ft");
        heightFTField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.INT_FILTER));
        heightINField = new TextField();
        heightINField.setPromptText("in");
        heightINField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.DOUBLE_FILTER));
        
        heightHBox.getChildren().addAll(heightFTField, heightINField);
        gridPane.addRow("Height:", heightHBox);

        genderCB = new ChoiceBox<Gender>();
        genderCB.getItems().addAll(Gender.values());
        gridPane.addRow("Gender:", genderCB);

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setWrapText(true);
        gridPane.add(errorLabel, 0, gridPane.getRowCount(), 2, 1);

        int buttonRow = gridPane.getRowCount();
        returnButton = new Button("Return");
        returnButton.setOnAction(e -> switchScreen("welcome")); 
        gridPane.add(returnButton, 0, buttonRow);

        //submit button (Needs to be changed later to set the users personal info fields to the entered info)
        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> onSubmit());
        gridPane.add(submitButton, 1, buttonRow);

        //Adds everything to the vbox
        centerVBox.getChildren().addAll(promptLabel, gridPane);
        centerVBox.setSpacing(10);

        //Title of program name and welcome screen button
        Label titleLabel = new Label("Personal Wellness");
        titleLabel.setFont(Font.font("Lucida Sans Unicode", 20));
 
        //vbox to hold label
        VBox titleVBox = new VBox();
        titleVBox.getChildren().addAll(titleLabel);
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setStyle("-fx-background-color: gainsboro;");
        VBox.setMargin(titleLabel, new Insets(20));

        //Sets pane and scene
        mainPane.setTop(titleVBox);
        mainPane.setCenter(centerVBox);
        getScene().setRoot(mainPane);
    }

    @Override
    public void onEnterScreen() {
        if (account.get() == null) {
            LOGGER.severe("Expected an account when entering PersonalInformationScreen!");
            switchScreen("welcome");
        } else {
            Person person = account.get().getPerson();
            loadFromPerson(person);
        }
    }

    private void onSubmit() {
        try {
            saveToPerson(account.get().getPerson());
            account.get().save();
            switchScreen("mainHealth");
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Failed to save account file!", exception);
            errorLabel.setText(exception.getMessage());  
        } catch (Throwable thrown) {
            LOGGER.log(Level.WARNING, "Personal Information Screen failed validation!", thrown);
            errorLabel.setText(thrown.getMessage());
        }
    }

    private void saveToPerson(Person person) {
        person.setFullName(nameField.getText());
        person.setBirthday(birthdayField.getValue());

        person.setWeight(0.453592 * Double.valueOf(weightField.getText()));
        person.setTargetWeight(0.453592 * Double.valueOf(targetWeightField.getText()));
        
        double heightIN = 12 * Integer.valueOf(heightFTField.getText()) + Double.valueOf(heightINField.getText());
        person.setHeightCM(2.54 * heightIN);

        person.setGender(genderCB.getValue());
    }

    private void loadFromPerson(Person person) {
        nameField.setText(person.getFullName());
        birthdayField.setValue(person.getBirthday());

        if (person.getWeight() != 0) {
            weightField.setText(String.valueOf(person.getWeight()));
        }
        if (person.getTargetWeight() != 0) { 
            targetWeightField.setText(String.valueOf(person.getTargetWeight()));
        }
        
        if (person.getHeightIN() != 0) {
            heightFTField.setText(String.valueOf((int) (person.getHeightIN() / 12)));
            heightINField.setText(String.format("%.2f", person.getHeightIN() % 12));
        }

        genderCB.setValue(person.getGender());  
    }

    @Override
    public String getTitle() {
        return "Personal Information";
    }
}