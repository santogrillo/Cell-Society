package cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provides functionality of getting a subset of the cell's neighbors that are in a particular state.
 *
 * @author Ben Schwennesen
 */
public abstract class NeighborSubsetCell extends AbstractCell {

    /** Random number generator used to pickk a random neighbor of some type to move to */
    private final Random NEIGHBOR_CHOOSER = new Random();

    public NeighborSubsetCell(Enum initialState) {
        super(initialState);
    }

    /**
     * Get a list of neighbors in a particular state.
     *
     * @param state - the simulation state to get a matching neighbor list of
     * @return a list of neighbors currently in the state
     */
    public List<AbstractCell> getNeighborsInState(Enum state) {
        List<AbstractCell> neighborsInState = new ArrayList<>();
        for (AbstractCell neighbor : getNeighbors()) {
            if (neighbor.getCurrentState() == state) {
                neighborsInState.add(neighbor);
            }
        }
        return neighborsInState;
    }

    /** Select a random neighbor from a subset (as a list) of the cell's neighbors */
    protected AbstractCell getRandomNeighborFromSubset(List<AbstractCell> neighbors) {
        return neighbors.get(NEIGHBOR_CHOOSER.nextInt(neighbors.size()));
    }
}
