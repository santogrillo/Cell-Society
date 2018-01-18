package file_processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cells.Cell;
import javafx.geometry.Point2D;

public class ProbabilityGridMaker extends GridMaker {

	private static final String STATE_STRING = "state";

	// HashMap of the probabilities for each cell state
	private Map<String, Double> prob = new HashMap<>();

	private List<Point2D> locations = new ArrayList<>();

	private int numRows;

	private int numColumns;

	public ProbabilityGridMaker(Map<String, String> parameterMap, Cell[][] cellArray, int rows, int columns,
			List<Point2D> locationsList, Map<String, Double> probabilityMap) {
		super(parameterMap, cellArray, rows, columns);
		locations = locationsList;
		numRows = rows;
		numColumns = columns;
		prob = probabilityMap;
	}

	public void fillGrid() {
		for (String state : prob.keySet()) {
			int total = (int) (numRows * numColumns * prob.get(state));
			for (int i = 0; i < total; i++) {
				Map<String, String> cellStates = updateLocation(locations);
				cellStates.put(STATE_STRING, state);
				createNewCell(cellStates);
			}
		}
	}
}