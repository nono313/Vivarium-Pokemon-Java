package model;


public abstract class UsableItem extends Item {
	
	private short baseInterest;
	public UsableItem(Coord position, short baseInterest, Environment env) {
		super(position,env);
		this.baseInterest=baseInterest;
	}
	public short getBaseInterest() {
		return baseInterest;
	}
	public abstract boolean affect(Pokemon on);

}


