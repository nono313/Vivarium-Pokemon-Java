package model.attacks;

import model.Attack;
import model.Feature;
import model.Pokemon;
import model.Type;

public class Vol_Vie extends Attack {

	public Vol_Vie() {
		super((short) 20, (float) 1., (short) 20, Type.Plante);
	}
	
	public void affect(Pokemon offender, Pokemon defender){
		short powerAttack = offender.getFeatureValue(Feature.Attack);
		short powerDefense = defender.getFeatureValue(Feature.Defense);
		short damage;
		damage=(short) (((offender.getLvl()*0.4+2)*powerAttack*power)/(powerDefense*50)+2);
		if(damage==1){
			offender.setHp((short) (offender.getHp()+1));
		}
		else if(damage>defender.getHp()){	
			offender.setHp((short) (offender.getHp()+defender.getHp()/2));
		}
		else{
			offender.setHp((short) (offender.getHp()+damage/2));
		}
		defender.setHp((short)(defender.getHp()-damage));
	}
}