package xgis;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * RectilinearRegion Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 5, 2021</pre>
 */
public class RectilinearRegionTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	RectilinearRegion rr() {
		XGISTest xgis = new XGISTest();
		return new RectilinearRegion(xgis.rectSet());
	}

	/**
	 * Method: isOverlapping()
	 */
	@Test
	public void testIsOverlapping() throws Exception {
		System.out.println(rr().isOverlapping());
	}

	/**
	 * Method: of(Set<Rectangle> rectangles)
	 */
	@Test
	public void testOf() throws Exception {
		RectilinearRegion rr = rr();
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(2));
		Coordinate c2 = new Coordinate(new BigDecimal(5), new BigDecimal(5));
		Coordinate c3 = new Coordinate(new BigDecimal(3), new BigDecimal(3));
		Coordinate c4 = new Coordinate(new BigDecimal(7), new BigDecimal(7));
		Rectangle r1 = new Rectangle(c1, c2);
		Rectangle r2 = new Rectangle(c3, c4);
		Rectangle r3 = new Rectangle(c1, c3);
		Rectangle r4 = new Rectangle(c2, c4);
		Set<Rectangle> rectangles1 = new HashSet<>();
		Set<Rectangle> rectangles2 = new HashSet<>();
		rectangles1.add(r1);
		rectangles1.add(r2);
		rectangles2.add(r3);
		rectangles2.add(r4);
		assertThrows(IllegalArgumentException.class, () -> RectilinearRegion.of(rectangles1));
		RectilinearRegion.of(rectangles2);
	}

	/**
	 * Method: rectangleMap()
	 */
	@Test
	public void testRectangleMap() throws Exception {
		rr().rectangleMap();
	}
}
