package model;

public abstract class Living extends Element{
	protected int cycle = 0;
	protected int maxCycle;
	
	public Living(Coord position,Environment env) {
		super(position, env);
	}
	public boolean update(int timeElapsed) {
		cycle += timeElapsed;
		if(cycle >= maxCycle) {
			cycle = 0;
			return true;
		}
		else
			return false;
	}
	public void resetCycle() {
		this.cycle = 0;
	}
}
