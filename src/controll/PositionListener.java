package controll;

import java.beans.PropertyChangeEvent;

import View.*;
import controll.*;

import java.beans.PropertyChangeListener;
import java.util.EventListener;

import model.*;

public class PositionListener implements EventListener{
	private static Window view;
	public void positionChange(Element e, Coord from, Coord to) {
		/* Lorsqu'un element est déplacé, on actualise la carte */
		view.getMainPane().repaint();
	}
	public static void setView(Window view) {
		PositionListener.view = view;
	}
}
