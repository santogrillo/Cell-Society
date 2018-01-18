package display.shapes;

import javafx.scene.paint.Color;

public class DownTriangle extends Triangle {

	public DownTriangle(double Width, double Height) {
		super(Width, Height);
		this.getPoints().addAll(new Double[] { 0.0, 0.0, width, 0.0, (width / 2.0), height });
		this.setFill(Color.RED);
	}

	@Override
	public void resize(double newWidth, double newHeight) {
		this.getPoints().clear();
		this.getPoints().addAll(new Double[] { 0.0, 0.0, newWidth, 0.0, (newWidth / 2.0), newHeight });
	}
}
