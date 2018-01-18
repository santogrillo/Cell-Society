package cells;

import javafx.scene.paint.Color;

import java.util.Map;

/**
 * Cell object implemented for each simulation implemented.
 *
 * Each cell should calculate and transition to the next state the cell should be in.
 *
 * The next state the cell should be in is determined according to the rules of each individual
 * simulation.
 *
 * @author Ben Schwennesen
 */
public interface Cell {

    /**
     * Calculate the next state of this cell, as determined by the rules for each type of
     * simulation.
     *
     * The method will analyze the states of the cell's neighboring cells, stored in
     * the field 'neighbors', and will determine the state the cell should transition to when
     * transitionState() is called.
     */
    void calculateNextState();

    /**
     * Update the state of this cell after 'nextState' has been calculated for each cell.
     *
     * This must be done separately from calculateNextState(), since we need to calculate the next
     * state each cell will be in before we transition them, otherwise the updated state for some
     * cells would be used in the calculation of other.
     */
    void transitionState();

    /**
     * Stores the cell's neighbors so that it can perform the calculation of its next state.
     *
     * @param adjacentCells - some number of cells adjacent to this cell in the on-screen grid
     */
    void addNeighbors(Cell... adjacentCells);


    /**
     * Clear the cell's neighbors so that the neighborhood type can be changed on the fly.
     */
    void clearNeighbors();

    /**
     * Determine the color the cell should be displayed as based on its current state.
     *
     * @return the color corresponding to the state the cell is currently in
     */
    Color getColor();

    /**
     * Create a representation of the cell's status in XML format. The status includes any
     * information needed to rebuild the cell, so that a simulation's state may be saved.
     *
     * @return a string in XML format representing the cell's state information
     */
    Map<String, String> toXMLMap(int row, int column);
}