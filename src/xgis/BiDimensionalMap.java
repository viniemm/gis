package xgis;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The type Bi dimensionalMap is a data type that stores a collection of generic type in a 2 dimensional cartesian plane.
 * This is the primary data-type we will use it to store points in our GIS.
 *
 * @param <T> generic object to be stored in the collection.
 * @author Vinayak Mathur vxm167
 */
public final class BiDimensionalMap<T> {


	private final SortedMap<BigDecimal, SortedMap<BigDecimal, Collection<T>>> points = new TreeMap<>();

	/**
	 * Instantiates a new Bi dimensional map using the given collection of x and y coordinates.
	 * A point is placed on every combination of these coordinates.
	 * The total number of points will be the product of the number of x coordinates and y coordinates.
	 *
	 * @param xCoord the collection of x coordinates.
	 * @param yCoord the collection of y coordinates.
	 */
	BiDimensionalMap(Collection<BigDecimal> xCoord, Collection<BigDecimal> yCoord) {
		Objects.requireNonNull(xCoord, "xCoord cannot be null");
		Objects.requireNonNull(yCoord, "yCoord cannot be null");
		Updater up = new Updater();
		for (BigDecimal x : xCoord) {
			for (BigDecimal y : yCoord) {
				up.setCoordinate(new Coordinate(x, y));
				up.add();
			}
		}
	}

	/**
	 * Instantiates a new empty Bi dimensional map.
	 */
	BiDimensionalMap() {
	}

	/**
	 * Returns the collection at the given coordinate of x and y values
	 *
	 * @param x x value
	 * @param y y value
	 * @return the collection at (x,y)
	 */
	public final Collection<T> get(BigDecimal x, BigDecimal y) {
		Objects.requireNonNull(x, "value of x cannot be null");
		Objects.requireNonNull(y, "value of y cannot be null");
		return points.get(x).get(y);
	}

	/**
	 * Returns the collection at the given coordinate.
	 *
	 * @param coordinate the coordinate
	 * @return the collection at the provided coordinate.
	 */
	public final Collection<T> get(Coordinate coordinate) {
		Objects.requireNonNull(coordinate, "Coordinate cannot be null");
		return get(coordinate.x(), coordinate.y());
	}

	/**
	 * xSet returns the collection of x coordinates in the map
	 *
	 * @return the set of all x coordinates
	 */
	public final Set<BigDecimal> xSet() {
		return points.keySet();
	}

	/**
	 * ySet returns  the  set  of  y  coordinates  corresponding  to  the
	 * given value of x (or an empty set if no such y value exists)
	 *
	 * @param x the x
	 * @return the set of all y coordinates at x.
	 */
	public final Set<BigDecimal> ySet(BigDecimal x) {
		Objects.requireNonNull(x, "x cannot be null");
		if (!points.containsKey(x)) {
			throw new IllegalArgumentException("x is not in the map");
		}
		return points.get(x).keySet();
	}

	/**
	 * Returns the list of coordinates sorted by their compareTo.
	 *
	 * @return the list of coordinates.
	 */
	public final List<Coordinate> coordinateSet() {
		List<Coordinate> result = new ArrayList<>();
		Set<BigDecimal> xKeys = xSet();
		for (BigDecimal xKey : xKeys) {
			Set<BigDecimal> yKeys = ySet(xKey);
			for (BigDecimal yKey : yKeys) {
				Coordinate c = new Coordinate(xKey, yKey);
				result.add(c);
			}
		}
		result.sort(Coordinate::compareTo);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		Set<BigDecimal> xSet = this.xSet();
		for (BigDecimal x : xSet) {
			Set<BigDecimal> ySet = this.ySet(x);
			for (BigDecimal y : ySet) {
				result.append("(");
				result.append(x.toPlainString());
				result.append(",");
				result.append(y.toPlainString());
				result.append("):");
				result.append(points.get(x).get(y).toString());
				result.append("\n");
			}
		}
		return result.toString();
	}

	/**
	 * Helper method that returns the entire collection
	 *
	 * @return collection of all points
	 */
//	public final List<Collection<T>> collectionList() {
//
//	}

	/**
	 * Helper method that returns the entire collection
	 *
	 * @return collection of all points
	 */
	Collection<InterestPoint<T>> fullCollection() {
		Collection<InterestPoint<T>> result = new ArrayList<>();
		Set<BigDecimal> xKeys = xSet();
		xSet().forEach(x -> {
			ySet(x).forEach(y -> {
				points.get(x).get(y).forEach(marker -> {
					result.add(new InterestPoint<T>(new Coordinate(x, y), marker));
				});
			});
		});
		return result;
	}

	/**
	 * Returns the number of elements in this collection.
	 * If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this collection
	 */
	public final long collectionSize() {
		Collection<InterestPoint<T>> fullC = fullCollection();
		return fullC.size();
	}

	/**
	 * Returns the number of entries that are stored throughout the map and that satisfy the given predicate.
	 * If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @param filter the predicate
	 * @return number of entries that satisfy the predicate
	 */
	public final long collectionSize(Predicate<? super T> filter) {
		Objects.requireNonNull(filter, "filter cannot be null");
		Collection<InterestPoint<T>> fullC = fullCollection();
		AtomicLong result = new AtomicLong();
		int i = 0;
		List<InterestPoint<T>> arr = new ArrayList<>();
		fullC.forEach((ip) -> {
			arr.add(ip);
			if (arr.stream().anyMatch((e) -> e.marker().equals(filter))) {
				result.addAndGet(1);
			}
			arr.clear();
		});
//		for (InterestPoint<T> ip : fullC) {
//			arr.add(ip);
//			if (arr.stream().anyMatch((e) -> e.marker().equals(filter))) {
//				result++;
//			}
//			arr.clear();
//		}
		return result.get();
	}

	/**
	 * Returns an instance of BiDimensionalMap.Updater
	 *
	 * @return the updater
	 */
	public final Updater getUpdater() {
		return new Updater();
	}

	private final void compareInner(BigDecimal x, BigDecimal y, Rectangle rectangle, Updater up) {
		if (y.compareTo(rectangle.bottom()) >= 0 &&
			x.compareTo(rectangle.top()) < 0) {
			up.setCoordinate(new Coordinate(x, y)).setValues(points.get(x).get(y)).add();
		}
	}

	/**
	 * Slice bi dimensional map.
	 *
	 * @param rectangle the rectangle
	 * @return BiDimensionalMap
	 */
	public final BiDimensionalMap<T> slice(Rectangle rectangle) {
		Objects.requireNonNull(rectangle, "rectangle cannot be null");
		BiDimensionalMap<T> result = new BiDimensionalMap<>();
		Updater up = result.getUpdater();
		for (Coordinate c : coordinateSet()) {
			if (rectangle.inside(c)) {
				up.setCoordinate(c);
				up.setValues(get(c));
				up.add();
			}
		}
		System.out.println("Heres the slice\n" + result.toString());
		return result;
	}

	/**
	 * Add everywhere.
	 *
	 * @param value the value
	 */
	public final void addEverywhere(T value) {
		Objects.requireNonNull(value, "value cannot be null");
		Updater up = new Updater();
		up.addValue(value);
		for (BigDecimal x : xSet()) {
			for (BigDecimal y : ySet(x)) {
				up.setCoordinate(new Coordinate(x, y));
				up.add();
			}
		}
	}

	/**
	 * The type Updater.
	 */
	public final class Updater {

		private BigDecimal x = BigDecimal.ZERO;
		private BigDecimal y = BigDecimal.ZERO;

		private final Supplier<Collection<T>> collectionFactory = HashSet::new;

		private Collection<T> values = collectionFactory.get();

		/**
		 * Sets x.
		 *
		 * @param x the x
		 * @return the x
		 */
		public final Updater setX(BigDecimal x) {
			Objects.requireNonNull(x, "x cannot be null");
			this.x = x;
			return this;
		}

		/**
		 * Sets y.
		 *
		 * @param y the y
		 * @return the y
		 */
		public final Updater setY(BigDecimal y) {
			Objects.requireNonNull(y, "y cannot be null");
			this.y = y;
			return this;
		}

		/**
		 * Sets coordinate.
		 *
		 * @param coordinate the coordinate
		 * @return the coordinate
		 */
		public final Updater setCoordinate(Coordinate coordinate) {
			Objects.requireNonNull(coordinate, "Coordinate cannot be null");
			coordinate.validate();
			x = coordinate.x();
			y = coordinate.y();
			return this;
		}


		/**
		 * Add value updater.
		 *
		 * @param value the value
		 * @return the updater
		 */
		public final Updater addValue(T value) {
			Objects.requireNonNull(value, "value cannot be null");
			values.add(value);
			return this;
		}

		/**
		 * Sets values.
		 *
		 * @param values the values
		 * @return the values
		 */
		public final Updater setValues(Collection<T> values) {
			Objects.requireNonNull(values, "new values cannot be null");
			this.values = values;
			return this;
		}

		/**
		 * Set collection.
		 *
		 * @return the collection
		 */
		public final Collection<T> set() {
			if (points.containsKey(x)) {
				SortedMap<BigDecimal, Collection<T>> temp = points.get(x);
				if (temp.containsKey(y)) {
					temp.put(y, values);
					points.put(x, temp);
					return temp.get(y);
				}
			}

			return null;
		}

		/**
		 * Add boolean.
		 *
		 * @return the boolean
		 */
		public final boolean add() {
			boolean flag = false;
			if (points.containsKey(x)) {
				if (points.get(x).containsKey(y)) {
					Collection<T> prevValues = points.get(x).get(y);
					points.get(x).get(y).addAll(values);
					Collection<T> newValues = points.get(x).get(y);
					flag = !newValues.equals(prevValues);
				} else {
					points.get(x).put(y, collectionFactory.get());
					points.get(x).get(y).addAll(values);
				}
			} else {
				SortedMap<BigDecimal, Collection<T>> s = new TreeMap<>();
				Collection<T> newValues = collectionFactory.get();
				newValues.addAll(values);
				s.put(y, newValues);
				points.put(x, s);
			}
			return flag;
		}
	}
}
