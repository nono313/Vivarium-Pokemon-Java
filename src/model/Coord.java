 package model;
public class Coord implements Comparable<Coord>{
	private int x;
	private int y;
	public Coord() {

	}
	public Coord(int x, int y){
		this.x=x;
		this.y=y;
	}
	public boolean isOnMap(Environment env) {
		return this.x >= 0 && this.x < env.getWidth() && this.y >= 0 && this.y < env.getHeight();
	}
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
	public boolean equals(Coord c) {
		return this.x == c.x && this.y == c.y;
	}
	public String toString() {
		return "(" + x + ";" + y + ")";
	}
	public Coord clone() {
		return new Coord(this.x, this.y);
	}
	public Coord plus(Coord c) {
		return new Coord(this.x+c.x, this.y+c.y);
	}
	public Coord minus(Coord c) {
		return new Coord(this.x-c.x, this.y-c.y);
	}
	public int distanceTo(Coord c){
		
		Coord tmp = minus(c);
		return Math.abs(tmp.getX())+Math.abs(tmp.getY());
		
	}
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
