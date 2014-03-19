package model.items;

import model.Coord;
import model.Environment;
import model.Feature;
import model.UpgradingItem;

public class Calcium extends UpgradingItem{

	public Calcium(Coord position, Environment env) {
		super(position, env, Feature.Special);
	}

}
