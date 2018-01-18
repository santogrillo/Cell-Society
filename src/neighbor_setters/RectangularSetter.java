package neighbor_setters;

import cells.Cell;

/**
 * Sets neighbors for a rectangular grid type.
 * 
 * <note> Since a rectangular cell is just a combination of cardinal and diagonal directions,
 * each method calls both of the classes from the other setter
 */
public class RectangularSetter extends NeighborSetter {
	private CardinalNeighborSetter cardinal;
	private DiagonalNeighborSetter diagonal;
	
	public RectangularSetter() {
		cardinal = new CardinalNeighborSetter();
		diagonal = new DiagonalNeighborSetter();
	}
	
	public void setInterior(Cell[][] initialCells, int row, int col) {
		cardinal.setInterior(initialCells, row, col);
		diagonal.setInterior(initialCells, row, col);
		
	}

	public void setCorner(Cell[][] initialCells, int row, int col, Wrapping wrap) {
		cardinal.setCorner(initialCells, row, col, wrap);
		diagonal.setCorner(initialCells, row, col, wrap);
		
	}

	public void setDefaultColumn(Cell[][] initialCells, int row, int col) {
		cardinal.setDefaultColumn(initialCells, row, col);
		diagonal.setDefaultColumn(initialCells, row, col);
		
	}

	public void setToroidalColumn(Cell[][] initialCells, int row, int col) {
		cardinal.setToroidalColumn(initialCells, row, col);
		diagonal.setToroidalColumn(initialCells, row, col);
		
	}

	public void setDefaultRow(Cell[][] initialCells, int row, int col) {
		cardinal.setDefaultRow(initialCells, row, col);
		diagonal.setDefaultRow(initialCells, row, col);
		
	}

	public void setToroidalRow(Cell[][] initialCells, int row, int col) {
		cardinal.setToroidalRow(initialCells, row, col);
		diagonal.setToroidalRow(initialCells, row, col);
	}
   
}
