 package model;
 
/**
 * 2-dimensional coordinates of an entity in the world. 
 */
public class Coord implements Comparable<Coord>{
	private int x;
	private int y;
	
	public Coord() { }
	
	public Coord(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	/**
	 * Check if the coord this is on the environment given in parameter
	 * @param env : Environment
	 * @return : boolean value
	 */
	public boolean isOnMap(Environment env) {
		return this.x >= 0 && this.x < env.getWidth() && this.y >= 0 && this.y < env.getHeight();
	}
	
	/* Getters and setters */
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Comparison between 2 coordinates.<br>
	 * In order, x is compared before y.
	 * @param o : Coord
	 * @return int : <li>-1 if this is "less" than o</li>
	 * 				 <li>0 if they are both equals</li>
	 * 				 <li>1 if this is greater than o</li>
	 */
	@Override
	public int compareTo(Coord o) {
		if(this.x < o.x) { 
			return -1;
		}
		else if(this.x > o.x){
			return 1;
		}
		else {
			if(this.y < o.y)
				return -1;
			else if(this.y > o.y)
				return 1;
			else
				return 0;
		}
	}
	
	/**
	 * Check if two coord are equals
	 * @param c
	 * @return boolean
	 */
	public boolean equals(Coord c) {
		return this.x == c.x && this.y == c.y;
	}
	
	/**
	 * Converts a Coord into a String in order to print or display it.
	 */
	public String toString() {
		return "(" + x + ";" + y + ")";
	}
	
	/**
	 * Create a new coord with the same attributes' values.
	 * @return a new Coord
	 */
	public Coord clone() {
		return new Coord(this.x, this.y);
	}
	
	/**
	 * Create a new Coord and assign it the coord of this plus the values of the parameter c
	 * @param c : a Coord
	 * @return the new Coord
	 */
	public Coord plus(Coord c) {
		return new Coord(this.x+c.x, this.y+c.y);
	}
	
	/**
	 * Create a new Coord and assign it the coord of this, on which the values of the parameter c have been substracted.
	 * @param c : a Coord
	 * @return the new Coord
	 */
	public Coord minus(Coord c) {
		return new Coord(this.x-c.x, this.y-c.y);
	}
	
	/**
	 * Compute the distance between two coordinates in term of movement needed.
	 * @param c : a Coord
	 * @return int : number of square needed to go from this to c
	 */
	public int distanceTo(Coord c){
		Coord tmp = minus(c);
		return Math.abs(tmp.getX())+Math.abs(tmp.getY());
	}
	
	/**
	 * Give the direction that needs to be taken to go from this to c
	 * @param c : Coord of the destination we are looking at
	 * @return a Movement representing the direction
	 */
	public Movement getDirectionTo(Coord c) {
		if(x < c.x)
			return Movement.Right;
		else if(x > c.x)
			return Movement.Left;
		else {
			if(y > c.y)
				return Movement.Up;
			else
				return Movement.Down;
		}
	}
	
	/**
	 * Give the coordinate of the next position if we move in a specific direction
	 * @param m : Movement (direction)
	 * @return a new Coord object
	 */
	public Coord plus(Movement m) {
		Coord c = this.clone();
		switch(m) {
		case Up:
			c.y--;
			break;
		case Down:
			c.y++;
			break;
		case Left:
			c.x--;
			break;
		case Right:
			c.x++;
			break;
		}
		return c;
	}
	
}
