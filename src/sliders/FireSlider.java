package sliders;

import event_handling.SliderEventHandler;

public class FireSlider extends BaseSlider {
	
	public FireSlider(SliderEventHandler eh)
	{
		super(eh);
		label.setText(myResources.getString("Fire"));		
		slider.valueProperty().addListener(e -> {eh.setCatchFireProbability(slider.getValue());
													System.out.println(slider.getValue());});
	}
}
