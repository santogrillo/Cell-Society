package neighbor_setters;

import cells.Cell;

/**
 * Sets neighbors for a hexagonal grid type.
 */
public class HexagonalSetter extends DiagonalNeighborSetter {
	
	private final int VERTICAL_INCREMENT=2;

	public void setInterior(Cell[][] initialCells, int row, int col) {
		super.setInterior(initialCells, row, col);
		Cell currentCell = initialCells[row][col];
		/* Add neighboring cell above and below current cell*/
		currentCell.addNeighbors(initialCells[row+VERTICAL_INCREMENT][col]);
		currentCell.addNeighbors(initialCells[row-VERTICAL_INCREMENT][col]);
	}

	@Override
	public void setDefaultColumn(Cell[][] initialCells, int row, int col) {
		super.setDefaultColumn(initialCells, row, col);
		Cell currentCell = initialCells[row][col];
		currentCell.addNeighbors(initialCells[row+VERTICAL_INCREMENT][col]);
		currentCell.addNeighbors(initialCells[row-VERTICAL_INCREMENT][col]);
	}

	public void setToroidalColumn(Cell[][] initialCells, int row, int col) {
		super.setToroidalColumn(initialCells,row,col);
	}

	@Override
	public void setDefaultRow(Cell[][] initialCells, int row, int col) {
		super.setDefaultRow(initialCells,row,col);
		addVerticalCell(initialCells,row,col);
		
	}

	@Override
	public void setToroidalRow(Cell[][] initialCells, int row, int col) {
		/* Add final vertical cells*/
		if(row==0) {
			initialCells[row][col].addNeighbors(initialCells[getMaxRows()-1][col]);
			initialCells[row][col].addNeighbors(initialCells[getMaxRows()-VERTICAL_INCREMENT][col-1]);
			initialCells[row][col].addNeighbors(initialCells[getMaxRows()-VERTICAL_INCREMENT][col+1]);
		}
		else {
			initialCells[row][col].addNeighbors(initialCells[0][col]);
			initialCells[row][col].addNeighbors(initialCells[1][col+1]);
			initialCells[row][col].addNeighbors(initialCells[1][col-1]);
		}
	}
	
	@Override
	public void setCorner(Cell[][] initialCells, int row, int col, Wrapping wrap) {
		Cell currentCell = initialCells[row][col];
		int newRow = getCornerVals(row);
		int newCol = getCornerVals(col);
		currentCell.addNeighbors(initialCells[newRow][newCol]);
		addVerticalCell(initialCells,row,col);
		if (isToroidal(wrap)) 
			setCornerToroidal(initialCells, row, col);
	}
	
	private void setCornerToroidal(Cell[][] initialCells, int row, int col) {
		Cell currentCell = initialCells[row][col];
		/* Add opposite corner */
		currentCell.addNeighbors(initialCells[getOpposite(row,getMaxRows()-1)][getOpposite(col,getMaxCols()-1)]);
		/* Add final cells */
		if(row==0) {
			currentCell.addNeighbors(initialCells[getMaxRows()-1][col]); // add vertical cell
			if(col==0) {
				currentCell.addNeighbors(initialCells[getMaxRows()-VERTICAL_INCREMENT][col+1]);
				currentCell.addNeighbors(initialCells[row+VERTICAL_INCREMENT][getMaxCols()-1]);
			}else {
				currentCell.addNeighbors(initialCells[row+VERTICAL_INCREMENT][getMaxCols()-1]);
				currentCell.addNeighbors(initialCells[row+2][0]);
			}
		}else {
			currentCell.addNeighbors(initialCells[0][col]);
			if(col==0) {
				currentCell.addNeighbors(initialCells[1][col+1]);
				currentCell.addNeighbors(initialCells[row-VERTICAL_INCREMENT][getMaxCols()-1]);
			}else {
				currentCell.addNeighbors(initialCells[row-VERTICAL_INCREMENT][0]);
				currentCell.addNeighbors(initialCells[1][col-1]);
			}
		}
	}

	private int getOpposite(int val,int max) {
		if(val==0) {
			return max;
		}else
			return val;
	}

	private void addVerticalCell(Cell[][] initialCells,int row,int col) {
		if(row==0)
			initialCells[row][col].addNeighbors(initialCells[row+VERTICAL_INCREMENT][col]);
		else
			initialCells[row][col].addNeighbors(initialCells[row-VERTICAL_INCREMENT][col]);
	}
	

}
