package display.shapes;

public class UpTriangle extends Triangle {

	public UpTriangle(double Width, double Height) {
		super(Width, Height);
		resize(Width, Height);
	}

	@Override
	public void resize(double newWidth, double newHeight) {
		this.getPoints().clear();
		this.getPoints().addAll(new Double[] { 0.0, newHeight, newWidth, newHeight, (newWidth / 2.0), 0.0 });
	}
}
