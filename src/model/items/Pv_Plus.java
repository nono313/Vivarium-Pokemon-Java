package model.items;

import model.Coord;
import model.Environment;
import model.Feature;
import model.UpgradingItem;

public class Pv_Plus extends UpgradingItem{
	public Pv_Plus(Coord position, Environment env) {
		super(position, env, Feature.HpMax);
	}
}