package View;

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


