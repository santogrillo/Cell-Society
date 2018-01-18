package display.shapes;

import javafx.scene.shape.Polygon;

public abstract class Triangle extends Polygon {
	
	protected double width, height;
	
	Triangle() {
		this(0,0);
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	Triangle(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public abstract void resize(double newWidth, double newHeight);
}
