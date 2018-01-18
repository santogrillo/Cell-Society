package display.grids;

import display.shapes.DownTriangle;
import display.shapes.Triangle;
import display.shapes.UpTriangle;

public class TriangleGrid extends BaseGrid {
	public TriangleGrid() {
		super();
	}

	public TriangleGrid(int numRows, int numCols, double width, double height) {
		super(numRows, numCols, width, height);
		buildGrid(this.numRows, this.numCols);
		//this.setPrefWidth(this.getPrefWidth() * 2);
	}

	@Override
	protected void buildGrid(int numRows, int numCols) {
		super.buildGrid(numRows, numCols);

		// add a display triangle to each spot on the grid
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {

				Triangle t;
				double triWidth = 2 * getPrefWidth() / (double) numCols;
				double triHeight = getPrefHeight() / (double) numRows;

				if ((i % 2 + j % 2) == 1) {
					t = new DownTriangle(triWidth, triHeight);
				} else {
					t = new UpTriangle(triWidth, triHeight);
				}
				add(t, j, i);
			}
		}
	}
}
