package sliders;

import event_handling.SliderEventHandler;

public class SatisfactionSlider extends BaseSlider {

	public SatisfactionSlider(SliderEventHandler eh) {
		super(eh);
		label.setText(myResources.getString("Satisfaction"));
		slider.valueProperty().addListener(e -> eh.setSatisfactionThreshold(slider.getValue()));
	}
}