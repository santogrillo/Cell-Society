package cells;

import utilities.PropertiesGetter;

/**
 * Cell used in the Chou-Reggia loop/self-replication simulation.
 *
 * @see <a href=This paper>https://www.researchgate.net/profile/Yun_Peng7/publication/266350459_Simple_Systems_Exhibiting_Self-Directed_Replication/links/550ae8300cf285564096306a/Simple-Systems-Exhibiting-Self-Directed-Replication.pdf?origin=publication_list</a>
 * @see ../docs/SIMULATION_RULES.md
 * @author Ben Schwennesen
 */
public class LoopCell extends AbstractCell {

    // in-line comments indicate the symbol used to represent the states in the linked paper
    public enum State {
        RED, // ^
        BLUE, // 0
        GREEN, // L
        BROWN, // V
        YELLOW, // >
        ORANGE, // <
        PURPLE, // #
        BLACK // .
    }

    private final int ROW;
    private final int COLUMN;

    private LoopCell north;
    private LoopCell south;
    private LoopCell east;
    private LoopCell west;

    public LoopCell(State initialState, int row, int column) {
        super(initialState);
        this.ROW = row;
        this.COLUMN = column;
        initializeColorMap(PropertiesGetter.getColorMap(State.values()));
    }

    @Override
    public void addNeighbors(Cell... adjacentCells){
        for (Cell rawNeighbor : adjacentCells) {
            LoopCell neighbor = (LoopCell) rawNeighbor;
            if (neighbor.ROW == this.ROW) {
                System.out.println(neighbor.COLUMN + " " + this.COLUMN);
                if (neighbor.COLUMN == this.COLUMN + 1) {
                    System.out.println("NORTH");
                    north = neighbor;
                } else if (neighbor.COLUMN == this.COLUMN - 1) {
                    System.out.println("SOUTH");
                    south = neighbor;
                }
            } else if (neighbor.COLUMN == this.COLUMN) {
                System.out.println(neighbor.ROW + " " + this.ROW);
                if (neighbor.ROW == this.ROW + 1) {
                    System.out.println("EAST");
                    east = neighbor;
                } else if (neighbor.ROW == this.ROW - 1) {
                    System.out.println("WEST");
                    west = neighbor;
                }
            }
        }
        System.out.println(north + " " + east + " " + south + " " + west);
    }

    @Override
    public void calculateNextState() {
        if (isInState(State.BLUE)) {
            calculateBlueNextState();
        } else if (isInState(State.BLACK) && north.isInState(State.BLACK)) {
            calculateBlackNextState();
        } else if ((isInState(State.RED) || isInState(State.BROWN))
                    && north.isInState(State.BLACK) && east.isInState(State.BLUE)
                || (isInState(State.ORANGE) && north.isInState(State.BLACK) && east.isInState(State.BLACK)
                    && south.isInState(State.BLACK) && west.isInState(State.PURPLE))) {
            setNextState(State.BLACK);
        } else if (isInState(State.GREEN) || isInState(State.PURPLE)) {
            setNextState(State.BLUE);
        } else if ((isInState(State.YELLOW) && south.isInState(State.BLACK) && west.isInState(State.GREEN))
                || (isInState(State.ORANGE) && north.isInState(State.BLACK)
                    && east.isInState(State.BLACK) && south.isInState(State.GREEN))) {
            setNextState(State.GREEN);
        } else {
            stayInSameState();
        }
    }

    private void calculateBlueNextState() {
        if (((north.isInState(State.BLACK) && east.isInState(State.ORANGE) && south.isInState(State.BLUE))
                && (west.isInState(State.BLACK) || west.isInState(State.RED)))
                || north.isInState(State.RED)) {
            setNextState(State.BROWN);
        } else if ((north.isInState(State.BLACK) && south.isInState(State.BLACK) && west.isInState(State.YELLOW))
                || ((north.isInState(State.BROWN) || north.isInState(State.BLUE))
                    && east.isInState(State.BLUE) && south.isInState(State.BLACK))) {
            setNextState(State.YELLOW);
        } else if (north.isInState(State.BLACK) && south.isInState(State.YELLOW)) {
            setNextState(State.ORANGE);
        } else {
            stayInSameState();
        }
    }

    private void calculateBlackNextState() {
        if ((east.isInState(State.BLACK) && south.isInState(State.BLACK) && west.isInState(State.YELLOW))
                || (east.isInState(State.BLUE) && south.isInState(State.BLUE) && west.isInState(State.BLUE))) {
            setNextState(State.RED);
        } else if (east.isInState(State.BLACK) && south.isInState(State.RED) && west.isInState(State.BLUE)) {
            setNextState(State.ORANGE);
        } else if (east.isInState(State.BLACK) && (south.isInState(State.YELLOW) || south.isInState(State.PURPLE))
                && west.isInState(State.BLACK)) {
            setNextState(State.BLUE);
        } else if (east.isInState(State.ORANGE) && south.isInState(State.ORANGE)) {
            setNextState(State.PURPLE);
        } else {
            stayInSameState();
        }
    }
}
