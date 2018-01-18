package display.grids;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

public class SquareGrid extends BaseGrid {

	public SquareGrid() {
		super();
	}

	public SquareGrid(int numRows, int numCols, double width, double height) {
		super(numRows, numCols, width, height);
		buildGrid(numRows, numCols);
	}

	@Override
	protected void buildGrid(int numRows, int numCols) {
		super.buildGrid(numRows, numCols);
		
		// add a display rectangle to each spot on the grid
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				Rectangle r = new Rectangle(getPrefWidth() / numCols, getPrefHeight() / numRows);
				add(r, j, i);
			}
		}
		
		
	}

}
