package model;

/**
 * Type of items, representing the elements that can heal a feature of a pokemon.
 */
public abstract class HealingItem extends UsableItem{
	
	/**
	 * Represents the value by which the feature will be augmented.<br>
	 * If it is initialize at Short.MAX_VALUE, the feature is set at the maximum value possible.
	 */
	private short heal;
	
	public HealingItem(Coord position, short baseInterest, Environment env, short heal) {
		super(position,baseInterest, env);
		this.heal=heal;
	}
	
	/**
	 * The object is used on a pokemon
	 */
	public boolean affect(Pokemon on) {
		if(on.getHp()==on.getFeatureValue(Feature.HpMax)){
			return false;
		}
		else{
			if(heal==Short.MAX_VALUE){
				on.setHp((short) on.getFeatureValue(Feature.HpMax));
			}
			else{
				on.setHp((short) (on.getHp()+heal));
			}
			return true;
		}
	}

}
