package xgis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Class InterestPoints stores a collection of many Points.
 *
 * @param <M> The type of object Marker.
 */
public class InterestPoints<M> {

	private final BiDimensionalMap<InterestPoint<M>> points;

	private InterestPoints(Builder<M> builder) {
		this.points = builder.points;
	}

	/**
	 * Returns the InterestPoint .
	 *
	 * @param coordinate the coordinate
	 * @return the collection
	 */
	public final Collection<InterestPoint<M>> get(Coordinate coordinate) {
		Objects.requireNonNull(coordinate);
		try {
			return points.get(coordinate);
		} catch (NullPointerException e) {
			System.out.println("The value of either x or y in the coordinate is null");
			return null;
		}
	}

	/**
	 * Interest points list.
	 *
	 * @return the list
	 */
	public final List<Collection<InterestPoint<M>>> interestPoints() {
		List<Coordinate> sortedCoordinates = points.coordinateSet();
		List<Collection<InterestPoint<M>>> result = new ArrayList<>();
		for (Coordinate c : sortedCoordinates) {
			result.add(get(c));
		}
		return result;
	}

	/**
	 * Count long.
	 *
	 * @param region the region
	 * @param marker the marker
	 * @return the long
	 */
	public final long count(RectilinearRegion region, M marker) {
		long result = 0;
		Predicate<InterestPoint<M>> predicate = x -> x.hasMarker(marker);

		for (Rectangle rectangle : region.getRectangles()) {
			BiDimensionalMap<InterestPoint<M>> bd = points.slice(rectangle);
			result += bd.collectionSize(predicate);
		}
		return result;
	}

	@Override
	public String toString() {
		return "InterestPoints{" +
			"points=" + points +
			'}';
	}

	/**
	 * The type Builder.
	 *
	 * @param <M> the type parameter
	 */
	public static class Builder<M> {
		private final BiDimensionalMap<InterestPoint<M>> points = new BiDimensionalMap<>();

		/**
		 * Add boolean.
		 *
		 * @param interestPoint the interest point
		 * @return the boolean
		 */
		public final boolean add(InterestPoint<M> interestPoint) {
			interestPoint.validate();
			BiDimensionalMap<M>.Updater up = (BiDimensionalMap<M>.Updater) points.getUpdater();
			up.setCoordinate(interestPoint.coordinate());
			up.addValue(interestPoint.marker());
			up.add();
			return true;
		}

		public final boolean addAll(Collection<InterestPoint<M>> interestPoints) {
			for (InterestPoint<M> ip : interestPoints) {
				if (!add(ip)) {
					return false;
				}
			}
			return true;
		}

		/**
		 * Build interest points.
		 *
		 * @return the interest points
		 */
		public final InterestPoints<M> build() {
			return new InterestPoints<M>(this);
		}
	}
}
