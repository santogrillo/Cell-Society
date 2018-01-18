package sliders;

import event_handling.SliderEventHandler;

public class FPSSlider extends BaseSlider {
	
	public FPSSlider(SliderEventHandler eh)
	{
		super(eh);
		label.setText(myResources.getString("Speed"));
		slider.setMin(1);
		slider.setMax(2000);
		slider.setMajorTickUnit(999);
		slider.valueProperty().addListener(e -> eh.setAnimationSpeed(slider.getValue()));
	}
}
