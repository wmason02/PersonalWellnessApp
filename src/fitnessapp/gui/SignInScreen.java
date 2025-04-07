package fitnessapp.gui;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import fitnessapp.account.Account;
import fitnessapp.account.AccountException;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SignInScreen extends Screen {

    private TextField userNameField;
    private TextField passwordField;
    private Label errorLabel;
    private Button returnButton;
    private Button createButton;
    private Button loginButton;

    private ObjectProperty<Account> account;

    private static final Logger LOGGER = Logger.getLogger(SignInScreen.class.getName());

    public SignInScreen(Consumer<String> switchScreen, ObjectProperty<Account> account) {
        super(switchScreen);
        this.account = account;

        BorderPane mainPane = new BorderPane();

        //Prompt to create a username and passwrod
        Label promptLabel = new Label("Enter your username and password");
        promptLabel.setFont(Font.font("Lucida Sans Unicode", 15));
        
        FormGridPane gridPane = new FormGridPane();

        userNameField = new TextField();
        userNameField.setTextFormatter(new TextFormatter<>(TextFormatterFilters.ALPHA_NUMERIC_FILTER));
        gridPane.addRow("Username:", userNameField);

        passwordField = new PasswordField();
        gridPane.addRow("Password:", passwordField);

        errorLabel = new Label();
        GridPane.setHgrow(errorLabel, Priority.ALWAYS);
        errorLabel.setWrapText(true);
        errorLabel.setTextFill(Color.RED);
        gridPane.add(errorLabel, 0, gridPane.getRowCount(), 2, 1);

        //Button to return to welcome screen
        int buttonRow = gridPane.getRowCount();
        returnButton = new Button("Return");
        returnButton.setOnAction(e -> switchScreen("welcome"));
        gridPane.add(returnButton, 0, buttonRow);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonHBox.setSpacing(10);

        createButton = new Button("Create");
        createButton.setOnAction(e -> onCreateAccount());
        buttonHBox.getChildren().add(createButton);

        //login button (Will later be changed to set the users username and password to what they entered)
        loginButton = new Button("Login");
        loginButton.setOnAction(e -> onLoginAccount());
        buttonHBox.getChildren().add(loginButton);

        gridPane.add(buttonHBox, 1, buttonRow);
        // button3.setOnAction(e -> Person.setUserName(textfield1.getText()));
        // button3.setOnAction(e -> Person.setPassword(textfield2.getText()));

        //vbox to hold label2, grid pane, the return button, and the submit button
        VBox box4 = new VBox();
        box4.getChildren().addAll(promptLabel, gridPane);
        box4.setAlignment(Pos.CENTER);
        box4.setStyle("-fx-background-color: white;");
        box4.setPadding(new Insets(0, 0, 20, 0));
        box4.setSpacing(10);

        //setting the pane and scene
        mainPane.setTop(new LogoVBox());
        mainPane.setCenter(box4);
        getScene().setRoot(mainPane);
    }
    
    @Override
    public void onEnterScreen() {
        userNameField.setText("");
        passwordField.setText("");
        errorLabel.setText("");
    }

    private void onCreateAccount() {
        try {
            String username = userNameField.getText();
            String password = passwordField.getText();

            Account newAccount = new Account(username, password);
            account.set(newAccount);
            LOGGER.log(Level.FINE, "Succesfully created account with Username: " + username);

            errorLabel.setText("");
            switchScreen("personalInfo");
        } catch (AccountException e) {
            LOGGER.log(Level.SEVERE, "", e);
            errorLabel.setText(e.getMessage());
        }
    }

    private void onLoginAccount() {
        try {
            String username = userNameField.getText();
            String password = passwordField.getText();
            Account newAccount = Account.retrieveAccount(username, password);
            newAccount.save();
            account.set(newAccount);

            errorLabel.setText("");
            switchScreen("mainHealth");
        } catch (AccountException e) {
            LOGGER.log(Level.WARNING, "", e);
            errorLabel.setText(e.getMessage());
        } catch (NoSuchFileException e) {
            LOGGER.log(Level.WARNING, "", e);
            errorLabel.setText("Account does not exist.");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "", e);
            errorLabel.setText(e.getMessage());
        }
    }

    @Override
    public String getTitle() {
        return "Sign In";
    }

    public TextField userNameField() {
        return userNameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getReturnButton() {
        return returnButton;
    }
    
    public Button getSubmitButton() {
        return loginButton;
    }
}