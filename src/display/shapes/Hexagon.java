package display.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class Hexagon extends Polygon {
	
	protected double width, height;
	
	public Hexagon (double width, double height)
	{
		this.width = width;
		this.height = height;
		resize(width, height);
		this.setFill(Color.RED);
	}
	
	public void resize(double newWidth, double newHeight) {
		this.getPoints().clear();
		this.getPoints().addAll(new Double[] { 0.0, (newHeight / 2.0), 
											  (newWidth / 3.0), 0.0, 
											  (newWidth * 2.0 / 3.0), 0.0,
											  newWidth, (newHeight / 2.0),
											  (newWidth * 2.0 / 3.0), newHeight,
											  (newWidth / 3.0), newHeight, 
											  });
	}

}
