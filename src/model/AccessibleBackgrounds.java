package model;

import java.util.ArrayList;

public class AccessibleBackgrounds extends ArrayList<BackgroundType> {
	
	@Override
	public boolean add(BackgroundType bg) {
		if(this.contains(bg))
			return false;
		super.add(bg);
		super.addAll(bg.getEquivalent());
		return true;
	}
	
	public boolean addOnly(BackgroundType bg) {
		if(this.contains(bg))
			return false;
		super.add(bg);
		return true;
	}
}
