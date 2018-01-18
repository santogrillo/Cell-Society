package display.grids;

import display.shapes.Hexagon;
import javafx.geometry.Pos;
import javafx.scene.Node;

public class HexGrid extends BaseGrid {
	public HexGrid() {
		super();
	}

	public HexGrid(int numRows, int numCols, double width, double height) {
		super(numRows, numCols, width * 2, height);

		this.numCols = 4 * numCols;
		this.numRows ++;
		buildGrid(this.numRows, this.numCols);
		
	}

	@Override
	protected void buildGrid(int numRows, int numCols) {
		super.buildGrid(numRows, numCols);

		double hexWidth = 3.0 / 2.0 * getPrefWidth() / (double) numCols + 2;
		double hexHeight = 2 * getPrefHeight() / (double) numRows + 1;

		// add a hexagon to each spot on the grid
		for (int i = 0; i < numRows - 1; i++) {
			for (int j = 0; j < numCols; j++) {

				if (i % 2 == 0) {
					if (j % 4 == 0) {
						Hexagon h = new Hexagon(hexWidth, hexHeight);
						add(h, j, i);
					}
				}
				else {
					if (j >= 2 && ((j - 2) % 4 == 0)) {
						Hexagon h = new Hexagon(hexWidth, hexHeight);
						add(h, j, i);
					}
				}
			}
		}
	}

	@Override
	public int getCol(Node Child) {
		int result = super.getCol(Child);
		return result / 4;
	}
	@Override
	public int getCols() {
		return numCols / 4;
	}

}
