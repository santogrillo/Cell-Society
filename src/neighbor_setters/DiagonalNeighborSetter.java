package neighbor_setters;

import cells.Cell;

public class DiagonalNeighborSetter extends NeighborSetter {

	public void setInterior(Cell[][] initialCells, int row, int col) {
		Cell currentCell = initialCells[row][col];
		for (int i = row - 1; i <= row + 1; i++) {
			if (i != row) {
				currentCell.addNeighbors(initialCells[i][col - 1]);
				currentCell.addNeighbors(initialCells[i][col + 1]);
			}
		}
	}

	public void setDefaultColumn(Cell[][] initialCells, int row, int col) {
		Cell currentCell = initialCells[row][col];
		
		if (col == 0) {
			currentCell.addNeighbors(initialCells[row - 1][col + 1]);
			currentCell.addNeighbors(initialCells[row + 1][col + 1]);
		} else {
			currentCell.addNeighbors(initialCells[row - 1][col - 1]);
			currentCell.addNeighbors(initialCells[row + 1][col - 1]);
		}
	
	}

	public void setToroidalColumn(Cell[][] initialCells, int row, int col) {
		Cell currentCell = initialCells[row][col];
		if (col == 0) {
			currentCell.addNeighbors(initialCells[row - 1][getMaxCols() - 1]);
			currentCell.addNeighbors(initialCells[row + 1][getMaxCols() - 1]);
		} else {
			currentCell.addNeighbors(initialCells[row - 1][0]);
			currentCell.addNeighbors(initialCells[row + 1][0]);
		}
	}

	public void setDefaultRow(Cell[][] initialCells, int row, int col) {
		Cell currentCell = initialCells[row][col];
		if (row == 0) {
			currentCell.addNeighbors(initialCells[row + 1][col - 1]);
			currentCell.addNeighbors(initialCells[row + 1][col + 1]);
		} else {
			currentCell.addNeighbors(initialCells[row - 1][col - 1]);
			currentCell.addNeighbors(initialCells[row - 1][col + 1]);
		}
	}

	public void setToroidalRow(Cell[][] initialCells, int row, int col) {
		Cell currentCell = initialCells[row][col];
		if (row == 0) {
			currentCell.addNeighbors(initialCells[row][initialCells[row].length - 1]);
		} else {
			currentCell.addNeighbors(initialCells[row][0]);
		}
	}

	public void setCorner(Cell[][] initialCells, int row, int col, Wrapping wrap) {
		Cell currentCell = initialCells[row][col];
		if (isToroidal(wrap)) {
			handleCornerToroidal(initialCells, row, col);
		} else {
			int newRow = getCornerVals(row);
			int newCol = getCornerVals(col);
			currentCell.addNeighbors(initialCells[newRow][newCol]);
		}
	}

	private void handleCornerToroidal(Cell[][] initialCells, int row, int col) {
		Cell currentCell = initialCells[row][col];
		int[] rows, cols;
		rows = getCornerArray(row, getMaxRows());
		cols = getCornerArray(col, getMaxCols());
		for (int i = 0; i <= rows.length; i++) {
			for (int j = 0; j < cols.length; j++) {
				currentCell.addNeighbors(initialCells[i][j]);
			}
		}
	}
}
