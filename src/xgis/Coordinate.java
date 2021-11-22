package xgis;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The type Coordinate.
 *
 * @author Vinayak Mathur vxm167 Coordinate is a record class that stores the x and y values for a given coordinate.
 */
public record Coordinate(BigDecimal x, BigDecimal y) implements Comparable<Coordinate> {

	/**
	 * The constant ORIGIN.
	 */
	public static final Coordinate ORIGIN = new Coordinate(BigDecimal.ZERO, BigDecimal.ZERO);

	/**
	 * Makes sure that the x and y values of the coordinate are non-null. Throws an exception is either is.
	 *
	 * @return this class.
	 */
	public final Coordinate validate() {
		if (Objects.isNull(x) || Objects.isNull(y)) {
			throw new NullPointerException("The values of x or y cannot be null");
		}
		return this;
	}

	/**
	 * Accepts an object of class Coordinate and validate that coordinate.
	 *
	 * @param coordinate is the coordinate to be validated.
	 * @return coordinate
	 */
	public static final Coordinate validate(Coordinate coordinate) {
		Objects.requireNonNull(coordinate, "Coordinate cannot be null");
		return coordinate.validate();
	}

	private int compareX(Coordinate c) {
		return x.compareTo(c.x());
	}

	private int compareY(Coordinate c) {
		return y.compareTo(c.y());
	}

	/**
	 * Compares this Coordinate with the specified Coordinate.
	 * This Coordinate is considered greater to another Coordinate if both x and y values are greater.
	 * This Coordinate is considered lesser to another Coordinate if both x and y values are lesser.
	 * Two Coordinates are considered equal if both x and y values are equal.
	 * Two Coordinate objects that are not equal in value but are neither greater nor lesser are still considered equal are considered equal by this method.
	 * This method is provided in preference to individual methods for each of the six boolean comparison operators (<, ==, >, >=, !=, <=).
	 * The suggested idiom for performing these comparisons is: (x.compareTo(y) <op> 0), where <op> is one of the six comparison operators.
	 *
	 * @param c Coordinate  to which this Coordinate is to be compared.
	 * @return -1, 0, or 1 as this Coordinate is geographically less than, equal to, or greater than c.
	 */
	@Override
	public int compareTo(Coordinate c) {
		try {
			return (int) (compareX(c) + compareY(c)) / Math.abs((compareX(c) + compareY(c)));
		} catch (ArithmeticException e) {
			return 0;
		}
	}

	/**
	 * Returns a simple string representation of this record class. The representation contains the x and y values of the coordinate.
	 *
	 * @return a simple string representation of this object
	 */
	public String toSimpleString() {
		String xs = this.x.toPlainString();
		String ys = this.y.toPlainString();
		return "(" + xs + "," + ys + ")";
	}
}
