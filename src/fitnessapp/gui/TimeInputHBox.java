package fitnessapp.gui;

import java.time.Duration;
import java.time.LocalTime;
import java.util.stream.IntStream;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TimeInputHBox extends HBox {
    private ChoiceBox<Integer> hrChoiceBox;
    private ChoiceBox<Integer> minChoiceBox;

    public TimeInputHBox(boolean absolute) {
        setAlignment(Pos.CENTER);
        setSpacing(10);
        
        hrChoiceBox = new ChoiceBox<>();
        IntStream.range(0, 24).forEach(hrChoiceBox.getItems()::add);

        minChoiceBox = new ChoiceBox<>();
        IntStream.range(0, 60).forEach(minChoiceBox.getItems()::add);

        if (absolute) {
            LocalTime now = LocalTime.now();
            hrChoiceBox.setValue(now.getHour());
            minChoiceBox.setValue(now.getMinute());

            Label seperatorLabel = new Label(":");
            getChildren().addAll(hrChoiceBox, seperatorLabel, minChoiceBox);
        } else {
            hrChoiceBox.setValue(0);
            minChoiceBox.setValue(0);

            Label hrLabel = new Label("hr(s)");
            Label minLabel = new Label("min(s)");
            getChildren().addAll(hrChoiceBox, hrLabel, minChoiceBox, minLabel);
        }
    }

    public Duration toDuration() {
        return Duration.ofMinutes(hrChoiceBox.getValue() * 60 + minChoiceBox.getValue());
    }

    public LocalTime toTime() {
        return LocalTime.of(hrChoiceBox.getValue(), minChoiceBox.getValue());
    }
}
