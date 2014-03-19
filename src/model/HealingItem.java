package model;

public abstract class HealingItem extends UsableItem{
	private short heal;
	public HealingItem(Coord position, short baseInterest, Environment env, short heal) {
		super(position,baseInterest, env);
		this.heal=heal;
	}
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
