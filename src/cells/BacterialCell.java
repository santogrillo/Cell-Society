package cells;

import javafx.scene.paint.Color;
import utilities.PropertiesGetter;

import java.util.HashMap;
import java.util.Map;

/**
 * Cell used in the Bacteria/Rock-paper-scissors simulation.
 *
 * @see ../docs/SIMULATION_RULES.md
 * @author Ben Schwennesen
 */
public class BacterialCell extends NeighborSubsetCell {

    public enum State {
        EMPTY,
        ROCK,
        PAPER,
        SCISSOR
    }

    /* constants */
    private final Map<State, State> DOMINATION_MAP;
    private final int MAXIMUM_GRADIENT_LEVEL;
    private final int MINIMUM_GRADIENT_LEVEL;
    private final double COLOR_STAY_SAME_MODIFIER = 1.0;
    private final double COLOR_BASE_BRIGHTNESS_MODIFIER = 0.50;

    /* state auxiliary info and to XML keys */
    private int colorGradient;
    private final String COLOR_GRADIENT_KEY = "colorGradient";

    public BacterialCell(State initialState) {
        super(initialState);
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));
        colorGradient = 0;
        DOMINATION_MAP = new HashMap<>();
        MAXIMUM_GRADIENT_LEVEL = PropertiesGetter.getMaximumColorGradientLevel();
        MINIMUM_GRADIENT_LEVEL = PropertiesGetter.getMinimumColorGradientLevel();
        populateDominationMap();
    }

    /**
     * Allow non-default initial auxiliary state info so that a save file can be loaded.
     *
     * @param initialState - state at save time
     * @param colorGradient - color gradient value at save time
     */
    public BacterialCell(State initialState, int colorGradient) {
        this(initialState);
        this.colorGradient = colorGradient;
    }

    private void populateDominationMap() {
        DOMINATION_MAP.put(State.ROCK, State.SCISSOR);
        DOMINATION_MAP.put(State.SCISSOR, State.PAPER);
        DOMINATION_MAP.put(State.PAPER, State.ROCK);
    }

    @Override
    public void calculateNextState() {
        BacterialCell randomNeighbor = (BacterialCell) getRandomNeighborFromSubset(getNeighbors());
        if (isInState(State.EMPTY)) {
            if (randomNeighbor.isInState(State.EMPTY) || randomNeighbor.colorGradient == MAXIMUM_GRADIENT_LEVEL) {
                stayInSameState();
            } else {
                setNextState(randomNeighbor.getCurrentState());
                colorGradient = randomNeighbor.colorGradient + 1;
            }
        } else {
            State edibleState = DOMINATION_MAP.get(getCurrentState());
            if (randomNeighbor.isInState(edibleState)) {
                upgrade();
                randomNeighbor.downgrade();
            }
            stayInSameState();
        }
    }

    private void upgrade() {
        if (colorGradient > MINIMUM_GRADIENT_LEVEL) {
            colorGradient--;
        }
    }

    private void downgrade() {
        if (colorGradient < MAXIMUM_GRADIENT_LEVEL) {
            colorGradient++;
            stayInSameState();
        } else if (colorGradient == MAXIMUM_GRADIENT_LEVEL) {
            // die
            setNextState(State.EMPTY);
        }
    }

    @Override
    public Color getColor() {
        Color baseColor = super.getColor();
        if (colorGradient > 0) {
            double brightnessModifier = COLOR_STAY_SAME_MODIFIER
                    - (COLOR_BASE_BRIGHTNESS_MODIFIER / (double) colorGradient);
            baseColor = baseColor.deriveColor(COLOR_STAY_SAME_MODIFIER, COLOR_STAY_SAME_MODIFIER,
                    brightnessModifier, COLOR_STAY_SAME_MODIFIER);
        }
        return baseColor;
    }

    @Override
    public Map<String, String> toXMLMap(int row, int column) {
        Map<String, String> xmlMappings = super.toXMLMap(row, column);
        xmlMappings.put(COLOR_GRADIENT_KEY, String.valueOf(colorGradient));
        return xmlMappings;
    }
}