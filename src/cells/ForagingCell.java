package cells;

import javafx.scene.paint.Color;
import utilities.PropertiesGetter;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Cell used in the Foraging Ants simulation.
 *
 * @see ../docs/SIMULATION_RULES.md
 * @author Ben Schwennesen
 */
public class ForagingCell extends NeighborSubsetCell {

    public enum State {
        POPULATED_MOUND,
        MOUND,
        GROUND,
        TRAIL,
        FOOD,
        ANT
    }

    /* constants */
    private final double MINIMUM_PHEROMONE_THRESHOLD;
    private final double MAXIMUM_PHEROMONE_LEVEL;
    private final Random GOD_OF_REPRODUCTION;
    private final double COLOR_STAY_SAME_MODIFIER = 1.0;

    /* state auxiliary info */
    private double homePheromoneLevel;
    private double foodPheromoneLevel;
    private int timeStepsSinceBirth;
    private boolean hasFoodItem;

    /* auxiliary info to XML keys */
    private final String HOME_PHEROMONE_LEVEL_KEY = "homePheromoneLevel";
    private final String FOOD_PHEROMONE_LEVEL_KEY = "foodPheromoneLevel";
    private final String TIME_ALIVE_KEY = "timeStepsSinceBirth";
    private final String HAS_FOOD_KEY = "hasFoodItem";

    /* slider configurable properties */
    private int antLifespan;
    private double pheromoneDropAmount;
    private double pheromoneRetentionRate;
    private double reproductionProbability;

    public ForagingCell(State initialState) {
        super(initialState);
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));

        GOD_OF_REPRODUCTION = new Random();
        reproductionProbability = PropertiesGetter.getDefaultAntReproductionProbability();

        MINIMUM_PHEROMONE_THRESHOLD = PropertiesGetter.getMinimumPheromoneThreshold();
        MAXIMUM_PHEROMONE_LEVEL = PropertiesGetter.getMaximumPheromoneLevel();
        pheromoneDropAmount = PropertiesGetter.getDefaultPheromoneDropAmount();
        pheromoneRetentionRate = PropertiesGetter.getDefaultPheromoneRetiotionRate();
        homePheromoneLevel = foodPheromoneLevel = 0.0;

        timeStepsSinceBirth = 0;
        antLifespan = PropertiesGetter.getDefaultAntLifespan();
    }

    /**
     * Allow non-default initial auxiliary state info so that a save file can be loaded.
     *
     * @param initialState - state at save time
     * @param homePheromoneLevel - level of home pheromones at save time
     * @param foodPheromoneLevel - level of food pheromones at save time
     * @param timeAlive - number of time steps the cell had survived at save time
     * @param hasFood - whether the cell was an ant holding food at save time
     */
    public ForagingCell(State initialState, double homePheromoneLevel, double foodPheromoneLevel,
                        int timeAlive, boolean hasFood) {
        this(initialState);
        this.homePheromoneLevel = homePheromoneLevel;
        this.foodPheromoneLevel = foodPheromoneLevel;
        this.timeStepsSinceBirth = timeAlive;
        this.hasFoodItem = hasFood;
    }

    @Override
    public void calculateNextState() {
        decayPheromones();
        timeStepsSinceBirth++;
        if (needsNextState()) {
            if (isInState(State.ANT)) {
                antForage();
            } else if (isInState(State.POPULATED_MOUND)) {
                List<AbstractCell> trailNeighbors = getNeighborsInState(State.TRAIL);
                if (trailNeighbors.size() != 0) {
                    System.out.println("BITCH");
                    ForagingCell moveTo = getNeighborWithMaxPheromone(trailNeighbors, true);
                    System.out.println(moveTo);
                    moveTo.setNextState(State.ANT);
                    setNextState(State.MOUND);
                } else {
                    moveAntToRandomEmptyNeighbor(State.POPULATED_MOUND);
                }
            } else {
                stayInSameState();
            }
        }
    }

    private void decayPheromones() {
        if (foodPheromoneLevel > MINIMUM_PHEROMONE_THRESHOLD) {
            foodPheromoneLevel *= pheromoneRetentionRate;
        } else if (foodPheromoneLevel != 0.0) {
            foodPheromoneLevel = 0.0;
        }
        if (homePheromoneLevel > MINIMUM_PHEROMONE_THRESHOLD) {
            homePheromoneLevel *= pheromoneRetentionRate;
        } else if (homePheromoneLevel != 0.0) {
            homePheromoneLevel = 0.0;
        }
    }

    private void antForage() {
        if (isDead()) {
            hasFoodItem = false;
            setNextState(setNextToTrailOrGround());
        } else if (hasFoodItem) {
            dropFoodPheromones();
            seekFoodOrHome(false);
        } else {
            dropHomePheromones();
            seekFoodOrHome(true);
        }
    }

    private boolean isDead() { return timeStepsSinceBirth >= antLifespan; }

    private void dropFoodPheromones() {
        if (foodPheromoneLevel + pheromoneDropAmount < MAXIMUM_PHEROMONE_LEVEL) {
            foodPheromoneLevel += pheromoneDropAmount;
        } else {
            foodPheromoneLevel = MAXIMUM_PHEROMONE_LEVEL;
        }
    }

    private void dropHomePheromones() {
        if (homePheromoneLevel + pheromoneDropAmount < MAXIMUM_PHEROMONE_LEVEL) {
            homePheromoneLevel += pheromoneDropAmount;
        } else {
            homePheromoneLevel = MAXIMUM_PHEROMONE_LEVEL;
        }
    }

    private void seekFoodOrHome(boolean needFood) {
        // check for and move to food or home, depending on whether we need for or not
        if ((needFood && !foundAndMovedToFood()) && (!needFood && !foundAndMovedToHome())) {
            // if no food/home found, move along the pheromone path to move closer to them
            List<AbstractCell> trailNeighbors = getNeighborsInState(State.TRAIL);
            if (trailNeighbors.size() != 0) {
                ForagingCell moveTo = getNeighborWithMaxPheromone(trailNeighbors, needFood);
                moveTo.setNextState(State.ANT);
                moveTo.timeStepsSinceBirth = this.timeStepsSinceBirth;
                setNextState(reproduceOrLeaveEmpty());
            } else {
                moveAntToRandomEmptyNeighbor(State.ANT);
            }
        }
    }

    private boolean foundAndMovedToHome() {
        List<AbstractCell> homeNeighbors = getNeighborsInState(State.MOUND);
        if (homeNeighbors.size() > 0) {
            AbstractCell home = getRandomNeighborFromSubset(homeNeighbors);
            goHome(home);
            return true;
        }
        return false;
    }

    private void goHome(AbstractCell homeCell) {
        // drop the food -> make home color brighter? TBD
        hasFoodItem = false;
        homeCell.setNextState(State.POPULATED_MOUND);
        setNextState(reproduceOrLeaveEmpty());
    }

    private boolean foundAndMovedToFood() {
        List<AbstractCell> foodNeighbors = getNeighborsInState(State.FOOD);
        if (foodNeighbors.size() > 0) {
            AbstractCell food = getRandomNeighborFromSubset(foodNeighbors);
            goGetTheFood(food);
            return true;
        }
        return false;
    }

    private void goGetTheFood(AbstractCell foodCell) {
        // grab the food
        hasFoodItem = true;
        foodCell.setNextState(State.ANT);
        setNextState(reproduceOrLeaveEmpty());
    }

    private ForagingCell getNeighborWithMaxPheromone(List<AbstractCell> trailNeighbors, boolean needFood) {
        double maxPheromoneLevel = -1;
        ForagingCell moveTo = null;
        for (AbstractCell neighbor : trailNeighbors) {
            double pheromoneLevel = (needFood ?
                    ((ForagingCell) neighbor).foodPheromoneLevel :
                    ((ForagingCell) neighbor).homePheromoneLevel);
            System.out.println(pheromoneLevel);
            if (pheromoneLevel > maxPheromoneLevel) {
                moveTo = (ForagingCell) neighbor;
                maxPheromoneLevel = pheromoneLevel;
            }
        }
        return moveTo;
    }

    private void moveAntToRandomEmptyNeighbor(State currentState) {
        List<AbstractCell> emptyNeighbors = getNeighborsInState(State.GROUND);
        if (emptyNeighbors.size() > 0) {
            ForagingCell moveTo = (ForagingCell) getRandomNeighborFromSubset(emptyNeighbors);
            moveTo.timeStepsSinceBirth = this.timeStepsSinceBirth;
            moveTo.setNextState(State.ANT);
            if (currentState == State.POPULATED_MOUND) {
                setNextState(State.MOUND);
            } else {
                setNextState(reproduceOrLeaveEmpty());
            }
        } else {
            // no choice but to stay put at this point if no neighbor is empty
            stayInSameState();
        }
    }

    private State reproduceOrLeaveEmpty() {
        if (GOD_OF_REPRODUCTION.nextDouble() > reproductionProbability) {
            timeStepsSinceBirth = 0;
            return State.ANT;
        } else {
            return setNextToTrailOrGround();
        }
    }

    private State setNextToTrailOrGround() {
        if (homePheromoneLevel > 0.0 || foodPheromoneLevel > 0.0) {
            return State.TRAIL;
        } else {
            return State.GROUND;
        }
    }

    @Override
    public Color getColor() {
        Color baseColor = super.getColor();
        double hueModifier = COLOR_STAY_SAME_MODIFIER - foodPheromoneLevel;
        double brightnessModifier = COLOR_STAY_SAME_MODIFIER - homePheromoneLevel;
        baseColor = baseColor.deriveColor(hueModifier, COLOR_STAY_SAME_MODIFIER,
                brightnessModifier, COLOR_STAY_SAME_MODIFIER);
        return baseColor;
    }

    @Override
    public Map<String, String> toXMLMap(int row, int column) {
        Map<String, String> xmlMappings = super.toXMLMap(row, column);
        xmlMappings.put(HOME_PHEROMONE_LEVEL_KEY, String.valueOf(homePheromoneLevel));
        xmlMappings.put(FOOD_PHEROMONE_LEVEL_KEY, String.valueOf(foodPheromoneLevel));
        xmlMappings.put(TIME_ALIVE_KEY, String.valueOf(timeStepsSinceBirth));
        xmlMappings.put(HAS_FOOD_KEY, String.valueOf(hasFoodItem));
        return xmlMappings;
    }
}