package View;

/**
 * Corner is used to define the graphical images displayed as backgrounds on the map.<br>
 * An area of 3 x 3 squares on water will have 9 different images (1 for the middle, 4 for the sides and 4 for the actual corners).<br>
 * The 4 cardinal points are used to symbolize the corners. (N = North, E = East,...)
 */
public enum Corner {
	NONE,
	N,
	E,
	W,
	S,
	NE,
	NW,
	SE,
	SW,
	NES,
	ESW,
	SWN,
	WNE;
	
	/**
	 * From a list of boolean, it generate the corresponding corner.<br>
	 * e.g : up and right gives NE (north-east)
	 * @param up : boolean
	 * @param down : boolean
	 * @param left : boolean
	 * @param right : boolean
	 * @return corresponding Corner
	 */
	public static Corner getCorner(boolean up, boolean down, boolean left, boolean right) {
		if(up && right && down)
			return Corner.NES;
		else if(right && down && left)
			return Corner.ESW;
		else if(down && left && up)
			return Corner.SWN;
		else if(left && up && right)
			return Corner.WNE;
		else if(up && left)
			return Corner.NW;
		else if(up && right) 
			return Corner.NE;
		else if(up)
			return Corner.N;
		else if(down && left)
			return Corner.SW;
		else if(down && right)
			return Corner.SE;
		else if(down)
			return Corner.S;
		else if(left)
			return Corner.W;
		else if(right)
			return Corner.E;
		else
			return null;
	}
}


