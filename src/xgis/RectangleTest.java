package xgis.test;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import xgis.Rectangle;
import xgis.Coordinate;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Rectangle Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Dec 1, 2021</pre>
 */
public class RectangleTest {
	BigDecimal l = new BigDecimal(3);
	BigDecimal r = new BigDecimal(9);
	BigDecimal b = new BigDecimal(2);
	BigDecimal t = new BigDecimal(5);
	Coordinate bl = new Coordinate(l, b);
	Coordinate tr = new Coordinate(r, t);
	Coordinate br = new Coordinate(r, b);
	Coordinate tl = new Coordinate(l, t);
	Rectangle rect = Rectangle.validate(new Rectangle(bl, tr));

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * Method: validate()
	 */
	@Test
	public void testValidate() throws Exception {
		Coordinate bl1 = new Coordinate(new BigDecimal(3), new BigDecimal(2));
		Coordinate tr1 = new Coordinate(new BigDecimal(9), new BigDecimal(5));
		Rectangle rect1 = new Rectangle(tr1, bl1);
		assertThrows(IllegalArgumentException.class, rect1::validate);
		Coordinate tr2 = new Coordinate(new BigDecimal(3), new BigDecimal(2));
		Rectangle rect2 = new Rectangle(bl1, tr2);
		assertThrows(IllegalArgumentException.class, rect2::validate);
		Rectangle rect3 = new Rectangle(bl1, tr1);
		assertEquals(rect3, rect3.validate());
	}

	/**
	 * Method: validate(Rectangle rectangle)
	 */
	@Test
	public void testValidateRectangle() throws Exception {
		Coordinate bl1 = new Coordinate(new BigDecimal(3), new BigDecimal(2));
		Coordinate tr1 = new Coordinate(new BigDecimal(9), new BigDecimal(5));
		Rectangle rect1 = new Rectangle(tr1, bl1);
		assertThrows(IllegalArgumentException.class, () -> Rectangle.validate(rect1));
		Coordinate tr2 = new Coordinate(new BigDecimal(3), new BigDecimal(2));
		Rectangle rect2 = new Rectangle(bl1, tr2);
		assertThrows(IllegalArgumentException.class, () -> Rectangle.validate(rect2));
		Rectangle rect3 = new Rectangle(bl1, tr1);
		assertEquals(rect3, Rectangle.validate(rect3));
	}

	/**
	 * Method: left()
	 */
	@Test
	public void testLeftRightTopBottom() throws Exception {
		assertEquals(rect.left(), l);
		assertEquals(rect.right(), r);
		assertEquals(rect.top(), t);
		assertEquals(rect.bottom(), b);
	}

	/**
	 * Method: allCorners()
	 */
	@Test
	public void testAllCorners() throws Exception {
		Set<Coordinate> result = new HashSet<>();
		result.add(bl);
		result.add(br);
		result.add(tr);
		result.add(tl);
		Rectangle rect = Rectangle.validate(new Rectangle(bl, tr));
		assertEquals(result, rect.allCorners());
	}

	/**
	 * Method: toString()
	 */
	@Test
	public void testToString() throws Exception {
		String s = "(3,5)\t(9,5)\n(3,2)\t(9,2)";
		assertEquals(s, rect.toString());
	}

	/**
	 * Method: bottomLeft()
	 */
	@Test
	public void testBottomLeft() throws Exception {
		assertEquals(bl, rect.bottomLeft());
	}

	/**
	 * Method: topRight()
	 */
	@Test
	public void testTopRight() throws Exception {
		assertEquals(tr, rect.topRight());
	}
} 
