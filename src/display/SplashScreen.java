package display;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import manager.SimulationManager;

import java.awt.*;
import java.io.File;
import java.util.ResourceBundle;



public class SplashScreen {
	private static final int TITLE_SIZE = 75;
	private static final int LOAD_BUTTON_OFFSET = 20;
	// basic setup
	private static final Dimension DEFAULT_SIZE = new Dimension(600, 800);
	private static final String DEFAULT_RESOURCE_PACKAGE = "";

	// list of scene elements
	private Scene scene;
	private Stage stage;
	private ObservableList<Node> sceneList;
	private ResourceBundle myResources;


	private Desktop desktop = Desktop.getDesktop();

	public SplashScreen(Stage s) {
		// setup scene elements
		Group root = new Group();
		sceneList = root.getChildren();
		scene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height, Color.WHITE);
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "default");
		stage = s;
		setupDisplay(s);
		s.setScene(scene);
		s.setTitle(myResources.getString("SPLASH_TITLE"));
		s.setResizable(false);
		s.show();
	}
	
	private void setupDisplay(Stage stage)
	{
		Text titleText = new Text();
		titleText.setText(myResources.getString("SPLASH_TITLE"));
		//center the title in the middle of the screen
		titleText.setFont(new Font("Helvetica", TITLE_SIZE));
		titleText.setX(scene.getWidth() / 2 - titleText.getBoundsInLocal().getWidth() / 2);
		titleText.setY(scene.getHeight() / 2 - titleText.getBoundsInLocal().getHeight() / 2);
		sceneList.add(titleText);
		
		final Button OPEN_XML_BUTTON = new Button("Open a Simulation File...");
		OPEN_XML_BUTTON.setPrefHeight(30);
		OPEN_XML_BUTTON.setPrefWidth(200);
		OPEN_XML_BUTTON.setLayoutX(scene.getWidth() / 2 - OPEN_XML_BUTTON.getPrefWidth() / 2);
		OPEN_XML_BUTTON.setLayoutY(titleText.getY()+titleText.getBoundsInLocal().getHeight() + LOAD_BUTTON_OFFSET);
		sceneList.add(OPEN_XML_BUTTON);

		OPEN_XML_BUTTON.setOnAction(event -> {
			File file = FileLoader.chooseFile(scene);
			if (file != null) {
				FileLoader.openFile(file, stage);
			}
		});
	}
}
