package model;

import View.Corner;

public enum Movement {
	Up,
	Down,
	Left,
	Right;
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