package file_processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cells.BacterialCell;
import cells.Cell;
import cells.ConwayCell;
import cells.DayNightCell;
import cells.FireCell;
import cells.ForagingCell;
import cells.LoopCell;
import cells.SegregationCell;
import cells.WaTorCell;
import javafx.geometry.Point2D;

public class GridMaker {

	private static final String INVALID_CELL_STATE3 = ". Setting cell state as empty.";

	private static final String INVALID_CELL_STATE2 = ", column ";

	private static final String INVALID_CELL_STATE1 = "Invalid cell state given for cell in row ";

	private static final String CELLTYPE_STRING = "cellType";

	private static final String STATE_STRING = "state";

	private static final String COLUMN_STRING = "column";

	private static final String ROW_STRING = "row";

	private List<Point2D> locations = new ArrayList<>();

	private Random locationPicker = new Random();

	private Point2D currLocation;

	private Map<String, String> param = new HashMap<>();

	private Cell[][] grid;

	private int numRows;

	private int numColumns;

	public GridMaker(Map<String, String> parameterMap, Cell[][] cellArray, int rows, int columns) {
		param = parameterMap;
		grid = cellArray;
		numRows = rows;
		numColumns = columns;
	}

	protected void makeLocationsList() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				locations.add(new Point2D(i, j));
			}
		}
	}

	protected Map<String, String> updateLocation(List<Point2D> locations) {
		Map<String, String> cellStates = new HashMap<>();
		currLocation = locations.remove(locationPicker.nextInt(locations.size()));
		int row = (int) currLocation.getX();
		int column = (int) currLocation.getY();
		cellStates.put(ROW_STRING, String.valueOf(row));
		cellStates.put(COLUMN_STRING, String.valueOf(column));
		return cellStates;
	}

	protected void createNewCell(Map<String, String> cellStates) {
		String state = cellStates.get(STATE_STRING);
		int row = Integer.parseInt(cellStates.get(ROW_STRING));
		int column = Integer.parseInt(cellStates.get(COLUMN_STRING));
		if (param.get(CELLTYPE_STRING).equals("ConwayCell")) {
			for (ConwayCell.State s : ConwayCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new ConwayCell(s);
					return;
				}
			}
		} else if (param.get(CELLTYPE_STRING).equals("FireCell")) {
			for (FireCell.State s : FireCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new FireCell(s);
					return;
				}
			}
		} else if (param.get(CELLTYPE_STRING).equals("SegregationCell")) {
			for (SegregationCell.State s : SegregationCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new SegregationCell(s);
					return;
				}
			}
		} else if (param.get(CELLTYPE_STRING).equals("WaTorCell")) {
			for (WaTorCell.State s : WaTorCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new WaTorCell(s);
					return;
				}
			}

		} else if (param.get(CELLTYPE_STRING).equals("BacterialCell")) {
			for (BacterialCell.State s : BacterialCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new BacterialCell(s);
					return;
				}
			}
		} else if (param.get(CELLTYPE_STRING).equals("DayNightCell")) {
			for (DayNightCell.State s : DayNightCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new DayNightCell(s);
					return;
				}
			}
		} else if (param.get(CELLTYPE_STRING).equals("ForagingCell")) {
			for (ForagingCell.State s : ForagingCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new ForagingCell(s);
					return;
				}
			}
		} else if (param.get(CELLTYPE_STRING).equals("LoopCell")) {
			for (LoopCell.State s : LoopCell.State.values()) {
				if (s.toString().equals(state)) {
					grid[row][column] = new LoopCell(s, row, column);
					return;
				}
			}
		}
		System.out.println(INVALID_CELL_STATE1 + row + INVALID_CELL_STATE2 + column + INVALID_CELL_STATE3);
		createEmptyCell(cellStates);
	}

	protected void createEmptyCell(Map<String, String> cellStates) {
		int row = Integer.parseInt(cellStates.get(ROW_STRING));
		int column = Integer.parseInt(cellStates.get(COLUMN_STRING));
		if (param.get(CELLTYPE_STRING).equals("ConwayCell")) {
			grid[row][column] = new ConwayCell(ConwayCell.State.DEAD);
		} else if (param.get(CELLTYPE_STRING).equals("FireCell")) {
			grid[row][column] = new FireCell(FireCell.State.EMPTY);
		} else if (param.get(CELLTYPE_STRING).equals("SegregationCell")) {
			grid[row][column] = new SegregationCell(SegregationCell.State.EMPTY);
		} else if (param.get(CELLTYPE_STRING).equals("WaTorCell")) {
			grid[row][column] = new WaTorCell(WaTorCell.State.WATER);
		} else if (param.get(CELLTYPE_STRING).equals("BacterialCell")) {
			grid[row][column] = new BacterialCell(BacterialCell.State.EMPTY);
		} else if (param.get(CELLTYPE_STRING).equals("DayNightCell")) {
			grid[row][column] = new DayNightCell(DayNightCell.State.NIGHT);
		} else if (param.get(CELLTYPE_STRING).equals("ForagingCell")) {
			grid[row][column] = new ForagingCell(ForagingCell.State.GROUND);
		} else if (param.get(CELLTYPE_STRING).equals("LoopCell")) {
			grid[row][column] = new LoopCell(LoopCell.State.BLACK, row, column);
		}
	}

	public List<Point2D> getLocationsList() {
		return locations;
	}
}