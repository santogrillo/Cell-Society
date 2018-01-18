package display.grids;

import java.awt.Dimension;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

public class BaseGrid extends GridPane {

	private static final double DEFAULT_SIZE = 100.0;

	protected int numRows, numCols;

	public BaseGrid() {
		this(1, 1, DEFAULT_SIZE, DEFAULT_SIZE);
	}

	public BaseGrid(int numRows, int numCols, double width, double height) {
		// set the width and the height
		setPrefWidth(width);
		setPrefHeight(height);
		this.setMinWidth(width);
		setMaxWidth(width);
		setMinHeight(height);
		setMaxHeight(height);

		// align the grid
		setAlignment(Pos.TOP_LEFT);

		this.numRows = numRows;
		this.numCols = numCols;
	}

	protected void clearGrid() {
		getChildren().clear();
		getRowConstraints().clear();
		getColumnConstraints().clear();
	}

	protected void buildGrid(int rows, int cols) {
		// add the columns to the grid
		for (int i = 0; i < cols; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setMaxWidth(getPrefWidth() / cols);
			getColumnConstraints().add(colConst);
		}
		// add rows to the grid
		for (int i = 0; i < rows; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setMaxHeight(getPrefHeight() / rows);
			getRowConstraints().add(rowConst);
		}
	}

	protected void rebuild() {
		clearGrid();
		buildGrid(numRows, numCols);
	}

	/**
	 * 
	 * @param newWidth
	 *            - the preferred width
	 * @param newHeight
	 *            = the preferred height
	 */
	public void resizeGrid(double newWidth, double newHeight) {
		setPrefWidth(newWidth);
		setPrefHeight(newHeight);

		rebuild();
	}

	/**
	 * 
	 * @param cols
	 *            - new number of columns displayed in the grid
	 */
	public void setColumns(int cols) {
		setDimensions(numRows, cols);
	}

	/**
	 * 
	 * @param rows
	 *            - new number of rows displayed in the grid
	 */
	public void setRows(int rows) {
		setDimensions(rows, numCols);
	}

	/**
	 * Sets the new number of rows and columns in the grid
	 * 
	 * @param rows
	 * @param cols
	 */
	public void setDimensions(int rows, int cols) {
		numRows = rows;
		numCols = cols;
		rebuild();
	}

	public int getRow(Node Child) {
		return getRowIndex(Child);
	}

	public int getCol(Node Child) {
		return getColumnIndex(Child);
	}

	public void setBorder(boolean enable) {
		for (Node n : this.getChildren()) {
			if (n instanceof Shape) {
				Shape s = (Shape)n;
				if (enable) {
					s.setStroke(Color.BLACK);
					s.setStrokeWidth(1);
					s.setStrokeType(StrokeType.INSIDE);
				} else {
					s.setStroke(s.getFill());
					s.setStrokeWidth(0);
				}
			}
		}
	}
	
	public int getRows() {
		return numRows;
	}
	
	public int getCols() {
		return numCols;
	}
}
