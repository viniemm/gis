package xgis;

import org.junit.Test;

import java.util.*;
import java.math.BigDecimal;

import static org.junit.Assert.*;


/**
 * InterestPoints Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 2, 2021</pre>
 */
public class InterestPointsTest {
	/**
	 * Method: get(Coordinate coordinate)
	 */
	@Test
	public void testGet() throws Exception {
		InterestPoints.Builder<String> build = helper1();
		build.addAll(interestPoints);
		InterestPoints<String> built = build.build();
		int[] randX = new Random().ints(20, -7, 8).toArray();
		int[] randY = new Random().ints(20, -7, 8).toArray();
		for (int i = 0; i < 20; i++) {
			built.get(new Coordinate(new BigDecimal(randX[i]), new BigDecimal(randY[i])));
		}
	}

	/**
	 * Method: interestPoints()
	 */
	@Test
	public void testInterestPoints() throws Exception {
		InterestPoints.Builder<String> build = helper1();
		build.addAll(interestPoints);
		InterestPoints<String> built = build.build();
		built.interestPoints();
	}

	private Set<Rectangle> helper2() {
		Rectangle rect = new Rectangle(
			new Coordinate(
				new BigDecimal(-30), new BigDecimal(-30)),
			new Coordinate(
				new BigDecimal(30), new BigDecimal(30)));
		Set<Rectangle> res = new HashSet<>();
		res.add(rect);
		return res;
	}

	/**
	 * Method: count(RectilinearRegion region, M marker)
	 */
	@Test
	public void testCount() throws Exception {
//		TODO: Error for some reason?
		InterestPoints.Builder<String> build = helper1();
		build.addAll(interestPoints);
		InterestPoints<String> built = build.build();
		System.out.println(built.toString());
		assertEquals( 2, built.count(new RectilinearRegion(helper2()), "School"));
	}

	/**
	 * Method: toString()
	 */
	@Test
	public void testToString() throws Exception {
		InterestPoints.Builder<String> build = helper1();
		build.addAll(interestPoints);
		InterestPoints<String> built = build.build();
		System.out.println(built.toString());
	}

	List<InterestPoint<String>> interestPoints;

	private InterestPoints.Builder<String> helper1() {
		InterestPoints.Builder<String> build = new InterestPoints.Builder<>();
		List<InterestPoint<String>> ips = new ArrayList<>();
		ips.add(new InterestPoint<>(new Coordinate(new BigDecimal(-7), new BigDecimal(4)), "School"));
		ips.add(new InterestPoint<>(new Coordinate(new BigDecimal(5), new BigDecimal(2)), "School"));
		ips.add(new InterestPoint<>(new Coordinate(new BigDecimal(5), new BigDecimal(2)), "Office"));
		ips.add(new InterestPoint<>(new Coordinate(new BigDecimal(-7), new BigDecimal(4)), "Home"));
		ips.add(new InterestPoint<>(new Coordinate(new BigDecimal(3), new BigDecimal(3)), "Playground"));
		ips.add(new InterestPoint<>(new Coordinate(new BigDecimal(-7), new BigDecimal(4)), "Police Station"));
		interestPoints = new ArrayList<>();
		interestPoints.addAll(ips);
		return build;
	}

	/**
	 * Method: add(InterestPoint<M> interestPoint)
	 */
	@Test
	public void testAdd() throws Exception {
		InterestPoints.Builder<String> ipb = helper1();
		assertTrue(ipb.addAll(interestPoints));
		InterestPoints<String> ip = ipb.build();
	}

	/**
	 * Method: build()
	 */
	@Test
	public void testBuild() throws Exception {
		InterestPoints<String> ips = helper1().build();
	}


} 
