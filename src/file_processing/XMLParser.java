package file_processing;

import cells.*;
import javafx.geometry.Point2D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to parse XML files. The parameters are stored in
 * a HashMap and the grid is filled as indicated by the given XML file. The XML
 * file must have root attribute named type, which specifies the simulation
 * type. Otherwise, an exception is thrown.
 * 
 * @author Richard Tseng
 */

public class XMLParser extends XMLHandler {

	private static final String OUT_OF_BOUNDS4 = ".";

	private static final String OUT_OF_BOUNDS3 = " based on number of cells created ";

	private static final String OUT_OF_BOUNDS2 = ", column ";

	private static final String OUT_OF_BOUNDS1 = "Given cell location was outside the bounds of the grid's size. Putting cell in row ";

	private static final String NO_SIMULATION_TYPE = "No simulation type given. Starting simulation as Game of Life.";

	private static final String UNSUPPORTED_MODEL = "XML file does not represent a supported simulation.";

	private static final String RANDOM_STRING = "Random";

	private static final String PROBABILITY_STRING = "Probability";

	private static final String PARAMETER_STRING = "parameter";

	private static final String GAME_OF_LIFE = "GameOfLife";

	private static final String SIMULATIONTYPE = "simulationType";

	private static final String FILE_STRING = "File";

	private static final String GRID_STRING = "grid";

	private static final String COLUMN_STRING = "column";

	private static final String ROW_STRING = "row";

	private static final String NUMBER_OF_COLUMNS = "numberOfColumns";

	private static final String NUMBER_OF_ROWS = "numberOfRows";

	private static final String SET_INITIAL_CONFIGURATION_BY = "setInitialConfigurationBy";

	private static final String MODEL = "Cellular Automata";

	// keep only one documentBuilder because it is expensive to make and can reset
	// it before parsing
	private final DocumentBuilder DOCUMENT_BUILDER;

	private Map<String, Double> prob = new HashMap<>();

	// HashMap of the parameters read in from the XML file
	private Map<String, String> param = new HashMap<>();

	// 2D array of integers representing the cells in the grid
	private Cell[][] grid;

	private int numRows;

	private int numColumns;

	private GridMaker g;

	/**
	 * Create a parser for XML files of given type.
	 */
	public XMLParser(String type) {
		super(type);
		DOCUMENT_BUILDER = getDocumentBuilder();
	}

	/**
	 * Get the data contained in this XML file and store it
	 */
	public void parseXMLFile(File xmlFile) {
		try {
			DOCUMENT_BUILDER.reset();
			Document document = DOCUMENT_BUILDER.parse(xmlFile);
			Element root = document.getDocumentElement();
			if (!isValidFile(root, MODEL)) {
				throw new XMLException(UNSUPPORTED_MODEL);
			}
			readParameters(root);
			String initConfig = makeGrid();
			g = new GridMaker(param, grid, numRows, numColumns);
			if (initConfig.equals(FILE_STRING)) {
				fillGrid(root);
				return;
			}
			g.makeLocationsList();
			List<Point2D> l = g.getLocationsList();
			if (initConfig.equals(RANDOM_STRING)) {
				NodeList random = getChildren(root, RANDOM_STRING.toLowerCase());
				int totalLoc = Integer.parseInt(random.item(1).getTextContent());
				g = new RandomGridMaker(param, grid, numRows, numColumns, l, totalLoc);
				((RandomGridMaker) g).fillGrid();
			}
			if (initConfig.equals(PROBABILITY_STRING)) {
				NodeList probabilities = getChildren(root, PROBABILITY_STRING.toLowerCase());
				for (int i = 0; i < probabilities.getLength(); i++) {
					Node probability = probabilities.item(i);
					if (probability.getNodeType() == Node.ELEMENT_NODE) {
						prob.put(probability.getNodeName(), Double.parseDouble(probability.getTextContent()));
					}
				}
				g = new ProbabilityGridMaker(param, grid, numRows, numColumns, l, prob);
				((ProbabilityGridMaker) g).fillGrid();
			}

		} catch (SAXException | IOException e) {
			throw new XMLException(e);
		}
	}

	/*
	 * Stores parameters in the XML file into HashMap of parameters
	 */
	private void readParameters(Element root) {
		NodeList parameters = getChildren(root, PARAMETER_STRING);
		for (int i = 0; i < parameters.getLength(); i++) {
			Node parameter = parameters.item(i);
			if (parameter.getNodeType() == Node.ELEMENT_NODE) {
				param.put(parameter.getNodeName(), parameter.getTextContent());
			}
		}
		if (!param.containsKey(SIMULATIONTYPE)) {
			param.put(SIMULATIONTYPE, GAME_OF_LIFE);
			System.out.println(NO_SIMULATION_TYPE);
		}
	}

	/*
	 * Initializes grid of cells. Returns a String read in from the XML file
	 * representing how the grid should be filled.
	 */
	private String makeGrid() {
		numRows = Integer.parseInt(param.get(NUMBER_OF_ROWS));
		numColumns = Integer.parseInt(param.get(NUMBER_OF_COLUMNS));
		grid = new Cell[numRows][numColumns];
		if (param.containsKey(SET_INITIAL_CONFIGURATION_BY)) {
			return param.get(SET_INITIAL_CONFIGURATION_BY);
		}
		return FILE_STRING;
	}

	/*
	 * Fills grid with cells as specified in the XML file.
	 */
	public void fillGrid(Element root) {
		NodeList cells = getChildren(root, GRID_STRING);
		int numCells = 0;
		for (int i = 0; i < cells.getLength(); i++) {
			Node cell = cells.item(i);
			if (cell.getNodeType() == Node.ELEMENT_NODE) {
				NodeList states = cell.getChildNodes();
				Map<String, String> cellStates = new HashMap<>();
				for (int j = 0; j < states.getLength(); j++) {
					Node state = states.item(j);
					if (state.getNodeType() == Node.ELEMENT_NODE) {
						cellStates.put(state.getNodeName(), state.getTextContent());
					}
				}
				checkOutOfBounds(cellStates, numCells);
				g.createNewCell(cellStates);
				numCells++;
			}
		}
	}

	private Map<String, String> checkOutOfBounds(Map<String, String> cellStates, int numCells) {
		int row = Integer.parseInt(cellStates.get(ROW_STRING));
		int column = Integer.parseInt(cellStates.get(COLUMN_STRING));
		if (row > numRows || column > numColumns) {
			String newRow = String.valueOf(numCells / numRows);
			String newColumn = String.valueOf(numCells % numColumns);
			System.out.println(
					OUT_OF_BOUNDS1 + newRow + OUT_OF_BOUNDS2 + newColumn + OUT_OF_BOUNDS3 + numCells + OUT_OF_BOUNDS4);
			cellStates.put(ROW_STRING, newRow);
			cellStates.put(COLUMN_STRING, newColumn);
		}
		return cellStates;
	}

	/*
	 * Returns HashMap of parameters
	 */
	public Map<String, String> getParameters() {
		return param;
	}

	/*
	 * Returns grid of cells
	 */
	public Cell[][] getGrid() {
		return grid;
	}
}