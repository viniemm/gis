package xgis;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import java.math.BigDecimal;

/**
 * InterestPoint Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Nov 21, 2021</pre>
 */
public class InterestPointTest {

	/**
	 * Before.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void before() throws Exception {
	}

	/**
	 * After.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void after() throws Exception {
	}

	/**
	 * Method: toString()
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testToString() throws Exception {
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(3));
		InterestPoint<String> ip = new InterestPoint<>(c1, "School");
		assertEquals(ip.toString(), "(2,3) : School");
	}

	/**
	 * Method: validate()
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testValidate() throws Exception {
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(3));
		InterestPoint<String> ip1 = new InterestPoint<>(c1, "School");
		assertEquals(ip1, ip1.validate());

		Coordinate c2 = new Coordinate(null, null);
		InterestPoint<String> ip2 = new InterestPoint<>(c2, "School");
		assertThrows(NullPointerException.class, ip2::validate);

		InterestPoint<String> ip3 = new InterestPoint<>(c1, null);
		assertThrows(NullPointerException.class, ip2::validate);
	}

	/**
	 * Method: validate(InterestPoint interestpoint)
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testValidateInterestPoint() throws Exception {
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(3));
		InterestPoint<String> ip1 = new InterestPoint<>(c1, "School");
		assertEquals(ip1, InterestPoint.validate(ip1));

		Coordinate c2 = new Coordinate(null, null);
		InterestPoint<String> ip2 = new InterestPoint<>(c2, "School");
		assertThrows(NullPointerException.class, () -> InterestPoint.validate(ip2));

		InterestPoint<String> ip3 = new InterestPoint<>(c1, null);
		assertThrows(NullPointerException.class, () -> InterestPoint.validate(ip3));
	}

	/**
	 * Method: hasMarker(M marker)
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testHasMarker() throws Exception {
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(3));
		InterestPoint<String> ip1 = new InterestPoint<>(c1, "School");
		assertTrue(ip1.hasMarker("School"));
		assertFalse(ip1.hasMarker("Office"));
		assertThrows(NullPointerException.class, () -> ip1.hasMarker(null));
	}

	/**
	 * Method: coordinate()
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCoordinate() throws Exception {
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(3));
		InterestPoint<String> ip1 = new InterestPoint<>(c1, "School");
		assertEquals(c1, ip1.coordinate());
	}

	/**
	 * Method: marker()
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testMarker() throws Exception {
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(3));
		InterestPoint<String> ip1 = new InterestPoint<>(c1, "School");
		assertEquals("School", ip1.marker());
	}
} 
