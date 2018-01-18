package sliders;

import event_handling.SliderEventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ResourceBundle;

public class BaseSlider extends GridPane {

	private static final int WIDTH = 195;
	private static final int HEIGHT = 50;
	private static final String SLIDERS_PROPERTIES = "sliders";
	protected Slider slider;
	protected Label label;
	protected SliderEventHandler eventHandler;
	protected ResourceBundle myResources;

	/** set up initial Slider parameters with
	 *  slider and label
	 * 
	 */
	public BaseSlider(SliderEventHandler eh) {
		slider = new Slider();
		label = new Label();
		eventHandler = eh;
		ColumnConstraints con = new ColumnConstraints();
		con.setMinWidth(WIDTH);
		this.getColumnConstraints().add(con);
		add(label, 0, 0);
		add(slider, 0, 1);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		setAlignment(Pos.CENTER_LEFT);
		myResources = ResourceBundle.getBundle(SLIDERS_PROPERTIES);
		setPrefHeight(HEIGHT);
		setPrefWidth(WIDTH);
		setMinWidth(WIDTH);
		
		// set default slider values for 0-1 scale
		slider.setMin(0.0);
		slider.setMax(1.0);
		slider.setMajorTickUnit(0.25);

	}
	
	public void setValue(double value) {
		slider.setValue(value);
	}
	
	public void update() {
		double temp = slider.getValue();
		slider.setValue(0);
		slider.setValue(temp);
	}

}
