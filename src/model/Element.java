package model;

import javax.swing.event.EventListenerList;
import controll.PositionListener;

/**
 * Base class of every element that can be placed on the map, either living creatures or objects.
 */
public abstract class Element implements Comparable<Element> {
	
	protected final Environment env;
	private boolean active;
	protected Coord position;
	private EventListenerList listeners = new EventListenerList();
	
	public Element(Coord position, Environment env){
		this.active = false;
		listeners.add(PositionListener.class, new PositionListener());
		this.env = env;
		this.position = position;
		this.env.createAt(this, position);
	}
	
	/* Getters and setters */
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Environment getEnv() {
		return env;
	}
	
	public Coord getPosition() {
		return position;
	}
	
	public void setPosition(Coord position) {
		this.position = position;
	}
	
	public PositionListener[] getPositionListeners() {
		return listeners.getListeners(PositionListener.class);
	}
	
	/**
	 * Change the position of the element and call every PositionListener
	 * @param dest : Coord
	 */
	public void moveTo(Coord dest) {
		Coord old = position;
		this.position = dest;
		for(PositionListener listener : getPositionListeners()) {
			listener.positionChange(this, old, dest);
		}
	}
	
	/**
	 * Comparison of two elements by comparing their coordinates
	 * @param o : Element to be compared
	 * @return integer : see {@link Coord#compareTo(Coord)}
	 */
	public int compareTo(Element o) {
		return position.compareTo(o.getPosition());
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
}
