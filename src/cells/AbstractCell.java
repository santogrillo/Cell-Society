package cells;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds fields and behavior shared by all cells, including state, color, and neighbor members as
 * well as methods operating on them.
 *
 * @author Ben Schwennesen
 */
public abstract class AbstractCell implements Cell {

    private Enum currentState;
    private Enum nextState;

    private final String CURRENT_STATE_KEY = "state";
    private final String COLUMN_KEY = "column";
    private final String ROW_KEY = "row";

    private final Map<Enum, Color> statesToColors;

    private final List<AbstractCell> neighbors;

    /**
     * Declare a new cell with a predetermined initial state.
     *
     * @param initialState - the state the cell should begin in
     */
    public AbstractCell(Enum initialState) {
        currentState = initialState;
        neighbors = new ArrayList<>();
        statesToColors = new HashMap<>();
    }

    /**
     * Initialize a map from simulation states to the colors the front-end should display for them.
     *
     * @param statesToColors
     *              - the map returned by the properties getter for the subclass's state types
     */
    public void initializeColorMap(Map<Enum, Color> statesToColors) {
        this.statesToColors.putAll(statesToColors);
    }

    @Override
    public void addNeighbors(Cell... adjacentCells) {
        for (Cell neighbor : adjacentCells) {
            neighbors.add((AbstractCell) neighbor);
        }
    }

    @Override
    public void clearNeighbors() {
        neighbors.clear();
    }
    
    @Override
    public void transitionState() {
        currentState = nextState;
        nextState = null;
    }

    /**
     * Check if the cell is in a particular state.
     *
     * @param state - the state to check against
     * @return true if the cell is currently in the state, false otherwise
     */
    protected boolean isInState(Enum state) {
        return currentState == state;
    }

    /**
     * Check if the cell doesn't know its next state.
     *
     * @return true if the next state is not yet set, false otherwise
     */
    protected boolean needsNextState() {
        return nextState == null;
    }

    /**
     * Set the next state of the cell (done by subclasses according to the rules of their simulation).
     *
     * @param nextState - the state the cell should transition to at the next time step
     */
    protected void setNextState(Enum nextState) {
        this.nextState = nextState;
    }

    /**
     * Remain in the same state at the next time step.
     */
    protected void stayInSameState() {
        this.nextState = currentState;
    }

    /**
     * Get the current state of the cell.
     *
     * @return the enum representing the current state
     */
    protected Enum getCurrentState() {
        return currentState;
    }

    /**
     * Get a neighbor list. Intended for use by abstract classes between AbstractCell and
     * simulation cells.
     *
     * @return a list of the cell's neighbors
     */
    protected List<AbstractCell> getNeighbors() {
        return neighbors;
    }

    @Override
    public Color getColor() {
        return statesToColors.get(currentState);
    }

    @Override
    public Map<String, String> toXMLMap(int row, int column) {
        Map<String, String> xmlMappings = new HashMap<>();
        xmlMappings.put(CURRENT_STATE_KEY, currentState.toString());
        xmlMappings.put(COLUMN_KEY, String.valueOf(column));
        xmlMappings.put(ROW_KEY, String.valueOf(row));
        return xmlMappings;
    }
}
