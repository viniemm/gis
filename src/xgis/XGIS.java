package xgis;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Xgis.
 */
public class XGIS extends RectilinearRegion{

	/**
	 * Instantiates a new Xgis.
	 *
	 * @param rectangles the rectangles
	 */
	public XGIS(Set<Rectangle> rectangles){
		super(rectangles);
	}

	/**
	 * Is connected boolean.
	 *
	 * @return the boolean
	 */
	public boolean isConnected() {
		return checkVertical() || checkHorizontal();
	}

	private boolean checkVertical(){
		Set<BigDecimal> vert = new HashSet<>();
		for (Rectangle rect : getRectangles()){
			if (vert.contains(rect.left()) || vert.contains(rect.right())){
				return true;
			}
			vert.add(rect.right());
			vert.add(rect.left());
		}
		return false;
	}

	private boolean checkHorizontal(){
		Set<BigDecimal> hori = new HashSet<>();
		for (Rectangle rect : getRectangles()){
			if (hori.contains(rect.top()) || hori.contains(rect.bottom())){
				return true;
			}
			hori.add(rect.top());
			hori.add(rect.bottom());
		}
		return false;
	}

}
