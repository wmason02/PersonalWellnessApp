package fitnessapp.gui;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter.Change;

/**
 * Various Filters for TextFormatters
 */
public class TextFormatterFilters {
    public static final UnaryOperator<Change> ALPHA_NUMERIC_FILTER = createRegexFilter("(\\d|\\w)*");
    public static final UnaryOperator<Change> DOUBLE_FILTER = createRegexFilter("([0-9]*)?(\\.?[0-9]*)?");
    public static final UnaryOperator<Change> INT_FILTER = createRegexFilter("([0-9]*)?");

    /**
     * Create a filter that only allows text that match a regular expression
     * @param regex the regular expression
     * @return the filter
     */
    private static UnaryOperator<Change> createRegexFilter(String regex) {
        return change -> (change.getControlNewText().matches(regex)) ? change : null;
    }
}
