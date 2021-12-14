package xgis;


import java.math.BigDecimal;
import java.util.*;

/**
 * The class XGIS extends the functionality of class RectilinearRegion. XGIS determines if the given RectilinearRegion
 * has any connected rectangles or not. A pair of connected rectangles will have at least one common side.
 */
public class XGIS extends RectilinearRegion {

	/**
	 * Instantiates a new Xgis with a set of Rectangles.
	 *
	 * @param rectangles the set of rectangles
	 */
	public XGIS(Set<Rectangle> rectangles) {
		super(rectangles);
	}

	/**
	 * Instantiates a new Xgis with an instance of RectilinearRegion.
	 *
	 * @param rectRegion an instance of RectilinearRegion;
	 */
	public XGIS(RectilinearRegion rectRegion) {
		super(rectRegion.getRectangles());
	}

	/**
	 * IsConnected is the primary method of this extended class.
	 * A pair of connected rectangles will have at least one common side.
	 *
	 * @return boolean True if there exists a pair of connected rectangles else returns False.
	 */
	public boolean isConnected() {
		List<Line> lines = new ArrayList<>();
		getRectangles().forEach((n) -> {
			lines.addAll(n.allSides());
		});
		List<Coordinate> corners = new ArrayList<>();
		getRectangles().forEach((n) -> {
			corners.addAll(n.allCorners());
		});
		for (Coordinate c : corners) {
			for (Line l : lines) {
				if(l.in(c))
					return true;
			}
		}
		return false;
	}
}
