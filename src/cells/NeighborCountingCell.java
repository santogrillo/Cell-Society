package cells;

/**
 * Provides functionality of counting how many of the cell's neighbors are in a particular state.
 *
 * @author Ben Schwennesen
 */
public abstract class NeighborCountingCell extends AbstractCell {

    public NeighborCountingCell(Enum initialState) {
        super(initialState);
    }

    /**
     * Count the number of neighbors in a particular state.
     *
     * @param state - the simulation state to get a count of
     * @return the count of neighbors in state
     */
    public int countNeighborsInState(Enum state) {
        int stateCount = 0;
        for (AbstractCell neighbor : getNeighbors()) {
            if (neighbor.getCurrentState() == state) {
                stateCount++;
            }
        }
        return stateCount;
    }
}
