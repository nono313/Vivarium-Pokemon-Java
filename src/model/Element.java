package model;

import javax.swing.event.EventListenerList;

import controll.PositionListener;


public abstract class Element implements Comparable<Element>{
	protected final Environment env;
	protected boolean active;
	protected Coord position;
	protected EventListenerList listeners = new EventListenerList();
	
	public Element(Coord position, Environment env){
		this.active = false;
		listeners.add(PositionListener.class, new PositionListener());
		this.env = env;
		this.position = position;
		this.env.createAt(this, position);
	}
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
	public void moveTo(Coord dest) {
		Coord old = position;
		this.position = dest;
		for(PositionListener listener : getPositionListeners()) {
			listener.positionChange(this, old, dest);
		}
	}
	public int compareTo(Element o) {
		return position.compareTo(o.getPosition());
	}
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
}
