package cells;

import utilities.PropertiesGetter;

import java.util.List;
import java.util.Map;

/**
 * Cell used in the WaTor World (also known as predator-prey) simulation.
 *
 * @see ../doc/SIMULATION_RULES.md
 * @author Ben Schwennesen
 */
public class WaTorCell extends NeighborSubsetCell {

    public enum State {
        FISH,
        SHARK,
        WATER
    }

    /*
     * Energy loss per chronon and energy gain per fish are not final since we might want to add
     * sliders to adjust them
     */
    private int energyLossPerChronon;
    private int energyGainPerFish;
    private int chrononsNeededToReproduce;

    private int numberOfChrononsSurvivedSinceLastReproduction;
    private int energyLevel;

    private final String REPRODUCTION_KEY = "numberOfChrononsSurvivedSinceLastReproduction";
    private final String ENERGY_LEVEL_KEY = "energyLevel";

    public WaTorCell(State initialState) {
        super(initialState);
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));
        numberOfChrononsSurvivedSinceLastReproduction = 0;
        chrononsNeededToReproduce = PropertiesGetter.getDefaultChrononsNeededToReproduce();
        energyLossPerChronon = PropertiesGetter.getDefaultEnergyLossPerChronon();
        energyGainPerFish = PropertiesGetter.getDefaultEnergyGainPerFish();
        energyLevel = PropertiesGetter.getSharkDefaultInitialEnergyLevel();
    }

    /**
     * Allow non-default initial auxiliary state info so that a save file can be loaded.
     *
     * @param initialState - the state of the cell at save time
     * @param chrononsSurvived - the number of time steps the cell survived at save time
     * @param energyLevel - the amount of energy the cell has left at save time
     */
    public WaTorCell(State initialState, int chrononsSurvived, int energyLevel) {
        this(initialState);
        this.numberOfChrononsSurvivedSinceLastReproduction = chrononsSurvived;
        this.energyLevel = energyLevel;
    }

    @Override
    public void calculateNextState() {
        // make sure the cell hasn't been updated already by another cell moving into it
        if (needsNextState()) {
            numberOfChrononsSurvivedSinceLastReproduction++;
            if (isInState(State.SHARK)) {
                devourFishOrMoveToEmptyNeighborIfPossible();
            } else if (isInState(State.FISH)) {
                moveToEmptyNeighborIfPossible();
            } else {
                setNextState(State.WATER);
            }
        }
    }

    /**
     * Devour an adjacent fish if there exists one, otherwise move to an empty cell or stay put if
     * surrounded by other sharks.
     */
    private void devourFishOrMoveToEmptyNeighborIfPossible() {
        List<AbstractCell> fishNeighbors = getNeighborsInState(State.FISH);
        if (this.energyLevel <= 0) {
            setNextState(State.WATER);
            numberOfChrononsSurvivedSinceLastReproduction = 0;
            energyLevel = 0;
        } else if (fishNeighbors.size() > 0) {
            gainEnergy();
            WaTorCell fishToDevour = (WaTorCell) getRandomNeighborFromSubset(fishNeighbors);
            devourFish(fishToDevour);
        } else {
            loseEnergy();
            // if there are no fish around a shark it behaves like a fish
            moveToEmptyNeighborIfPossible();
        }
    }

    /**
     * Devour a fish once an adjacent one has been found
     *
     * @param fish - the cell containing the fish the shark will move onto and devour
     */
    private void devourFish(WaTorCell fish) {
        // transfer this cell's status info over to the cell occupied by the fish to be devoured
        fish.numberOfChrononsSurvivedSinceLastReproduction =
                this.numberOfChrononsSurvivedSinceLastReproduction;
        fish.energyLevel = this.energyLevel;
        fish.setNextState(State.SHARK);
        reproduceIfPossible();
    }

    /**
     * Move to a random empty neighbor if there are any, otherwise stay in the same state
     */
    private void moveToEmptyNeighborIfPossible() {
        List<AbstractCell> emptyNeighbors = getNeighborsInState(State.WATER);
        if (emptyNeighbors.size() > 0) {
            WaTorCell emptyNeighbor = (WaTorCell) getRandomNeighborFromSubset(emptyNeighbors);
            // transfer this cell's status info over to its new location
            emptyNeighbor.numberOfChrononsSurvivedSinceLastReproduction =
                    this.numberOfChrononsSurvivedSinceLastReproduction;
            emptyNeighbor.energyLevel = this.energyLevel;
            emptyNeighbor.setNextState(this.getCurrentState());
            reproduceIfPossible();
        } else {
            stayInSameState();
        }
    }

    /**
     * Reproduce once enough chronons have passed.
     */
    private void reproduceIfPossible() {
        if (numberOfChrononsSurvivedSinceLastReproduction < chrononsNeededToReproduce) {
            energyLevel = 0;
            numberOfChrononsSurvivedSinceLastReproduction = 0;
            setNextState(State.WATER);
        } else {
            energyLevel = PropertiesGetter.getSharkDefaultInitialEnergyLevel();
            numberOfChrononsSurvivedSinceLastReproduction = 0;
            stayInSameState();
        }
    }

    /** Lose a certain amount of energy each chronon a shark doesn't devour a fish */
    private void loseEnergy() { energyLevel -= energyLossPerChronon; }

    /** Gain a certain amount of energy each time a shark devours a fish */
    private void gainEnergy() { energyLevel += energyGainPerFish; }

    @Override
    public Map<String, String> toXMLMap(int row, int column) {
        Map<String, String> xmlMappings = super.toXMLMap(row, column);
        xmlMappings.put(REPRODUCTION_KEY, String.valueOf(numberOfChrononsSurvivedSinceLastReproduction));
        xmlMappings.put(ENERGY_LEVEL_KEY, String.valueOf(energyLevel));
        return xmlMappings;
    }
}