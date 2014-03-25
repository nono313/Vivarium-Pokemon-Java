package model;

/**
 * These are objects that affect the effort value of a certain feature.
 */
public abstract class UpgradingItem extends UsableItem{

	private Feature f;
	public UpgradingItem(Coord position, Environment env, Feature f) {
		super(position,(short) 15,env);
		this.f=f;
	}
	public boolean affect(Pokemon on) {
		if(on.getEffortValue().get(f)==(short)255){
			return false;
		}
		else{
			on.setEffortValue(f, (short) 10);
			return true;
		}
	}


}




