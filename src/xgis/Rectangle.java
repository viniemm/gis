package xgis;

import java.math.BigDecimal;
import java.util.*;

/**
 * The type Rectangle is a record class that accepts a bottom left amd top right coordinate and constructs a rectangle.
 */
public record Rectangle(Coordinate bottomLeft, Coordinate topRight) {

	/**
	 * Validates this rectangle and throws an exception if either the bottom left or top right coordinates are invalid.
	 * Also throws an exception if bottom left >= top right.
	 *
	 * @return the rectangle
	 */
	public final Rectangle validate() {
		Coordinate.validate(bottomLeft);
		Coordinate.validate(topRight);

		int xVal = bottomLeft.x().compareTo(topRight.x());
		int yVal = bottomLeft.y().compareTo(topRight.y());
		if (xVal <= 0 && yVal < 0) {
			return this;
		}
		throw new IllegalArgumentException("Illegal values of bottom left and top right");
	}

	/**
	 * Checks whether the given argument coordinate lies within/inside this rectangle.
	 *
	 * @param coordinate the coordinate to be checked.
	 * @return true if coordinate is inside this rectangle else false
	 */
	boolean inside(Coordinate coordinate) {
		return coordinate.compareTo(bottomLeft) + coordinate.compareTo(topRight) == 0;
	}

	/**
	 * Validates the given argument rectangle.
	 *
	 * @param rectangle the rectangle to be validated.
	 * @return the rectangle
	 */
	public static final Rectangle validate(Rectangle rectangle) {
		Objects.requireNonNull(rectangle, "Argument is null");
		return rectangle.validate();
	}

	/**
	 * Returns the value of the left abscissa of this rectangle.
	 *
	 * @return the x value of bottomLeft.
	 */
	public final BigDecimal left() {
		return bottomLeft.x();
	}

	/**
	 * Returns the value of the right abscissa of this rectangle.
	 *
	 * @return the x value of topRight
	 */
	public final BigDecimal right() {
		return topRight.x();
	}

	/**
	 * Returns the value of the top ordinate of this rectangle.
	 *
	 * @return the y value of topRight
	 */
	public final BigDecimal top() {
		return topRight.y();
	}

	/**
	 * Returns the value of the bottom ordinate of this rectangle.
	 *
	 * @return the y value of bottomLeft
	 */
	public final BigDecimal bottom() {
		return bottomLeft.y();
	}

	/**
	 * Returns the bottom right coordinate of this rectangle.
	 *
	 * @return the coordinate bottomRight
	 */
	Coordinate bottomRight() {
		return new Coordinate(right(), bottom());
	}

	/**
	 * Returns the top left coordinate of this rectangle.
	 *
	 * @return the coordinate topLeft
	 */
	Coordinate topLeft() {
		return new Coordinate(left(), top());
	}

	/**
	 * Returns a set of all corners of this rectangle in the form of Coordinate.
	 *
	 * @return the set of corners
	 */
	public final Set<Coordinate> allCorners() {
		Set<Coordinate> result = new HashSet<>();
		result.add(bottomLeft());
		result.add(bottomRight());
		result.add(topRight());
		result.add(topLeft());
		return result;
	}

	/**
	 * Returns a set of all sides of this rectangle in the form of Line.
	 *
	 * @return the set of sides
	 */
	final Set<Line> allSides() {
		Set<Line> result = new HashSet<>();
		result.add(new Line(bottomLeft(), bottomRight()));
		result.add(new Line(topLeft(), topRight()));
		result.add(new Line(bottomLeft(), topLeft()));
		result.add(new Line(bottomRight(), topRight()));
		return result;
	}

	@Override
	public String toString() {
		return topLeft().toSimpleString() + "\t" + this.topRight().toSimpleString() + "\n" +
			this.bottomLeft().toSimpleString() + "\t" + bottomRight().toSimpleString();
	}
}