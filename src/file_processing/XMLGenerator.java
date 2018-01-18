package file_processing;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import cells.Cell;

public class XMLGenerator extends XMLHandler {

	private static final String ATTRIBUTE_VALUE = "Cellular Automata";

	private static final String CELL_STRING = "cell";

	private static final String GRID_STRING = "grid";

	private static final String PARAMETER_STRING = "parameter";

	private static final String FILE_SAVED_MESSAGE1 = "File saved as 'Save";
	
	private static final String FILE_SAVED_MESSAGE2 = ".xml'.";

	private static final String YES = "yes";

	private static final String INDENT_AMOUNT_VALUE = "2";

	private static final String INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";

	private static final String FILEPATH = "data/Save ";
	
	private static final String XML_EXTENSION = ".xml";

	private static final String ATTRIBUTE_NAME = "model";

	private static final String SIMULATION_STRING = "simulation";

	public static final List<String> PARAMETERS = Arrays
			.asList(new String[] { "simulationType", "cellType", "numberOfColumns", "numberOfRows", });

	public static final List<String> STATES = Arrays.asList(new String[] { "state", "row", "column",
			"numberOfChrononsSurvivedSinceLastReproduction", "energyLevel", "colorGradient", "homePheromoneLevel",
			"foodPheromoneLevel", "timeStepsSinceBirth", "hasFoodItem" });

	// keep only one documentBuilder because it is expensive to make and can reset
	// it before parsing
	private final DocumentBuilder DOCUMENT_BUILDER;

	// keep only one transformer because it is expensive to make and can reset it
	// before parsing
	private final Transformer TRANSFORMER;

	// A HashMap of the parameters read in from the XML file
	private Map<String, String> param = new HashMap<String, String>();

	// 2D array of integers representing the cells in the grid
	private Cell[][] grid;

	public XMLGenerator(String type, Map<String, String> parameterMap, Cell[][] cellArray) {
		super(type);
		DOCUMENT_BUILDER = getDocumentBuilder();
		TRANSFORMER = getTransformer();
		param = parameterMap;
		grid = cellArray;
	}

	public void saveXMLFile() {
		DOCUMENT_BUILDER.reset();
		Document document = DOCUMENT_BUILDER.newDocument();
		Element root = document.createElement(SIMULATION_STRING);
		document.appendChild(root);
		root.setAttribute(ATTRIBUTE_NAME, ATTRIBUTE_VALUE);
		root.appendChild(writeParameters(document));
		root.appendChild(writeGrid(document));
		DOMSource source = new DOMSource(document);
		String timestamp = Calendar.getInstance().getTime().toString();
		StreamResult result = new StreamResult(new File(FILEPATH + timestamp + XML_EXTENSION));
		TRANSFORMER.reset();
		TRANSFORMER.setOutputProperty(INDENT_AMOUNT, INDENT_AMOUNT_VALUE);
		TRANSFORMER.setOutputProperty(OutputKeys.INDENT, YES);
		try {
			TRANSFORMER.transform(source, result);
			System.out.println(FILE_SAVED_MESSAGE1 + timestamp + FILE_SAVED_MESSAGE2);
		} catch (TransformerException e) {
			throw new XMLException(e);
		}
	}

	private Element writeParameters(Document document) {
		Element parameter = document.createElement(PARAMETER_STRING);
		for (String p : PARAMETERS) {
			Element e = document.createElement(p);
			e.appendChild(document.createTextNode(param.get(p)));
			parameter.appendChild(e);
		}
		return parameter;
	}

	private Element writeGrid(Document document) {
		Element g = document.createElement(GRID_STRING);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				Element cell = document.createElement(CELL_STRING);
				Map<String, String> states = grid[i][j].toXMLMap(i, j);
				for (String state : STATES) {
					if (states.containsKey(state)) {
						Element s = document.createElement(state);
						s.appendChild(document.createTextNode(states.get(state)));
						cell.appendChild(s);
					}
				}
				g.appendChild(cell);
			}
		}
		return g;
	}
}