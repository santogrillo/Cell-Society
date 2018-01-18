package cells;

import utilities.PropertiesGetter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Cell used in Thompson's Day and Night simulation.
 *
 * @see <a href=Wikipedia for Day and Night>https://en.wikipedia.org/wiki/Day_and_Night_(cellular_automaton)</a>
 * @see ../doc/SIMULATION_RULES.md
 * @author Ben Schwennesen
 */
public class DayNightCell extends NeighborCountingCell {

    public enum State {
        DAY,
        NIGHT
    }

    Set<Integer> REANIMATION_NEIGHBOR_REQUIREMENTS = Stream.of(3, 6, 7,  8).collect(Collectors.toSet());
    Set<Integer> SURVIVAL_NEIGHBOR_REQUIREMENTS = Stream.of(3, 4, 6, 7,  8).collect(Collectors.toSet());

    public DayNightCell(State initialState) {
        super(initialState);
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));
    }

    @Override
    public void calculateNextState() {
        int liveNeighborCount = countNeighborsInState(State.DAY);
        if ((isInState(State.DAY) && SURVIVAL_NEIGHBOR_REQUIREMENTS.contains(liveNeighborCount))
                || (isInState(State.NIGHT) && REANIMATION_NEIGHBOR_REQUIREMENTS.contains(liveNeighborCount))) {
            setNextState(State.DAY);
        } else {
            setNextState(State.NIGHT);
        }
    }
}