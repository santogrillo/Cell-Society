/*
 * The primary menu at the top of the user window. Holds options to load a new file,
 * save the current file, or exit. Also contains a variety of style options, that can be selected by the user
 * 
 * This class is well designed because it is almost completely self contained. Additional functionality can be easily
 * added, and it interacts cleanly with the rest of the program.
 * 
 * @author Santo Grillo
 * 
 */

package display.menu;

import event_handling.MenuEventHandler;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import manager.SimulationManager;

public class MainMenu extends MenuBar {

	private RadioMenuItem bordersEnabled, bordersDisabled, toroidal, finite, infinite, square, triangle, hex,
			allNeighbors, cardinalNeighbors, diagonalNeighbors;

	private MenuItem newMenuItem, saveMenuItem, exitMenuItem;

	private MenuEventHandler eventHandler;

	private SimulationManager manager;

	/**
	 * 
	 * @param e
	 *            - MenuEventHandler, processes input to the various options in the
	 *            menu
	 * @param m
	 *            - SimulationManager, a reference to the simulation manager, in
	 *            order to access functions like Save
	 */
	public MainMenu(MenuEventHandler e, SimulationManager m) {
		eventHandler = e;
		manager = m;
		Menu fileMenu = fileMenu();
		Menu styleMenu = styleMenu();
		getMenus().addAll(fileMenu, styleMenu);
		setupInput();
	}

	/**
	 * File menu
	 * 
	 * @return - returns a menu with the default file options
	 */

	private Menu fileMenu() {
		// File menu - new, save, exit
		Menu fileMenu = new Menu("File");
		newMenuItem = new MenuItem("New");
		saveMenuItem = new MenuItem("Save");
		exitMenuItem = new MenuItem("Exit");

		fileMenu.getItems().addAll(newMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
		return fileMenu;
	}

	/**
	 * 
	 * @return - The base menu for the style options, Calls various submenus that
	 *         contain style choices
	 */

	private Menu styleMenu() {
		// Style Menu borders on/off
		Menu styleMenu = new Menu("Style");
		Menu shapeMenu = shapeMenu();
		Menu borderMenu = borderMenu();
		Menu wrappingMenu = wrappingMenu();
		Menu neighborMenu = neighborMenu();

		styleMenu.getItems().addAll(borderMenu, new SeparatorMenuItem(), wrappingMenu, new SeparatorMenuItem(),
				neighborMenu, new SeparatorMenuItem(), shapeMenu);
		return styleMenu;
	}

	/**
	 * Border options
	 */

	private Menu borderMenu() {
		Menu borderMenu = new Menu("Borders");
		ToggleGroup borderToggle = new ToggleGroup();
		bordersEnabled = new RadioMenuItem("Enabled");
		bordersEnabled.setToggleGroup(borderToggle);
		bordersEnabled.setSelected(true);
		bordersDisabled = new RadioMenuItem("Disabled");
		bordersDisabled.setToggleGroup(borderToggle);
		borderMenu.getItems().addAll(bordersEnabled, bordersDisabled);
		return borderMenu;
	}

	/**
	 * wrapping options
	 */
	private Menu wrappingMenu() {
		Menu wrappingMenu = new Menu("Wrapping");
		ToggleGroup wrapGroup = new ToggleGroup();
		toroidal = new RadioMenuItem("Toroidal");
		finite = new RadioMenuItem("Finite");
		infinite = new RadioMenuItem("Infinite");
		toroidal.setToggleGroup(wrapGroup);
		finite.setToggleGroup(wrapGroup);
		infinite.setToggleGroup(wrapGroup);
		toroidal.setSelected(true);
		infinite.setDisable(true);
		wrappingMenu.getItems().addAll(toroidal, finite, infinite);

		toroidal.disableProperty().bind(triangle.selectedProperty());

		return wrappingMenu;
	}

	/**
	 * Grid shape options
	 */
	private Menu shapeMenu() {
		Menu shapeMenu = new Menu("Cell Shape");
		ToggleGroup shapeGroup = new ToggleGroup();
		square = new RadioMenuItem("Square");
		triangle = new RadioMenuItem("Triangle");
		hex = new RadioMenuItem("Hexagon");
		square.setSelected(true);
		square.setToggleGroup(shapeGroup);
		triangle.setToggleGroup(shapeGroup);
		hex.setToggleGroup(shapeGroup);
		shapeMenu.getItems().addAll(square, triangle, hex);
		return shapeMenu;
	}

	/**
	 * Neighbor calculation options
	 */
	private Menu neighborMenu() {
		Menu neighborMenu = new Menu("Neighbors");
		ToggleGroup neightborGroup = new ToggleGroup();
		allNeighbors = new RadioMenuItem("All neighbors");
		cardinalNeighbors = new RadioMenuItem("Cardinal Directions");
		diagonalNeighbors = new RadioMenuItem("Diagonals");
		allNeighbors.setToggleGroup(neightborGroup);
		cardinalNeighbors.setToggleGroup(neightborGroup);
		diagonalNeighbors.setToggleGroup(neightborGroup);

		// set the default neightbor configuration, since some sims require specific
		// settings to work correctly
		switch (manager.getDirection()) {
		case ALL:
			allNeighbors.setSelected(true);
			break;
		case CARDINAL:
			cardinalNeighbors.setSelected(true);
			break;
		case DIAGONAL:
			diagonalNeighbors.setSelected(true);
			break;
		default:
			cardinalNeighbors.setSelected(true);
			break;

		}

		neighborMenu.getItems().addAll(cardinalNeighbors, diagonalNeighbors, allNeighbors);

		// disable certain types of neighbor calculations based on shape
		diagonalNeighbors.disableProperty().bind(triangle.selectedProperty());
		cardinalNeighbors.disableProperty().bind(hex.selectedProperty());

		return neighborMenu;
	}

	/**
	 * Enable action events for each menu option
	 */
	private void setupInput() {
		// turn borders on/off
		bordersEnabled.setOnAction(actionEvent -> eventHandler.enableBorders(bordersEnabled.isSelected()));
		bordersDisabled.setOnAction(actionEvent -> eventHandler.enableBorders(!bordersDisabled.isSelected()));

		// change cell shape, disable certain neighbor settings for incompatible shapes
		square.setOnAction(actionEvent -> {
			eventHandler.setSquareCells();
			eventHandler.enableBorders(bordersEnabled.isSelected());
		});
		triangle.setOnAction(actionEvent -> {
			eventHandler.setTriangleCells();
			eventHandler.enableBorders(bordersEnabled.isSelected());
			if (diagonalNeighbors.isSelected()) {
				diagonalNeighbors.setSelected(false);
				allNeighbors.setSelected(true);
			}
			if (toroidal.isSelected()) {
				toroidal.setSelected(false);
				finite.setSelected(true);
			}
		});
		hex.setOnAction(actionEvent -> {
			eventHandler.setHexCells();
			eventHandler.enableBorders(bordersEnabled.isSelected());
			if (cardinalNeighbors.isSelected()) {
				cardinalNeighbors.setSelected(false);
				allNeighbors.setSelected(true);
			}
		});

		// change neighbors
		allNeighbors.setOnAction(actionEvent -> eventHandler.setAllNeighbors());
		diagonalNeighbors.setOnAction(actionEvent -> eventHandler.setDiagonalNeighbors());
		cardinalNeighbors.setOnAction(actionEvent -> eventHandler.setCardinalNeighbors());

		// new, save, exit
		newMenuItem.setOnAction(actionEvent -> eventHandler.newFile());
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
		saveMenuItem.setOnAction(actionEvent -> eventHandler.save());
	}

}
