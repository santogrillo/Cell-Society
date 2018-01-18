package cells;

import utilities.PropertiesGetter;

/**
 * Cell used in Schelling's Model of Segregation, as described at
 * http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/. State transition rules for
 * the cell:

 *
 * @author Ben Schwennesen
 */
public class SegregationCell extends NeighborCountingCell {

    public enum State {
        BLUE,
        RED,
        EMPTY
    }

    private double satisfactionThreshold;
    private boolean wantsToMove;

    public SegregationCell(State initialState) {
        super(initialState);
        wantsToMove = false;
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));
        setSatisfactionThreshold(PropertiesGetter.getDefaultSatisfactionThreshold());
    }

    @Override
    public void calculateNextState() {
        // assume initially that the cell is satisfied and doesn't want to move
        wantsToMove = false;
        // make sure the cell hasn't been updated already by another cell moving into it
        if (needsNextState()) {
            if (isInState(State.EMPTY)) {
                setNextState(State.EMPTY);
            } else {
                int numberOfSameStateNeighbors = countNeighborsInState(getCurrentState());
                int numberOfNeighbors = getNeighbors().size();
                double neighborSimilarityRatio = ((double) numberOfSameStateNeighbors) / numberOfNeighbors;
                if (neighborSimilarityRatio < satisfactionThreshold) {
                    setNextState(State.EMPTY);
                    wantsToMove = true;
                } else {
                    stayInSameState();
                }
            }
        }
    }

    public void setSatisfactionThreshold(double satisfactionThreshold) {
        if (satisfactionThreshold >= 0.0 && satisfactionThreshold <= 1.0) {
            this.satisfactionThreshold = satisfactionThreshold;
        } else {
            this.satisfactionThreshold = (satisfactionThreshold < 0.0 ? 0.0 : 1.0);
        }
    }

    public void moveTo(SegregationCell newHostCell) {
        newHostCell.setNextState(getCurrentState());
    }

    public boolean isEmpty() {
        return isInState(State.EMPTY);
    }

    public boolean wantsToMove() { return wantsToMove; }

}