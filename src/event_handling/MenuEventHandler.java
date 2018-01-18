/**
 * Menu Event Handler
 * 
 * @author Santo Grillo
 */

package event_handling;

import java.io.File;
import display.Display;
import display.FileLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.SimulationManager;
import neighbor_setters.NeighborSetter;

public class MenuEventHandler {

	private SimulationManager manager;
	private Display display;
	private Stage stage;
	private Scene scene;

	public MenuEventHandler(SimulationManager m, Display d, Stage stage, Scene scene) {
		manager = m;
		display = d;
		this.stage = stage;
		this.scene = scene;
	}

	/**
	 * @param selected
	 *            - whether or not to enable true - enabled, false - disabled
	 */
	public void enableBorders(boolean selected) {
		display.getGrid().setBorder(selected);
	}

	/**
	 * set the grid to square cells
	 */
	public void setSquareCells() {
		display.setupGrid(display.createSquareGrid());
		manager.updateDisplay();
	}

	/**
	 * Triangle Cells
	 */
	public void setTriangleCells() {
		display.setupGrid(display.createTriangleGrid());
		manager.updateDisplay();
	}

	/**
	 * Hex Cells
	 */
	public void setHexCells() {
		display.setupGrid(display.createHexGrid());
		manager.updateDisplay();
	}

	/**
	 * Sets neighbor calculation to ALL
	 */
	public void setAllNeighbors() {
		manager.setDirection(NeighborSetter.Direction.ALL);
		manager.setNeighborSetter();
		manager.setNeighbors();
	}

	/**
	 * Sets neighbor calculation to Cardinal Directions
	 */
	public void setCardinalNeighbors() {
		manager.setDirection(NeighborSetter.Direction.CARDINAL);
		manager.setNeighborSetter();
		manager.setNeighbors();
	}

	/**
	 * Sets neighbor calculation to Diagonals
	 */
	public void setDiagonalNeighbors() {
		manager.setDirection(NeighborSetter.Direction.DIAGONAL);
		manager.setNeighborSetter();
		manager.setNeighbors();
	}

	/**
	 * Open a new file
	 */
	public void newFile() {
		File file = FileLoader.chooseFile(scene);
		if (file != null) {
			FileLoader.openFile(file, stage);
		}
	}

	/**
	 * Save the current file
	 */
	public void save() {
		manager.saveState();
	}

}
