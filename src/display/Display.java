package display;

import cells.Cell;
import display.grids.BaseGrid;
import display.grids.HexGrid;
import display.grids.SquareGrid;
import display.grids.TriangleGrid;
import display.menu.MainMenu;
import event_handling.MenuEventHandler;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import manager.SimulationManager;
import sliders.BaseSlider;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class Display {

	private static final int SLIDER_OFFSET = 175;
	private static final int BUTTON_HEIGHT = 40;
	// basic setup
	private static final Dimension DEFAULT_SIZE = new Dimension(600, 700);
	private static final Dimension GRID_SIZE = new Dimension(450, 450);

	// hold the number of rows and columns for the output
	private int numRows = 0, numCols = 0;

	// grid to hold Cells
	BaseGrid grid;

	// list of scene elements
	private Scene scene;
	private Stage stage;
	private ObservableList<Node> windowLayout;
	private List<BaseSlider> sliders;
	BorderPane root;

	// reference to calling manager
	private SimulationManager manager;

	public Display(Stage s, SimulationManager m) {
		this(s, 1, 1, null, "", m); // create a default simulation with a 1x1 grid
	}

	/**
	 * 
	 * @param rowSpan
	 *            - number of rows
	 * @param columnSpan
	 *            - number of sliders
	 * @param sliderList
	 *            - list of sliders needed for the current simulation
	 * @param simName
	 *            - name of the current simulation
	 */
	public Display(Stage s, int rowSpan, int columnSpan, List<BaseSlider> sliderList, String simName,
			SimulationManager m) {
		// store initial grid size
		numRows = rowSpan;
		numCols = columnSpan;
		manager = m;
		sliders = sliderList;
		stage = s;
		root = new BorderPane();
		StackPane center = new StackPane();
		root.setLeft(null);
		root.setBottom(null);
		center.setPrefSize(DEFAULT_SIZE.width, DEFAULT_SIZE.height);
		root.setCenter(center);
		center.setAlignment(Pos.TOP_LEFT);
		scene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height, Color.WHITE);
		windowLayout = center.getChildren();
		
		
		setupGrid(createSquareGrid());
		
		//setup main menu
		MainMenu menu = new MainMenu(new MenuEventHandler(m, this, stage, scene), manager);
		menu.prefWidthProperty().bind(s.widthProperty());
	    root.setTop(menu);
		
		

		// setup scene elements
	    Group buttons = new Group();
	    buttons.getChildren().addAll(addDefaultButtons());
	    root.setBottom(buttons);
		addSliders();
		s.setScene(scene);
		s.setTitle(simName);
		s.setResizable(false);
		s.show();
		
		
	}

	public void setupGrid(BaseGrid newGrid) {
		
		if(grid != null)
		{
			windowLayout.remove(grid);
		}
		
		grid = newGrid;

		// align the grid
		grid.setTranslateX(DEFAULT_SIZE.width / 2 - GRID_SIZE.width / 2);
		//grid.setTranslateY(GRID_SIZE.height / 2 + GRID_OFFSET);
		
		//grid.setTranslateX(0);
		grid.setTranslateY(0);
		
		grid.setBorder(true);
		windowLayout.add(grid);

	}
	
	public HexGrid createHexGrid()
	{
		return new HexGrid(numRows, numCols, GRID_SIZE.width, GRID_SIZE.height);
	}
	
	public TriangleGrid createTriangleGrid()
	{
		return new TriangleGrid(numRows, numCols, GRID_SIZE.width, GRID_SIZE.height);
	}
	
	public SquareGrid createSquareGrid()
	{
		return new SquareGrid(numRows, numCols, GRID_SIZE.width, GRID_SIZE.height);
	}	

	private void addSliders() {

		double y = SLIDER_OFFSET;
		for (int i = 0; i < sliders.size(); i++) {
			windowLayout.add(sliders.get(i));
			sliders.get(i).setTranslateX((i % 3) * sliders.get(i).getPrefWidth());
			sliders.get(i).setTranslateY(y);
			if(i % 3 == 2) y += sliders.get(i).getPrefHeight();
		}
	}

	/**
	 * 
	 * @param cellArray
	 *            - 2d array of cells
	 */
	public void updateCells(Cell[][] cellArray) {
		// iterate over the 2d array of cells
		int count = 0;
		for (Node n : grid.getChildren()) {
			Color color;
			try {
				Shape r = (Shape) n; // cast the node to a rectangle
				/* get current color from cell */
				color = (cellArray[grid.getRow(n)][grid.getCol(n)].getColor());
				r.setFill(color);
			} catch (ClassCastException e) // failed one of the casts
			{
				// do nothing
				// there is a single Group as a member of the grid, cannot be cast
			} catch (IndexOutOfBoundsException e) {
				System.out.println(
						String.format("Index %d %d out of bounds\n", grid.getRow(n), grid.getCol(n)));
			}
		}
	}

	/**
	 * Adds the start / stop / step buttons
	 */
	private List<Button> addDefaultButtons() {
		Button startButton = new Button();
		Button stopButton = new Button();
		Button stepButton = new Button();
		Button resetButton = new Button();

		startButton.setText("Start");
		stopButton.setText("Stop");
		stepButton.setText("Step");
		resetButton.setText("Reset");

		startButton.setOnAction(actionEvent -> manager.startPushed());
		stopButton.setOnAction(actionEvent -> manager.stopPushed());
		stepButton.setOnAction(actionEvent -> manager.step());
		resetButton.setOnAction(actionEvent -> manager.resetPushed());
		startButton.setOnMouseClicked(e -> manager.startPushed());
		stopButton.setOnMouseClicked(actionEvent -> manager.stopPushed());
		stepButton.setOnMouseClicked(actionEvent -> manager.step());
		resetButton.setOnMouseClicked(actionEvent -> manager.resetPushed());

		ArrayList<Button> buttons = new ArrayList<Button>();
		buttons.add(startButton);
		buttons.add(stopButton);
		buttons.add(stepButton);
		buttons.add(resetButton);

		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setPrefWidth(DEFAULT_SIZE.width / 4.0);
			buttons.get(i).setPrefHeight(BUTTON_HEIGHT);
			//buttons.get(i).setTranslateY(DEFAULT_SIZE.height - BUTTON_HEIGHT * 2);
			buttons.get(i).setTranslateY(0);
			buttons.get(i).setTranslateX(i * buttons.get(i).getPrefWidth());
			
		}
		
		//windowLayout.addAll(buttons);
		return buttons;
	}	
	
	public BaseGrid getGrid() {
		return grid;
	}
	
}
