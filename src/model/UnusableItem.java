package model;

/**
 * Unusable items are mostly elements of the environment like rocks of trees
 */
public abstract class UnusableItem extends Item{
	public UnusableItem(Coord position, Environment env) {
		super(position, env);
	}
}
