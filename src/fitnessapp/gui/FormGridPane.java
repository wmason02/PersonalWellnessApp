package fitnessapp.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * A grid pane customized to best fit "forms" in which labels are placed in the 
 * left column and various controls are placed in the right column.
 */
public class FormGridPane extends GridPane {
    private int currentRow = 0;

    /**
     * Create an empty FormGridPane
     */
    public FormGridPane() {
        setMaxWidth(300);
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        // Make sure the right column is aligned to the right
        ColumnConstraints leftColumnConstraint = new ColumnConstraints();
        leftColumnConstraint.setPercentWidth(30);
        ColumnConstraints rightColumnConstraint = new ColumnConstraints();
        rightColumnConstraint.setPercentWidth(70);
        rightColumnConstraint.setHgrow(Priority.ALWAYS);
        rightColumnConstraint.setHalignment(HPos.RIGHT);

        getColumnConstraints().addAll(leftColumnConstraint, rightColumnConstraint);
    }

    /**
     * Add a row with a label in the left column and a control in the right column
     * 
     * @param labelText the text of the label
     * @param node the control to add
     */
    public void addRow(String labelText, Node node) {
        Label label = new Label(labelText);
        if (node instanceof Region) {
            // Make regions in the right column fill the entire column
            ((Region) node).setMaxWidth(Double.MAX_VALUE);
        }

        add(label, 0, currentRow);
        add(node, 1, currentRow);
        currentRow++;
    }

    /**
     * Add an empty row
     */
    public void skipRow() {
        currentRow++;
    }

    /**
     * @return the row in which new controls are added
     */
    public int getCurrentRow() {
        return currentRow;
    }
}
