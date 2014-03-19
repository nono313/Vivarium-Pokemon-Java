package model.items;

import model.Coord;
import model.Environment;
import model.Feature;
import model.UpgradingItem;

public class Fer extends UpgradingItem{

	public Fer(Coord position, Environment env) {
		super(position, env, Feature.Defense);
	}

}
