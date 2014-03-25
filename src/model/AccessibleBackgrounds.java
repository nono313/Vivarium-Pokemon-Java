package model;

import java.util.ArrayList;

/**
 * A list of {@link BackgroundType} representing which type of ground a Pokemon is allowed to be on.
 */
public class AccessibleBackgrounds extends ArrayList<BackgroundType> {
	
	/**
	 * Add bg (and its equivalents) to the list of {@link BackgroundType}
	 * 
	 * @param bg : BackgroundType
	 * @return false if bg was already in the list, true if it has been added to the list
	 */
	@Override
	public boolean add(BackgroundType bg) {
		if(this.contains(bg))
			return false;
		else {
			super.add(bg);
			super.addAll(bg.getEquivalent());
			return true;
		}
	}
	
	/**
	 * Add bg to the list of {@link BackgroundType}
	 * 
	 * @param bg : BackgroundType
	 * @return false if bg was already in the list, true if it has been added to the list
	 */
	public boolean addOnly(BackgroundType bg) {
		if(this.contains(bg))
			return false;
		else {
			super.add(bg);
			return true;
		}
	}
}
