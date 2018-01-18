import display.SplashScreen;
import javafx.application.Application;
import javafx.stage.Stage;
import manager.SimulationManager;

public class Launcher extends Application {
	private SimulationManager manager;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// manager = new SimulationManager();
		// manager.start(primaryStage);
		// new Display(primaryStage, 50, 50, null, "Test", manager);
		new SplashScreen(primaryStage);
	}

	/**
	 * Start the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}