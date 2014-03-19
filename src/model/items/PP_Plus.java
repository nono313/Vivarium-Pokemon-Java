package model.items;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import model.Attack;
import model.Coord;
import model.Environment;
import model.Pokemon;
import model.UsableItem;

public class PP_Plus extends UsableItem{

	public PP_Plus(Coord position, Environment env) {
		super(position, (short) 10, env);
	}

	@Override
	public boolean affect(Pokemon on) {
		Random rand = new Random();
		List<Attack> listAtt=new ArrayList<Attack>();
		for(Attack a : on.getAttacksAvailable()){
			if(a.getPowerPointBegin()*1.6>a.getPowerPointMax()){
				listAtt.add(a);
			}
		}
		if(listAtt.size()>0){
			int i=rand.nextInt()%listAtt.size();
			Attack att= listAtt.get(i);
			att.setPowerPointMax((short) (att.getPowerPointMax()+att.getPowerPointBegin()*0.2));
			return true;
		}
		return false;
	}
}

