package simulation_processors;

import cells.Cell;
import cells.SegregationCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SegregationProcessor extends BaseProcessor {
	
	/* All empty cells currently in the grid */	
	private List<Cell> emptyCells;

	private Random emptyCellSelector = new Random();
	
	/** Sets the next state of each cell to the appropriate next state as defined by the SegregationCell rules.
	 * 
	 * <p>
	 * This method handles the case when a cell needs to "change" locations. In order to test if this happened, the current state
	 * of a cell must not be empty and it's next state must be empty. When this happens the cells is "moved" to a next state.
	 * 
	 * */
	
	@Override
	public void updateCellStates() {
		initializeEmptyCells();
		for(Cell[] row : currentCells) {
			for(Cell currentCell : row) {
					currentCell.calculateNextState();
					if (((SegregationCell) currentCell).wantsToMove() ) {
						Cell newHostCell = getRandomEmptyCellToMoveInto();
						((SegregationCell) currentCell).moveTo((SegregationCell) newHostCell);
						/* add the newly empty location */
						emptyCells.add(currentCell);
					}
			}
		}
	}
	
	/**
	 * Finds all the empty cells in the grid and populate their locations in the 
	 * emptyCells ArrayList.
	 * 
	 * */
	private void initializeEmptyCells() {
		/* Reinitialize emptyCells */
		emptyCells = new ArrayList<>();
		/* Loop through all cells */
		for(Cell[] row : currentCells) {
			for(Cell cellToCheck : row) {
				if(((SegregationCell)cellToCheck).isEmpty())
					// if the currentState of the cell is empty, add it to emptyCells set
					emptyCells.add(cellToCheck);
			}
		}
	}
	
	/**
	 * Get a random empty cell
	 *
	 * */
	private Cell getRandomEmptyCellToMoveInto() {
		/* Get random location */
		return emptyCells.remove(emptyCellSelector.nextInt(emptyCells.size()));
	}
}
