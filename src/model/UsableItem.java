package model;

/**
 * Any object that can be used by a pokemon
 */
public abstract class UsableItem extends Item {
	/**
	 * Base value used to compute the interest map
	 */
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


