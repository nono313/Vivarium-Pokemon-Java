package model;

import View.Corner;

/**
 * This enum class represents the four directions.<br>
 * It is used for the lookingAt attribute of Pokemon, but also within the MovementManager and the functions that chooses the next position of a pokemon. 
 */
public enum Movement {
	Up,
	Down,
	Left,
	Right;
	
	/**
	 * The movement opposite to this movement.
	 * @return opposite movement
	 */
	public Movement opposite() {
		switch(this) {
		case Up:
			return Down;
		case Down:
			return Up;
		case Left:
			return Right;
		case Right:
			return Left;
		}
		return null;
	}
	
	/**
	 * The mix of two movements gives a Corner.<br>
	 * e.g : Up and Left gives North-West
	 * @param m : Movement
	 * @return a Corner
	 */
	public Corner plus(Movement m) {
		switch(this) {
		case Up:
			switch(m) {
			case Left:
				return Corner.NW;
			case Right:
				return Corner.NE;
			default:
				return Corner.N;		
			}
		case Down:
			switch(m) {
			case Left:
				return Corner.SW;
			case Right:
				return Corner.SE;
			default:
				return Corner.S;
			}
		case Left:
			switch(m) {
			case Up:
				return Corner.NW;
			case Down:
				return Corner.NE;
			default:
				return Corner.W;
			}
		case Right:
			switch(m) {
			case Up:
				return Corner.NE;
			case Down:
				return Corner.SE;
			default:
				return Corner.E;
			}
		}
		return null;
	}
}