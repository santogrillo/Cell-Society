package file_processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cells.*;
import javafx.geometry.Point2D;

public class RandomGridMaker extends GridMaker {

	private static final String CELLTYPE_STRING = "cellType";

	private static final String STATE_STRING = "state";

	private Map<String, String> param = new HashMap<>();

	private List<Point2D> locations = new ArrayList<>();

	private int totalLoc;

	public RandomGridMaker(Map<String, String> parameterMap, Cell[][] cellArray, int numRows, int numColumns,
			List<Point2D> locationsList, int totalLocations) {
		super(parameterMap, cellArray, numRows, numColumns);
		param = parameterMap;
		locations = locationsList;
		totalLoc = totalLocations;
	}

	public void fillGrid() {
		Random r = new Random();
		for (int i = 0; i < totalLoc; i++) {
			Map<String, String> cellStates = updateLocation(locations);
			String randState = pickRandomState(r, param);
			cellStates.put(STATE_STRING, randState);
			System.out.println(cellStates.get(STATE_STRING));
			createNewCell(cellStates);
		}
		while (locations.size() > 0) {
			Map<String, String> cellStates = updateLocation(locations);
			createEmptyCell(cellStates);
		}
	}

	private String pickRandomState(Random r, Map<String, String> param) {
		if (param.get(CELLTYPE_STRING).equals("ConwayCell")) {
			List<ConwayCell.State> states = Arrays.asList(ConwayCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		} else if (param.get(CELLTYPE_STRING).equals("FireCell")) {
			List<FireCell.State> states = Arrays.asList(FireCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		} else if (param.get(CELLTYPE_STRING).equals("SegregationCell")) {
			List<SegregationCell.State> states = Arrays.asList(SegregationCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		} else if (param.get(CELLTYPE_STRING).equals("WaTorCell")) {
			List<WaTorCell.State> states = Arrays.asList(WaTorCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		} else if (param.get(CELLTYPE_STRING).equals("BacterialCell")) {
			List<BacterialCell.State> states = Arrays.asList(BacterialCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		} else if (param.get(CELLTYPE_STRING).equals("DayNightCell")) {
			List<DayNightCell.State> states = Arrays.asList(DayNightCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		} else if (param.get(CELLTYPE_STRING).equals("ForagingCell")) {
			List<ForagingCell.State> states = Arrays.asList(ForagingCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		} else if (param.get(CELLTYPE_STRING).equals("LoopCell")) {
			List<LoopCell.State> states = Arrays.asList(LoopCell.State.values());
			return states.get(r.nextInt(states.size())).toString();
		}
		return "";
	}
}