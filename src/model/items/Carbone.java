package model.items;

import model.Coord;
import model.Environment;
import model.Feature;
import model.UpgradingItem;

public class Carbone extends UpgradingItem{

	public Carbone(Coord position, Environment env) {
		super(position, env, Feature.Speed);
	}

}
