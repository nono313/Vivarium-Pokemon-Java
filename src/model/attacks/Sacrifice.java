package model.attacks;

import model.Attack;
import model.Feature;
import model.Pokemon;
import model.Type;

public class Sacrifice extends Attack {

	public Sacrifice() {
		super((short) 80, (float) 0.80, (short) 25, Type.Combat);
	}
	
	public void affect(Pokemon offender, Pokemon defender){
		short powerAttack = offender.getFeatureValue(Feature.Attack);
		short powerDefense = defender.getFeatureValue(Feature.Defense);
		short damage;
		damage=(short) (((offender.getLvl()*0.4+2)*powerAttack*power)/(powerDefense*50)+2);
		if(damage<4){
			offender.setHp((short) (offender.getHp()-1));
		}
		else{
			offender.setHp((short) (offender.getHp()-damage/4));
		}
		defender.setHp((short)(defender.getHp()-damage));
	}
}