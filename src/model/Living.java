package model;

/**
 * Living elements are elements that have a cycle and a maxCycle value.
 */
public abstract class Living extends Element{
	
	protected int cycle = 0;
	protected int maxCycle;
	
	public Living(Coord position,Environment env) {
		super(position, env);
	}
	
	/**
	 * Update method that increases the value of cycle.
	 * @param timeElapsed : integer representing the time between the last update and now 
	 * @return boolean value : true if the value of maxCycle have been reached
	 */
	public boolean update(int timeElapsed) {
		cycle += timeElapsed;
		if(cycle >= maxCycle) {
			cycle = 0;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Initialize the cycle value
	 */
	public void resetCycle() {
		this.cycle = 0;
	}
}
