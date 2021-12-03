package xgis;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
		return checkVertical() || checkHorizontal();
	}

	private boolean checkVertical() {
		Set<BigDecimal> vert = new HashSet<>();
		for (Rectangle rect : getRectangles()) {
			if (vert.contains(rect.left()) || vert.contains(rect.right())) {
				return true;
			}
			vert.add(rect.right());
			vert.add(rect.left());
		}
		return false;
	}

	private boolean checkHorizontal() {
		Set<BigDecimal> hori = new HashSet<>();
		for (Rectangle rect : getRectangles()) {
			if (hori.contains(rect.top()) || hori.contains(rect.bottom())) {
				return true;
			}
			hori.add(rect.top());
			hori.add(rect.bottom());
		}
		return false;
	}

}
