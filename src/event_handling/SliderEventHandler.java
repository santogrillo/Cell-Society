package event_handling;

import cells.Cell;
import cells.FireCell;
import cells.SegregationCell;
import manager.SimulationManager;

/**
 * Handles events from sliders in all simulations.
 *
 * @author Ben Schwennesen
 */
public class SliderEventHandler {

	private Cell[][] grid;
	private SimulationManager manager;

	public SliderEventHandler(Cell[][] grid, SimulationManager manager) {
		this.grid = grid;
		this.manager = manager;
	}

	public void setSatisfactionThreshold(double newThreshold) {
		for (Cell[] rowOfCells : grid) {
			for (Cell cell : rowOfCells) {
				((SegregationCell) cell).setSatisfactionThreshold(newThreshold);
			}
		}
	}

	public void setCatchFireProbability(double newProbability) {
		System.out.println("Fire slider updated");
		for (Cell[] rowOfCells : grid) {
			for (Cell cell : rowOfCells) {
				((FireCell) cell).setCatchFireProbability(newProbability);
			}
		}
	}

	public void setAnimationSpeed(double newSpeed) {
		manager.setSpeed(newSpeed);
	}

	public void setCellArray(Cell[][] array) {
		grid = array;
	}
}
