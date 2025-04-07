package fitnessapp.gui;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import fitnessapp.Person;
import fitnessapp.account.Account;
import fitnessapp.physical.DaySummary;
import fitnessapp.physical.PhysicalHealth;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StatsScreen extends Screen {

    private TableView<DaySummary> statisticsTable; 

    private Label avgCaloriesBurntLabel;
    private Label avgCaloriesConsumedLabel;
    private Label avgExerciseDurationLabel;

    private Button returnButton;

    private ObjectProperty<Account> account;

    private ExecutorService executor;

    private static final Logger LOGGER = Logger.getLogger(StatsScreen.class.getName());

    public StatsScreen(Consumer<String> switchScreen, ObjectProperty<Account> account) {
        super(switchScreen);

        this.account = account;
        this.executor = Executors.newCachedThreadPool();

        BorderPane mainPane = new BorderPane();

        VBox contentVBox = new VBox();
        contentVBox.setSpacing(10);
        BorderPane.setMargin(contentVBox, new Insets(20));

        VBox dayStatsVBox = new VBox();
        dayStatsVBox.setSpacing(10);
        dayStatsVBox.setAlignment(Pos.CENTER);

        Label dayStatsLabel = new Label("Per Day Stats");
        dayStatsLabel.setFont(Font.font("Lucida Sans Unicode", 20));
        dayStatsLabel.setAlignment(Pos.CENTER);
        dayStatsVBox.getChildren().add(dayStatsLabel);

        statisticsTable = new TableView<>();
        statisticsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addStatisticColumn("Date", DaySummary::getDate);
        addStatisticColumn("Calories Burnt", DaySummary::getCaloriesBurnt);
        addStatisticColumn("Calories Consumed", DaySummary::getCaloriesConsumed);
        addStatisticColumn("Net Calories", DaySummary::getNetCalories);
        addStatisticColumn("Est Net Weight (lb.)", summary -> String.format("%.2f", summary.getEstNetWeightLB()));
        addStatisticColumn("Total Exercise Duration", summary -> formatDuration(summary.getTotalExerciseDuration()));
        addStatisticColumn("Foods Consumed", DaySummary::getNumFoodsConsumed);
        dayStatsVBox.getChildren().add(statisticsTable);

        contentVBox.getChildren().add(dayStatsVBox);

        Label allTimeStatsLabel = new Label("All Time Stats");
        allTimeStatsLabel.setFont(Font.font("Lucida Sans Unicode", 20));
        contentVBox.getChildren().add(allTimeStatsLabel);

        HBox bottomHBox = new HBox();

        FormGridPane gridPane = new FormGridPane();
        gridPane.getColumnConstraints().get(0).setPercentWidth(50);
        gridPane.getColumnConstraints().get(1).setPercentWidth(50);
        gridPane.setMaxWidth(600);

        avgCaloriesBurntLabel = new Label();
        gridPane.addRow("Average Calories Burnt:", avgCaloriesBurntLabel);

        avgCaloriesConsumedLabel = new Label();
        gridPane.addRow("Average Calories Consumed:", avgCaloriesConsumedLabel);
    
        avgExerciseDurationLabel = new Label();
        gridPane.addRow("Average Exercise Duration:", avgExerciseDurationLabel);
        bottomHBox.getChildren().add(gridPane);

        returnButton = new Button("Return");
        returnButton.setAlignment(Pos.BOTTOM_RIGHT);
        returnButton.setOnAction(e -> switchScreen("mainHealth"));

        // Sigh, push the button to the bottom right
        HBox buttonHBox = new HBox(returnButton);
        buttonHBox.setAlignment(Pos.BOTTOM_RIGHT);
        HBox.setHgrow(buttonHBox, Priority.ALWAYS);
        bottomHBox.getChildren().add(buttonHBox);

        contentVBox.getChildren().add(bottomHBox);

        //Setting the pane and mainScene
        mainPane.setTop(new HeaderVBox());
        mainPane.setCenter(contentVBox);
        getScene().setRoot(mainPane);
    }

    private void addStatisticColumn(String columnTitle, Function<DaySummary, Object> propertyGetter) {
        TableColumn<DaySummary, Object> column = new TableColumn<>(columnTitle);
        column.setCellValueFactory(cellDataFeatures -> {
            return new ReadOnlyObjectWrapper<Object>(propertyGetter.apply(cellDataFeatures.getValue()));
        });
        statisticsTable.getColumns().add(column);
    }

    @Override
    public void onEnterScreen() {
        statisticsTable.getItems().clear();
        CountDownLatch latch = new CountDownLatch(4);
        executor.execute(() -> {
            try {
                latch.await();
                LOGGER.log(Level.INFO, "Finished all tasks");
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Was interrupted while waiting for tasks to complete.", e);
            }
        });

        fillAllTimeStats(latch);
        account.get().getPerson().getPhysicalHealth().summarizeByDay(executor)
                                                     .thenAccept(statisticsTable.getItems()::addAll)
                                                     .thenRun(latch::countDown);
    }

    private void fillAllTimeStats(CountDownLatch latch) {
        Person person = account.get().getPerson();
        PhysicalHealth physicalHealth = person.getPhysicalHealth();

        avgCaloriesBurntLabel.setText("N/A");
        avgCaloriesConsumedLabel.setText("N/A");
        avgExerciseDurationLabel.setText("N/A");

        executor.execute(() -> {
            int daysExercised = physicalHealth.getExerciseDays().size();
            if (daysExercised != 0) {
                int totalCaloriesBurnt = physicalHealth.getTotalCaloriesBurnt();
                double avgCaloriesBurnt = ((double) (totalCaloriesBurnt)) / daysExercised;
                Platform.runLater(() -> avgCaloriesBurntLabel.setText(String.format("%.2f", avgCaloriesBurnt)));
            }
            latch.countDown();
        });

        executor.execute(() -> {
            int daysAte = physicalHealth.getFoodDays().size();
            if (daysAte != 0) {
                int totalCaloriesConsumed = physicalHealth.getTotalCaloriesAte();
                double avgCaloriesConsumed = ((double) (totalCaloriesConsumed)) / daysAte;
                Platform.runLater(() -> avgCaloriesConsumedLabel.setText(String.format("%.2f", avgCaloriesConsumed)));
            }
            latch.countDown();
        });

        executor.execute(() -> {
            int daysExercised = physicalHealth.getExerciseDays().size();
            if (daysExercised != 0) {
                Duration avgDuration = physicalHealth.getTotalExerciseDuration().dividedBy(daysExercised);
                Platform.runLater(() -> avgExerciseDurationLabel.setText(formatDuration(avgDuration)));
            }
            latch.countDown();
        });
    }

    private String formatDuration(Duration duration) {
        return String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    }

    @Override
    public String getTitle() {
        return "Statistics";
    }
}