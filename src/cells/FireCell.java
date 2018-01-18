package cells;

import utilities.PropertiesGetter;

import java.util.Random;

/**
 * Cell used in the spreading-fire simulation.
 *
 * @see ../doc/SIMULATION_RULES.md
 * @author Ben Schwennesen
 */
public class FireCell extends NeighborCountingCell  {

    public enum State {
        BURNING,
        TREE,
        EMPTY
    }

    private double catchFireThreshold;
    private Random catchFireDecisionMaker;

    //private final String XML_CATCH_RATE_START_TAG = "<catch-fire-rate>";
    //private final String XML_CATCH_RATE_END_TAG = "</catch-fire-rate>";


    public FireCell(State initialState) {
        super(initialState);
        catchFireDecisionMaker = new Random();
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));
        setCatchFireProbability(PropertiesGetter.getDefaultCatchFireProbability());
    }

    @Override
    public void calculateNextState() {
        if (isInState(State.TREE)) {
            // assume at first we won't change states
            stayInSameState();
            if (countNeighborsInState(State.BURNING) > 0) {
                setNextState(catchFireOrNot());
            }
        } else if (isInState(State.BURNING)) {
            setNextState(State.EMPTY);
        } else {
            // current state is EMPTY
            stayInSameState();
        }
    }

    private State catchFireOrNot() {
        if (catchFireDecisionMaker.nextDouble() < catchFireThreshold) {
            return State.BURNING;
        } else {
            return State.TREE;
        }
    }

    public void setCatchFireProbability(double catchFireThreshold) {
        if (catchFireThreshold >= 0.0 && catchFireThreshold <= 1.0) {
            this.catchFireThreshold = catchFireThreshold;
        } else {
            this.catchFireThreshold = (catchFireThreshold < 0.0 ? 0.0 : 1.0);
        }
    }
}