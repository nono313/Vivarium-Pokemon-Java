package model.items;

import model.Coord;
import model.Environment;
import model.Feature;
import model.UpgradingItem;

public class Proteine extends UpgradingItem{

	public Proteine(Coord position, Environment env) {
		super(position, env, Feature.Attack);
	}

}
