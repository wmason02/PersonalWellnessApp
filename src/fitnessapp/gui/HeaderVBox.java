package fitnessapp.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * A VBox with the title of the app
 */
public class HeaderVBox extends VBox {
    public HeaderVBox() {
        setStyle("-fx-background-color: gainsboro;");
        setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Personal Wellness");
        titleLabel.setFont(Font.font("Lucida Sans Unicode", 20));
        getChildren().addAll(titleLabel);
        VBox.setMargin(titleLabel, new Insets(20));
    }
}
