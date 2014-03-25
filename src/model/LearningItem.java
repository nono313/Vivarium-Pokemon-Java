package model;

/**
 * Category of items that makes pokemon learn an attack.<br>
 * Unfortunately we didn't have time to implement some of these objects.
 */
public abstract class LearningItem extends UsableItem{

	public LearningItem(Coord position, short baseInterest, Environment env) {
		super(position,baseInterest, env);
	}

}
