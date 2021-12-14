package xgis;

import java.math.BigDecimal;

/**
 * Coordinate is a record class that stores the start and end coordinates for a given line
 */
record Line(Coordinate start, Coordinate end) {

	/**
	 * Determines whether the argument coordinate lies on this line.
	 *
	 * @param coordinate the coordinate to be checked
	 * @return true if the coordinate lies on this line else returns false.
	 */
	boolean in(Coordinate coordinate) {
		if (coordinate.compareTo(start) + coordinate.compareTo(end) == 0) {
			return coordinate.x().compareTo(start.x()) == 0 || coordinate.y().compareTo(start.y()) == 0;
		}
		return false;
	}
}
