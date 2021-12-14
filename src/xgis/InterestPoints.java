package xgis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * The public  final  class  InterestPoints stores a set of
 * InterestPoints in the private final
 * BiDimensionalMap points.
 *
 * @param <M> The type of object Marker.
 */
public class InterestPoints<M> {

	private final BiDimensionalMap<InterestPoint<M>> points;

	private InterestPoints(Builder<M> builder) {
		this.points = builder.points;
	}

	/**
	 * Returns the collection of InterestPoint at the given coordinate.
	 * If there is no InterestPoint at the given coordinate it returns null.
	 *
	 * @param coordinate the coordinate
	 * @return the collection of interest points
	 */
	public final Collection<InterestPoint<M>> get(Coordinate coordinate) {
		Objects.requireNonNull(coordinate);
		try {
			return points.get(coordinate);
		} catch (NullPointerException e) {
			System.out.println("No collection found at " + coordinate.toSimpleString());
			return null;
		}
	}

	/**
	 * Returns all the interest points in the form of a list of collections.
	 *
	 * @return a list of collections of interest points.
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
	 * Returns the number of interest points that are present in the given rectilinear region and have the given marker in them.
	 *
	 * @param region the rectilinear region
	 * @param marker the marker
	 * @return the number of points that obey the criteria.
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
		return points.toString();
	}

	/**
	 * The type Builder.
	 *
	 * @param <M> the type parameter
	 */
	public static class Builder<M> {
		private final BiDimensionalMap<InterestPoint<M>> points = new BiDimensionalMap<>();

		/**
		 * Adds the given interest point.
		 *
		 * @param interestPoint the interest point to be added to the BiDimensionalMap points.
		 * @return true if successful or throws an exception.
		 */
		public final boolean add(InterestPoint<M> interestPoint) {
			interestPoint.validate();
			BiDimensionalMap<M>.Updater up = (BiDimensionalMap<M>.Updater) points.getUpdater();
			up.setCoordinate(interestPoint.coordinate());
			up.addValue(interestPoint.marker());
			up.add();
			return true;
		}

		/**
		 * Adds all the given interest points in the collection.
		 *
		 * @param interestPoints the collection of interest points to be added to the BiDimensionalMap points.
		 * @return true if successful or throws an exception.
		 */
		final boolean addAll(Collection<InterestPoint<M>> interestPoints) {
			for (InterestPoint<M> ip : interestPoints) {
				add(ip);
			}
			return true;
		}

		/**
		 * Build interest points and return an instance of this InterestPoints.
		 *
		 * @return this instance of InterestPoints.
		 */
		public final InterestPoints<M> build() {
			return new InterestPoints<M>(this);
		}
	}
}
