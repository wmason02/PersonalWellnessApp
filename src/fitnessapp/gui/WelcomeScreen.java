package fitnessapp.gui;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class WelcomeScreen extends Screen {

    private Button signInButton;

    public WelcomeScreen(Consumer<String> switchScreen) {
        super(switchScreen);
        BorderPane mainPane = new BorderPane();
        
        signInButton = new Button("Continue to Sign-In");
        signInButton.setOnAction(e -> switchScreen("signIn")); 

        //Creates a second vbox to hold the button, to later be set as the center of the pane
        VBox box2 = new VBox();
        box2.getChildren().addAll(signInButton);
        box2.setAlignment(Pos.CENTER);
        box2.setStyle("-fx-background-color: white;");
        box2.setPadding(new Insets(0, 0, 20, 0));
        VBox.setMargin(signInButton, new Insets(20));

        //Sets the top and center of the pane to the two vboxs
        mainPane.setTop(new LogoVBox());
        mainPane.setCenter(box2);

        getScene().setRoot(mainPane);
    }

    @Override
    public String getTitle() {
        return "Welcome";
    }

    public Button getSignInButton() {
        return signInButton;
    }
}