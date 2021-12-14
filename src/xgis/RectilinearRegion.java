package xgis;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Rectilinear region is a data structure that consists of a set of non-overlapping rectangles.
 */
public class RectilinearRegion {

	/**
	 * The set of rectangles.
	 */
	public final Set<Rectangle> rectangles;

	RectilinearRegion(Set<Rectangle> rectangles) {
		this.rectangles = new HashSet<>(rectangles);
	}

	/**
	 * Getter method to return the set of rectangles.
	 *
	 * @return the set of rectangles
	 */
	public Set<Rectangle> getRectangles() {
		return rectangles;
	}

	BiDimensionalMap<Rectangle> rectangleMap() {
		Set<BigDecimal> xCoord = new HashSet<>();
		Set<BigDecimal> yCoord = new HashSet<>();
		for (Rectangle rect : rectangles) {
			yCoord.add(rect.bottom());
			yCoord.add(rect.top());
			xCoord.add(rect.left());
			xCoord.add(rect.right());
		}
		BiDimensionalMap<Rectangle> grid = new BiDimensionalMap<>(xCoord, yCoord);
		for (Rectangle rect : rectangles) {
			BiDimensionalMap<Rectangle> newGridSlice = grid.slice(rect);
			newGridSlice.addEverywhere(rect);
		}
		return grid;
	}

	private boolean checkInside(Rectangle box, Rectangle toTest) {
		for (Coordinate corner : toTest.allCorners()) {
			if (box.bottomLeft().compareTo(corner) <= 0 && box.topRight().compareTo(corner) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Is overlapping boolean.
	 *
	 * @return the true if any of the rectangles overlap.
	 */
	public boolean isOverlapping() {
		for (Rectangle rectangle : rectangles) {
			for (Rectangle toTest : rectangles) {
				if (!Objects.equals(rectangle, toTest) && checkInside(rectangle, toTest)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Generates and returns a rectilinear region using the given set of rectangles.
	 * The rectangles cannot overlap each other, or it throws an IllegalArgumentException.
	 *
	 * @param rectangles the set rectangles from which to construct the rectilinear region
	 * @return the rectilinear region constructed by the set of non-overlapping rectangles
	 */
	public static final RectilinearRegion of(Set<Rectangle> rectangles) {
		Objects.requireNonNull(rectangles, "set of rectangles cannot be null");
		rectangles.forEach((rect) -> {
			rect.validate();
		});
		RectilinearRegion rr = new RectilinearRegion(rectangles);
		if (rr.isOverlapping()) {
			throw new IllegalArgumentException("Set of rectangles cannot intercept");
		}
		return rr;
	}
}