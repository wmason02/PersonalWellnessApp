package fitnessapp.gui;

import java.util.function.Consumer;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * A screen with a title and scene.
 */
public abstract class Screen {
    private Scene scene;
    private Consumer<String> switchScreen;

    public Screen(Consumer<String> switchScreen) {
        this.scene = new Scene(new Pane());
        this.switchScreen = switchScreen;
    }

    /**
     * Switch the current screen
     * @param newScreen the name of the new screen
     */
    protected void switchScreen(String newScreen) {
        switchScreen.accept(newScreen);
    }

    /**
     * Called when the screen is entered
     */
    public void onEnterScreen() {}

    /**
     * @return the title of the screen
     */
    public abstract String getTitle();

    /**
     * @return this screen's scene
     */
    public Scene getScene() {
        return scene;
    }
}
