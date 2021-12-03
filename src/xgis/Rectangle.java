package xgis;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Rectangle.
 */
public record Rectangle(Coordinate bottomLeft, Coordinate topRight) {

	/**
	 * Validate rectangle.
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
	 * Validate rectangle.
	 *
	 * @param rectangle the rectangle
	 * @return the rectangle
	 */
	public static final Rectangle validate(Rectangle rectangle) {
		Objects.requireNonNull(rectangle, "Argument is null");
		return rectangle.validate();
	}

	/**
	 * Left big decimal.
	 *
	 * @return the big decimal
	 */
	public final BigDecimal left() {
		return bottomLeft.x();
	}

	/**
	 * Right big decimal.
	 *
	 * @return the big decimal
	 */
	public final BigDecimal right() {
		return topRight.x();
	}

	/**
	 * Top big decimal.
	 *
	 * @return the big decimal
	 */
	public final BigDecimal top() {
		return topRight.y();
	}

	/**
	 * Bottom big decimal.
	 *
	 * @return the big decimal
	 */
	public final BigDecimal bottom() {
		return bottomLeft.y();
	}

	private Coordinate bottomRight() {
		return new Coordinate(right(), bottom());
	}

	private Coordinate topLeft() {
		return new Coordinate(left(), top());
	}

	/**
	 * All corners set.
	 *
	 * @return the set
	 */
	public final Set<Coordinate> allCorners() {
		Set<Coordinate> result = new HashSet<>();
		result.add(bottomLeft());
		result.add(bottomRight());
		result.add(topRight());
		result.add(topLeft());
		return result;
	}

	@Override
	public String toString() {
		return topLeft().toSimpleString() + "\t" + this.topRight().toSimpleString() + "\n" +
			this.bottomLeft().toSimpleString() + "\t" + bottomRight().toSimpleString();
	}
}