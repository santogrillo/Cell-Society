package manager;

import cells.Cell;
import display.Display;
import event_handling.SliderEventHandler;
import file_processing.XMLGenerator;
import file_processing.XMLParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import neighbor_setters.*;
import neighbor_setters.NeighborSetter.Direction;
import neighbor_setters.NeighborSetter.Wrapping;
import simulation_processors.*;
import sliders.BaseSlider;
import sliders.FPSSlider;
import sliders.FireSlider;
import sliders.SatisfactionSlider;
import utilities.PropertiesGetter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SimulationManager {
	
	public static final List<String> SIMULATIONTYPES = Arrays
			.asList(new String[] { "GameOfLife", "Fire", "Segregation", "WaTor", "Bacteria", "DayNight", "Foraging", "Loop"});
	
	private final String TYPE_ATTRIBUTE = "model";
	private final String XML_ROW_NAME = "numberOfRows";
	private final String XML_COL_NAME = "numberOfColumns";
	private final String XML_SIMULATION_NAME = "simulationType";
	private final String FIRE_STRING = "Fire";
	private final String SEGREGATION_STRING = "Segregation";
	private static final int FRAMES_PER_SECOND = 60;
	private int millisecondDelay = 1000 / FRAMES_PER_SECOND;

	private Timeline animation;
	private KeyFrame frame;
	private int numberOfRows;
	private int numberOfColumns;
	private BaseProcessor processor;
	private Stage s;
	private Display display;
	private HashMap<String, String> initialConditions;
	private String simulationType;
	private XMLParser xmlParser;
	private Cell[][] cells;
	private Cell[][] defaultCells;
	private List<BaseSlider> sliders;
	private SliderEventHandler eventHandler;
	private File xmlFile;
	private Wrapping wrap=Wrapping.FINITE;
	private Direction direction;
	public enum Shape {
		TRIANGLE, RECTANGLE, HEXAGON
}
	private Shape shape=Shape.RECTANGLE;
	/**
	 * Get the initial simulation conditions HashMap from the XML file
	 * */
	private void getInitialConditions() {
		initialConditions = (HashMap) xmlParser.getParameters();
	}
	
	public void saveState() {
		XMLGenerator gen = new XMLGenerator(TYPE_ATTRIBUTE, initialConditions, cells);
		gen.saveXMLFile();
	}

	/** Dissect the initial condition hashmap to get necessary parameters for the simulation 
	 * */
	private void setInitialParameters() {
		simulationType = initialConditions.get(XML_SIMULATION_NAME);
		numberOfRows = Integer.parseInt(initialConditions.get(XML_ROW_NAME));
		numberOfColumns = Integer.parseInt(initialConditions.get(XML_COL_NAME));
	}

	/**
	 * Initializes objects necessary for the simulation.
	 * Gets parameters from XML files and sets up display.
	 * 
	 * @param s Stage for display to be displayed on
	 * */
	private void startupSimulation(Stage s) {
		xmlParser = new XMLParser(TYPE_ATTRIBUTE);
		xmlParser.parseXMLFile(xmlFile);
		getCellsFromXML();
		getInitialConditions();
		setInitialParameters();
		setInitialSliders();
		// set default display
		setProcessor();
		display = new Display(s, numberOfRows, numberOfColumns, sliders, simulationType, this);
		setNeighborSetter();
	}

	/**
	 * Gets default cells from XML file and sets them as such.
	 * Initializes current values of cells to be kept track off.
	 * */
	private void getCellsFromXML() {
		defaultCells = xmlParser.getGrid();
		setCells(defaultCells);
	}


	/**
	 * Set the cells of this manager. Make a copy of the passed grid so as not to modify it.
	 * 
	 * @param newGrid - the new 2D Cell array
	 * */
	private void setCells(Cell[][] newGrid) {
		cells = newGrid;
	}

	
	
	
	/**
	 * Determines what processor is needed to run the game and sets processor accordingly
	 * */
	private void setProcessor() {
		direction= Direction.CARDINAL;
		if (simulationType.equals(GAME_OF_LIFE_STRING)) {
			processor = new BaseProcessor();
			direction = Direction.ALL;
		}
		else if (simulationType.equals(FIRE_STRING))
			processor = new BaseProcessor();
		else if (simulationType.equals(SEGREGATION_STRING)) {
			processor = new SegregationProcessor();
			direction = Direction.ALL;
		}
		else if(simulationType.equals(WATOR_WORLD_STRING)) {
			processor = new BaseProcessor();
		}
		else if(simulationType.equals("Bacteria")) {
			processor = new BaseProcessor();
		}
		else if(simulationType.equals("DayNight")) {
			processor = new BaseProcessor();
			direction = Direction.ALL;
		}
		else if(simulationType.equals("Foraging")) {
			processor = new BaseProcessor();
		}
		else if(simulationType.equals("Loop")) {
			processor = new BaseProcessor();
		}
		else {
			System.out.println("Invalid simulation type" + simulationType);
		}
		System.out.println("Invalid simulation type" + simulationType);
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setNeighborSetter() {
		if(direction==Direction.CARDINAL)
			processor.setNeighborSetter(new CardinalNeighborSetter());
		else if(direction==Direction.DIAGONAL)
			processor.setNeighborSetter(new DiagonalNeighborSetter());
		else if(direction==Direction.ALL) {
			if(shape==Shape.RECTANGLE) {
				processor.setNeighborSetter(new RectangularSetter());
			}
			else if(shape == Shape.TRIANGLE) {
				processor.setNeighborSetter(new TriangularSetter());
			}
			else if(shape == Shape.HEXAGON) {
				processor.setNeighborSetter(new HexagonalSetter());
			}
		}
	}
	
	/**
	 * Sets up initial slider for display
	 * */
	private void setInitialSliders() {
		/* Event handler for sliders*/
		eventHandler = new SliderEventHandler(cells, this);
		/* Initialize Slider array */
		sliders = new ArrayList<BaseSlider>();
		FPSSlider frames = new FPSSlider(eventHandler);
		frames.setValue(PropertiesGetter.getDefaultAnimationDelay());
		sliders.add(frames); // add basic slider
		/* Add simulation specific sliders*/
		if (simulationType.contentEquals("Fire")) {
			FireSlider slider = new FireSlider(eventHandler);
			slider.setValue(PropertiesGetter.getDefaultCatchFireProbability());
			sliders.add(slider);
		}
		else if (simulationType.contentEquals(SEGREGATION_STRING)) {
			SatisfactionSlider slider = new SatisfactionSlider(eventHandler);
			slider.setValue(PropertiesGetter.getDefaultSatisfactionThreshold());
			sliders.add(slider);
		}
		/*
		else if(simulationType.contentEquals("WaTor"))
			slider.add(new WaTorSlider(eventSLider));	
		*/
		else {
			//System.out.println(String.format("Invalid simulation type %s\n", simulationType));
		}
	}
	
	/**
	 * Main step method controls animation and states of cells.
	 * */
	public void step() {
		/* Set the cells to their next state */
		this.setCells(processor.processCells(cells));
		System.out.println(processor.neighborSetter.getClass());
		/* Display updated cells */
		updateDisplay();
	}

	public void updateDisplay() {
		display.updateCells(cells);
	}
	
	/** Called upon the start of the application
	 *  Sets up all default objects and initializes variables
	 *  */
	public void start(Stage s) {
		// attach "game loop" to timeline to play it
		//xmlFile = new File("C:\\Users\\Michael\\eclipse-workspace\\cellsociety_team16\\data\\Segregation.xml");
		animation = new Timeline();
		startupSimulation(s);
		setNeighbors();
		display.updateCells(cells);
		//testSimulation(s);	
		frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.stop();
	}

	public void setNeighbors() {
		cells = processor.setNeighbors(cells,wrap,direction);
	}

	/** Sets the simulation XML file to the passed file 
	 * 
	 * @param simulationFile simulationFIle to be set as the XML file
	 * */
	public void setSimulationFile(File simulationFile) {
		this.xmlFile=simulationFile;
	}
	
	/**
	 * Plays the simulation
	 * */
	public void startPushed() {
		System.out.println("pushed start");
		animation.play();
	}

	/**
	 * Pauses the simulation
	 * */
	public void stopPushed() {
		animation.stop();
	}

	public void setDirection(Direction direction) {
		this.direction=direction;
	}
	
	public void setWrap(Wrapping wrap) {
		this.wrap=wrap;
	}
	
	public void setShape(Shape shape) {
		this.shape=shape;
	}
	/**
	 * Resets the simulation to default cells
	 * */
	public void resetPushed() {
		stopPushed();
		xmlParser.parseXMLFile(xmlFile);
		setCells(processor.setNeighbors(xmlParser.getGrid(),wrap,direction));
		eventHandler.setCellArray(cells);
		updateDisplay();
		
		for(BaseSlider b : sliders)
		{
			b.update();
		}
		
	}

	/**
	 * Sets the application speed
	 * 
	 * @param newSpeed New desired speed of application
	 * */
	public void setSpeed(double newSpeed) {
		animation.stop();
		animation.getKeyFrames().remove(frame);
		frame = new KeyFrame(Duration.millis(newSpeed), e -> step());
		animation.getKeyFrames().add(frame);
	}

}