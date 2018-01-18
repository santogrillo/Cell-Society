package simulation_processors;
import cells.Cell;
import neighbor_setters.NeighborSetter;
import neighbor_setters.NeighborSetter.*;
public class BaseProcessor {
	
	/* Object to set cell neighbors */
	public NeighborSetter neighborSetter;
	
	/** Array of the current state of each cell */
	protected Cell[][] currentCells;
	
	/** 
	 * Default constructor is empty
	 * */
	public BaseProcessor() {
		
	}
	
	/**
	 * Calls the method necessary to update the cell to its next state
	 * 
	 * @param cells 2D array of cells representing the previous state of the cells
	 * @return the updated state of each cell
	 * */
	public Cell[][] processCells(Cell[][] cells) {
		currentCells = cells;
		updateCellStates();
		transitionCellStates();
		return currentCells;
	}
	
	/** 
	 * Sets the neighborSetter for this Processor
	 * */
	public void setNeighborSetter(NeighborSetter set) {
		neighborSetter =set;
	}
	
	/**
	 * Abstract method that will be defined in each child class. Updates the nextState in each individual
	 * cell.
	 * 
	 * 
	 * */
	public void updateCellStates() {
		for(int row=0;row<currentCells.length;row++) {
			for(int col=0;col<currentCells[row].length;col++) {
				currentCells[row][col].calculateNextState();
			}
		}
	}
	 
	/**
	 * Abstract method for which all cells will need to instantiate.
	 * Sets the neighbors of each cell with accordance to the simulation rules.
	 * */	
	public Cell[][] setNeighbors(Cell[][] initialCells, Wrapping wrap, Direction direction){
		return neighborSetter.setNeighbors(initialCells, wrap);
	}
	
	/**
	 * Transitions each cell to it next state as defined within the cell.
	 * */
	private void transitionCellStates(){
		for(int row=0;row<currentCells.length;row++) {
			for(int col=0;col<currentCells.length;col++) {
				/*if(currentCells[row][col].getCurrentState()!=currentCells[row][col].getNextState())
					System.out.println(String.format("Cell states changed from %s to %s"
							,currentCells[row][col].getCurrentState(),currentCells[row][col].getNextState()));
				else
					System.out.println(String.format("Cell states stayed at %s"
							,currentCells[row][col].getCurrentState()));*/
				currentCells[row][col].transitionState();
			}
		}
	}
	
	/**
	 * Sets the neighbors of cells when the simulation requires eight adjacent 
	 * neighbors and wraps
	 * 
	 * @param initialCells the initial cell state with no each cell having no neighbors
	 * @return The cells with each neighbor added appropriately.
	 * */
	public Cell[][] setNeighborsDiagonal(Cell[][]initialCells) {
		setCornerNeighborsDiagonal(0,0,initialCells); // set upper left corner
		setRowNeighborsDiagonal(0,initialCells); // set top row
		setCornerNeighborsDiagonal(0,initialCells.length-1,initialCells); // set upper right corner
		/* sets body of cell until last row*/
		for(int row=1;row<initialCells.length-1;row++) {
			for(int col=0;col<initialCells.length;col++) {
				/* handle is cell is located on an end column */
				if(col==0||col==initialCells.length-1)
					setEndColNeighborsDiagonal(row,col,initialCells);
				else
					setInnerCellNeighborsDiagonal(row,col,initialCells);
			}
		}
		setCornerNeighborsDiagonal(initialCells.length-1,0,initialCells); // set lower left corner
		setRowNeighborsDiagonal(initialCells.length-1,initialCells); // set bottom row
		setCornerNeighborsDiagonal(initialCells.length-1,initialCells.length-1,initialCells); // set lower right corner
		return initialCells;
	}
	
	/** Sets the neighbors of any corner blocks 
	 * 
	 * @param defaultRow Row of corner cell
	 * @param defaultCol Column of corner cell
	 * @param initialCells Array of cells
	 * 
	 * */
	private void setCornerNeighborsDiagonal(int defaultRow, int defaultCol, Cell[][]initialCells) {
		int rightCol=defaultCol+1;
		int leftCol=defaultCol-1;
		int topRow=defaultRow-1;
		int bottomRow=defaultRow+1;
		
		/* Determine the right most limit */
		if(rightCol==initialCells[defaultRow].length)
			rightCol=0;
		
		/* Determine the left most neighbor row */
		if(leftCol<0)
			leftCol=initialCells[defaultRow].length-1;
		
		/* Determine the bottom most limit*/
		if(bottomRow==initialCells.length)
			bottomRow=0;
		
		/* Determine the top most neighbor row */
		if(topRow<0)
			topRow=initialCells.length-1;
		
		int[] rows = {bottomRow,defaultRow,topRow};
		int[] columns = {leftCol,defaultCol,rightCol};
		
		for(int i=0;i<rows.length;i++) {
			for(int j=0;j<columns.length;j++) {
				if((i!=defaultRow) || (j!=defaultCol)) 
					initialCells[defaultRow][defaultCol].addNeighbors(initialCells[i][j]);
			}
		}
		
	}
	
	/** Sets the neighbors for any end of column cells under diagonal conditions 
	 * 
	 * @param defaultRow
	 * @param defaultCol
	 * @param initialCells
	 * */
	private void setEndColNeighborsDiagonal(int defaultRow, int defaultCol, Cell[][]initialCells) {
		int rightCol=defaultCol+1;
		int leftCol=defaultCol-1;
		/* Determine the right most limit*/
		if(rightCol==initialCells[defaultRow].length)
			rightCol=0;
		
		/* Determine the left most neighbor row */
		if(leftCol<0)
			leftCol=initialCells[defaultRow].length-1;
		
		for(int row=defaultRow-1;row<=defaultRow+1;row++) {
			if(row!=defaultRow)
				initialCells[defaultRow][defaultCol].addNeighbors(initialCells[row][defaultCol]);
			initialCells[defaultRow][defaultCol].addNeighbors(initialCells[row][leftCol]);
			initialCells[defaultRow][defaultCol].addNeighbors(initialCells[row][rightCol]);
		}
	}
	
	/**
	 * Sets the neighbors of cells, under diagonal conditions, for any cells in the top or bottom row
	 * 
	 *@param defaultRow Row of Cell which neighbors need to be assigned.
	 *@param initialCells Array of all cells
	 * */
	private void setRowNeighborsDiagonal(int defaultRow, Cell[][]initialCells) {
		int topRow=defaultRow-1;
		int bottomRow=defaultRow+1;
		
		/* Determine the bottom most limit*/
		if(defaultRow==initialCells.length-1)
			bottomRow=0;
		
		/* Determine the top most neighbor row */
		if(topRow<0)
			topRow=initialCells.length-1;
		for(int col=1;col<initialCells[defaultRow].length-1;col++) {
			//add the three bottom cells and top cells wrapped to bottom most cells
			for(int i=col-1;i<=col+1;i++) {
				if(i!=col) {
					initialCells[defaultRow][col].addNeighbors(initialCells[defaultRow][i]);
				}
				initialCells[defaultRow][col].addNeighbors(initialCells[topRow][i]);
				initialCells[defaultRow][col].addNeighbors(initialCells[bottomRow][i]);
			}
		}
	}
	
	/** Sets the neighbors of all interior cells
	 * 
	 * @param centerRow Row of cell that needs its neighbors set
	 * @param centerCol Column of cell that needs its neighbors set
	 * @param initialCells Array of initial cells
	 * */
	private void setInnerCellNeighborsDiagonal(int centerRow, int centerCol, Cell[][]initialCells) {
		for(int row=centerRow-1;row<=centerRow+1;row++) {
			for(int col=centerCol-1;col<=centerCol+1;col++) {				
				if((row!=centerRow) || (col!=centerCol)) {
					initialCells[centerRow][centerCol].addNeighbors(initialCells[row][col]);
				}
			}
		}
	}

	/** Sets the neighbors of cells when simulation wants 4 neighbors,
	 * aka non diagonal neighbor setting
	 * 
	 * @param initialCells initial array of cell
	 * @return cells with updated neighbors
	 * */
	public Cell[][] setNeighborsNonDiagonal(Cell[][]initialCells) {
		assert(initialCells.length >= 2 && initialCells[0].length >= 2);
		int numberOfRows = initialCells.length, numberOfColumns = initialCells[0].length;
		setTopLeftCellNeighborsNonDiagonal(initialCells);
		setFirstRowCellNeighborsNonDiagonal(initialCells, numberOfColumns);
		setTopRightCellNeighborsNonDiagonal(initialCells, numberOfColumns);
		for(int row = 1; row < initialCells.length - 1; row++) {
			setFirstColumnCellNeighborsNonDiagonal(initialCells, row);
			for(int col = 1; col < initialCells.length-1; col++) {
				setInnerCellNeighborsNonDiagonal(initialCells, row, col);
			}
			setLastColumnCellNeighborsNonDiagonal(initialCells, numberOfColumns, row);
		}
		setBottomLeftCellNeighborsNonDiagonal(initialCells, numberOfRows);
		setLastRowCellNeighborsNonDiagonal(initialCells, numberOfRows, numberOfColumns);
		setBottomRightCellNeighborsNonDiagonal(initialCells, numberOfRows, numberOfColumns);
		return initialCells;
	}

	private void setBottomRightCellNeighborsNonDiagonal(Cell[][] initialCells, int numberOfRows,
														int numberOfColumns) {
		initialCells[numberOfRows-1][numberOfColumns-1].addNeighbors(
				initialCells[numberOfRows-1][numberOfRows-2],
				initialCells[numberOfRows - 2][numberOfColumns - 1]);
	}

	private void setBottomLeftCellNeighborsNonDiagonal(Cell[][] initialCells, int numberOfRows) {
		initialCells[numberOfRows-1][0].addNeighbors(
				initialCells[numberOfRows-1][1],
				initialCells[numberOfRows-2][0]);
	}

	private void setTopLeftCellNeighborsNonDiagonal(Cell[][] initialCells) {
		initialCells[0][0].addNeighbors(
				initialCells[0][1],
				initialCells[1][0]);
	}

	private void setLastRowCellNeighborsNonDiagonal(Cell[][] initialCells, int numberOfRows, int numberOfColumns) {
		for (int i = 1; i < numberOfColumns -1; i++) {
			initialCells[numberOfRows-1][i].addNeighbors(
					initialCells[numberOfRows-1][i-1],
					initialCells[numberOfRows-1][i+1],
					initialCells[numberOfRows-2][i]);
		}
	}

	private void setFirstRowCellNeighborsNonDiagonal(Cell[][] initialCells, int numberOfColumns) {
		for (int i = 1; i < numberOfColumns-1; i++) {
			initialCells[0][i].addNeighbors(
					initialCells[0][i-1],
					initialCells[0][i+1],
					initialCells[1][i]);
		}
	}

	private void setTopRightCellNeighborsNonDiagonal(Cell[][] initialCells, int numberOfColumns) {
		initialCells[0][numberOfColumns-1].addNeighbors(
				initialCells[0][numberOfColumns-2],
				initialCells[1][numberOfColumns-1]);
	}

	private void setFirstColumnCellNeighborsNonDiagonal(Cell[][] initialCells, int row) {
		initialCells[row][0].addNeighbors(
				initialCells[row-1][0],
				initialCells[row+1][0],
                initialCells[row][1]);
	}

	private void setLastColumnCellNeighborsNonDiagonal(Cell[][] initialCells, int numberOfColumns, int row) {
		initialCells[row][numberOfColumns-1].addNeighbors(
				initialCells[row-1][numberOfColumns-1],
				initialCells[row+1][numberOfColumns-1],
				initialCells[row][numberOfColumns-2]);
	}

	private void setInnerCellNeighborsNonDiagonal(Cell[][] initialCells, int row, int col) {
		initialCells[row][col].addNeighbors(
				initialCells[row-1][col],
				initialCells[row+1][col],
                initialCells[row][col-1],
				initialCells[row][col+1]);
	}

}
