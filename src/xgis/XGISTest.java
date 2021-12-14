package xgis;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * XGIS Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 5, 2021</pre>
 */
public class XGISTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	Set<Rectangle> rectSet() {
		Rectangle rect1 = new Rectangle(
			new Coordinate(
				new BigDecimal(-5), new BigDecimal(-5)),
			new Coordinate(
				new BigDecimal(-3), new BigDecimal(-2)));
		Rectangle rect2 = new Rectangle(
			new Coordinate(
				new BigDecimal(3), new BigDecimal(2)),
			new Coordinate(
				new BigDecimal(4), new BigDecimal(5)));
		Rectangle rect3 = new Rectangle(
			new Coordinate(
				new BigDecimal(5), new BigDecimal(5)),
			new Coordinate(
				new BigDecimal(7), new BigDecimal(8)));
		Set<Rectangle> res = new HashSet<>();
		res.add(rect1);
		res.add(rect2);
		res.add(rect3);
		return res;
	}

	/**
	 * Method: isConnected()
	 */
	@Test
	public void testIsConnected1() throws Exception {
		Set<Rectangle> rectSet = rectSet();
		XGIS xgis = new XGIS(rectSet);
		assertFalse(xgis.isConnected());
	}

	/**
	 * Method: isConnected()
	 */
	@Test
	public void testIsConnected2() throws Exception {
		Set<Rectangle> rectSet = rectSet();
		rectSet.add(new Rectangle(
			new Coordinate(
				new BigDecimal(-4), new BigDecimal(-5)),
			new Coordinate(
				new BigDecimal(3), new BigDecimal(3))));
		XGIS xgis = new XGIS(new RectilinearRegion(rectSet));
		assertTrue(xgis.isConnected());
	}
} 
