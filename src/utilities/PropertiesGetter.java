package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.scene.paint.Color;

/**
 * Static utility class for retrieving the simulations' properties. Extends the functionality of
 * the base java class java.util.Properties. All methods are static and fields are intialized in
 * a static block.
 *
 * @author Ben Schwennesen
 */
public final class PropertiesGetter {

    private static final String PROPERTIES_FILE = "simulations.properties";
    private static final Properties PROPERTIES;

    private static final Color FALLBACK_COLOR = Color.BLACK;

    /* keys used to access the configuration values in the .properties file */
    /* grid configuration value keys */
    private static final String GRID_DIMENSIONS_KEY = "grid-dimensions";
    private static final String CELL_SIDE_LENGTH_KEY = "cell-side-length";
    /* segregation configuration keys */
    private static final String DEFAULT_SATISFACTION_THRESHOLD_KEY =
            "default-satisfaction-threshold";
    /* spreading-fire configuration keys */
    private static final String DEFAULT_CATCH_FIRE_PROBABILITY_KEY =
            "default-catch-fire-probability";
    /* WaTor world configuration value keys */
    private static final String DEFAULT_SHARK_INITIAL_ENERGY_LEVEL_KEY =
            "default-shark-initial-energy-level";
    private static final String DEFAULT_ENERGY_LOSS_PER_CHRONON_KEY =
            "default-energy-loss-per-chronon";
    private static final String DEFAULT_ENERGY_GAIN_PER_FISH_KEY =
            "default-energy-gain-per-fish";
    private static final String DEFAULT_CHRONONS_NEEDED_TO_REPRODUCE =
            "default-chronons-needed-to-reproduce";

    /* Bacteria/Rock-paper-scissors configuration value keys*/
    private static final String MINIMUM_COLOR_GRADIENT_KEY = "minimum-color-gradient-level";
    private static final String MAXIMUM_COLOR_GRADIENT_KEY = "maximum-color-gradient-level";

    /* Foraging ants configuration value keys */
    private static final String DEFAULT_PHEROMONE_RETENTION_KEY = "default-pheromone-retention-rate";
    private static final String DEFAULT_PHEROMONE_DROP_AMOUNT_KEY = "default-pheromone-drop-amount";
    private static final String DEFAULT_ANT_REPRODUCTION_PROBABILITY_KEY
            = "default-ant-reproduction-probability";
    private static final String DEFAULT_ANT_LIFESPAN_KEY = "default-ant-lifespan";
    private static final String MINIMUM_PHEROMONE_THRESHOLD_KEY = "minimum-pheromone-threshold";
    private static final String MAXIMUM_PHEROMONE_LEVEL_KEY = "maximum-pheromone-level";

    /* miscellaneous configuration value keys */
    private static final String DEFAULT_ANIMATION_DELAY = "default-animation-delay";

    /**
     * Blank, private constructor to ensure no other class tries to create an instance of this
     * utility class.
     */
    private PropertiesGetter() {
        // do nothing
    }

    /** static block to initialize the static java.util.Properties member */
    static {
        PROPERTIES = new Properties();
        InputStream properties = PropertiesGetter.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE);
        try {
            PROPERTIES.load(properties);
        } catch (IOException failure) {
            /* do nothing: if file fails to load, all methods are prepared to return
             * default/fallback value when getProperty() returns null */
        }
    }

    /**
     * Get a property that is know to be an integer.
     *
     * @param key - the key used to index the desired configuration value
     * @return value - the integer configuration value we want to get
     */
    private static int getIntegerProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        // if the key is not found, Properties will return null and we should return a default value
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public static int getSharkDefaultInitialEnergyLevel() {
        return getIntegerProperty(DEFAULT_SHARK_INITIAL_ENERGY_LEVEL_KEY);
    }

    public static int getDefaultEnergyLossPerChronon() {
        return getIntegerProperty(DEFAULT_ENERGY_LOSS_PER_CHRONON_KEY);
    }

    public static int getDefaultEnergyGainPerFish() {
        return getIntegerProperty(DEFAULT_ENERGY_GAIN_PER_FISH_KEY);
    }

    public static int getDefaultChrononsNeededToReproduce() {
        return getIntegerProperty(DEFAULT_CHRONONS_NEEDED_TO_REPRODUCE);
    }

    public static int getMinimumColorGradientLevel() {
        return getIntegerProperty(MINIMUM_COLOR_GRADIENT_KEY);
    }

    public static int getMaximumColorGradientLevel() {
        return getIntegerProperty(MAXIMUM_COLOR_GRADIENT_KEY);
    }

    public static int getDefaultAntLifespan() {
        return getIntegerProperty(DEFAULT_ANT_LIFESPAN_KEY);
    }


    /**
     * Get a property that is know to be a double.
     *
     * @param key - the key used to index the desired configuration value
     * @return value - the double configuration value we want to get
     */
    private static Double getDoubleProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        // if the key is not found, Properties will return null and we should return a default value
        if (value == null) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }

    public static double getDefaultCatchFireProbability() {
        return getDoubleProperty(DEFAULT_CATCH_FIRE_PROBABILITY_KEY);
    }

    public static double getDefaultSatisfactionThreshold() {
        return getDoubleProperty(DEFAULT_SATISFACTION_THRESHOLD_KEY);
    }

    public static double getDefaultPheromoneRetiotionRate() {
        return getDoubleProperty(DEFAULT_PHEROMONE_RETENTION_KEY);
    }

    public static double getDefaultPheromoneDropAmount() {
        return getDoubleProperty(DEFAULT_PHEROMONE_DROP_AMOUNT_KEY);
    }

    public static double getDefaultAntReproductionProbability() {
        return getDoubleProperty(DEFAULT_ANT_REPRODUCTION_PROBABILITY_KEY);
    }

    public static double getMinimumPheromoneThreshold() {
        return getDoubleProperty(MINIMUM_PHEROMONE_THRESHOLD_KEY);
    }

    public static double getMaximumPheromoneLevel() {
        return getDoubleProperty(MAXIMUM_PHEROMONE_LEVEL_KEY);
    }

    public static double getDefaultAnimationDelay() {
        return getDoubleProperty(DEFAULT_ANIMATION_DELAY);
    }

    public static Map<Enum, Color> getColorMap(Enum[] cellStates) {
        Map<Enum, Color> stateToColors = new HashMap<>();
        for (Enum state : cellStates) {
            String colorValue = PROPERTIES.getProperty(state.name());
            stateToColors.put(state, colorValue == null ? FALLBACK_COLOR : Color.valueOf(colorValue));
        }
        return stateToColors;
    }
}