package xgis;

import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

/**
 * Coordinate Tester.
 *
 * @author <Vinayak Mathur>
 * @version 1.0
 * @since <pre>Nov 19, 2021</pre>
 */
public class CoordinateTest {
	/**
	 * Method: validate()
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testValidateNull() throws Exception {
		Coordinate c1 = new Coordinate(null, null);
		assertThrows(NullPointerException.class, c1::validate);
		Coordinate c2 = new Coordinate(new BigDecimal(2), null);
		assertThrows(NullPointerException.class, c2::validate);
	}

	/**
	 * Test validate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testValidate() throws Exception {
		Coordinate c = new Coordinate(new BigDecimal(2), new BigDecimal(2));
		assertEquals(c, c.validate());
	}

	/**
	 * Method: validate(Coordinate coordinate)
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testValidateCoordinateNull() throws Exception {
		Coordinate c = new Coordinate(new BigDecimal(4), null);
		assertThrows(NullPointerException.class, () -> {
			Coordinate.validate(c);
		});
		assertThrows(NullPointerException.class, () -> {
			Coordinate.validate(null);
		});
	}

	/**
	 * Test validate coordinate.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testValidateCoordinate() throws Exception {
		Coordinate c = new Coordinate(new BigDecimal(4), new BigDecimal(3));
		assertEquals(c, Coordinate.validate(c));
	}

	/**
	 * Method: compareTo(Coordinate c)
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCompareTo() throws Exception {
		Coordinate c1 = new Coordinate(new BigDecimal(2), new BigDecimal(2));
		Coordinate c2 = new Coordinate(new BigDecimal(2), new BigDecimal(2));
		assertEquals(c1.compareTo(c2), 0);

		Coordinate c3 = new Coordinate(new BigDecimal(3), new BigDecimal(3));
		assertEquals(c1.compareTo(c3), -1);
		assertEquals(c3.compareTo(c1), 1);

		Coordinate c4 = new Coordinate(new BigDecimal(1), new BigDecimal(3));
		assertEquals(c1.compareTo(c4), 0);
	}

	/**
	 * Method: toSimpleString()
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testToSimpleString() throws Exception {
		Coordinate c = new Coordinate(new BigDecimal(3), new BigDecimal(7));
		assertEquals("(3,7)", c.toSimpleString());
	}
}
