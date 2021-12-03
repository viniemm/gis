package test.xgis;

import org.junit.Test;
import xgis.InterestPoints;
import xgis.InterestPoint;
import xgis.Coordinate;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;
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

	}

	/**
	 * Method: interestPoints()
	 */
	@Test
	public void testInterestPoints() throws Exception {
//TODO: Test goes here... 
	}

	/**
	 * Method: count(RectilinearRegion region, M marker)
	 */
	@Test
	public void testCount() throws Exception {

//TODO: Test goes here... 
	}

	/**
	 * Method: toString()
	 */
	@Test
	public void testToString() throws Exception {
//		System.out.println(helper1().)
	}

	List<InterestPoint<String>> interestPoints;

	private InterestPoints.Builder<String> helper1() {
		InterestPoints.Builder<String> build = new InterestPoints.Builder<>();
		//List<Coordinate> coordinates = new ArrayList<>();
		interestPoints = new ArrayList<>();
		int[] randX = new Random().ints(20, -7, 8).toArray();
		int[] randY = new Random().ints(20, -7, 8).toArray();
		String[] markers = {"School", "Police Station", "Home", "Office", "Playground", "Post Office"};
		for (int i = 0; i < 20; i++) {
			//coordinates.add(new Coordinate(new BigDecimal(randX[i]), new BigDecimal(randY[i])));
			InterestPoint<String> ip = new InterestPoint<>(new Coordinate(new BigDecimal(randX[i]), new BigDecimal(randY[i])), markers[i % 5]);
			interestPoints.add(ip);
		}
		return build;
	}

	/**
	 * Method: add(InterestPoint<M> interestPoint)
	 */
	@Test
	public void testAdd() throws Exception {
		assertTrue(helper1().addAll(interestPoints));
	}

	/**
	 * Method: build()
	 */
	@Test
	public void testBuild() throws Exception {
		InterestPoints<String> ips = helper1().build();
	}


} 
