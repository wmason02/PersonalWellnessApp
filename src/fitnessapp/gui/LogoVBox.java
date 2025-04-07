package fitnessapp.gui;

import java.nio.file.Path;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * A VBox with the title and logo of the application
 */
public class LogoVBox extends VBox {
    public LogoVBox() {
        Label titleLabel = new Label("Personal Wellness");
        titleLabel.setFont(Font.font("Lucida Sans Unicode", 20));

        // Creates the image
        ImageView logoImage = new ImageView(Path.of("images/person-riding-bike-png-transparent.png").toUri().toString());
        logoImage.setX(50);
        logoImage.setY(25);
        logoImage.setFitHeight(200);
        logoImage.setFitWidth(200);
        logoImage.setPreserveRatio(true);

        getChildren().addAll(titleLabel, logoImage);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: gainsboro;");
        setPadding(new Insets(0, 0, 20, 0));
        VBox.setMargin(titleLabel, new Insets(20));
    }
}
