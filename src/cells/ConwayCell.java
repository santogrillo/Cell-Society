package cells;

import utilities.PropertiesGetter;

/**
 * Cell used in Conway's Game of Life simulation.
 *
 * @see ../doc/SIMULATION_RULES.md
 * @author Ben Schwennesen
 */
public class ConwayCell extends NeighborCountingCell {

    public enum State {
        DEAD,
        LIVE
    }

    private final int UNDERPOPULATION_THRESHOLD = 2;
    private final int OVERPOPULATION_THRESHOLD = 3;
    private final int REPRODUCTION_THRESHOLD = 3;

    public ConwayCell(State initialState) {
        super(initialState);
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));
    }

    @Override
    public void calculateNextState() {
        int liveNeighborCount = countNeighborsInState(State.LIVE);
        if (isInState(State.LIVE) && (liveNeighborCount < UNDERPOPULATION_THRESHOLD
                || liveNeighborCount > OVERPOPULATION_THRESHOLD)) {
            setNextState(State.DEAD);
        } else if (isInState(State.DEAD) && liveNeighborCount == REPRODUCTION_THRESHOLD) {
            setNextState(State.LIVE);
        } else {
            stayInSameState();
        }
    }
}