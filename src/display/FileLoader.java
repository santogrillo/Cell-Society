package display;

import java.io.File;

import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import manager.SimulationManager;

public class FileLoader {
	
	public static void openFile(File file, Stage s) {
		SimulationManager manager = new SimulationManager();
		manager.setSimulationFile(file);
		manager.start(s);
	}
	
	public static File chooseFile(Scene s)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Simulation XML File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter(
				"Simulation XML File", "*.xml"));
		return fileChooser.showOpenDialog(s.getWindow());
	}
}
