package controll;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import View.SelectionPanel;
import View.Window;
import model.*;

public class MouseManager implements MouseListener {
	Environment env;
	Window view;
	SelectionPanel selectionPanel;
	
	public MouseManager(Environment env) {
		this.env = env;
		this.view = Window.getInstance();
		selectionPanel = view.getSelectionPanel();
	}
	public Coord getCoord(MouseEvent e) {
		int pixel = view.getMainPane().getPixel();
		return new Coord(e.getX()/pixel, e.getY()/pixel);
	}
	public void mouseClicked(MouseEvent arg0) {

	}

	public void mouseEntered(MouseEvent arg0) {

	}
	public void mouseExited(MouseEvent arg0) {

	}
	public void mousePressed(MouseEvent arg0) {
		Coord c = getCoord(arg0);
		Element el = env.getAt(c);
		/* 
		 * Si l'utilisateur a clique sur un objet dans le panneau de selection et que la case clique ici est vide, 
		 * on cree l'objet et le place sous le curseur
		 */
		if(selectionPanel.getObjectToAdd() != null && el == null) {
			Element toAdd;
			/* Si l'element a ajouter est un oeuf */
			if(selectionPanel.getObjectToAdd().equals(Egg.class)) {	
				toAdd = new Egg(c, env);
			}
			else if(Pokemon.class.isAssignableFrom(selectionPanel.getObjectToAdd())) { 			/* Si l'element a ajouter est un pokemon */
				/* Création à partir du nom de la classe */
				Constructor con = null;
				try {
					con = selectionPanel.getObjectToAdd().getConstructor(Environment.class, Coord.class, Movement.class);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				toAdd = null;
				try {
					toAdd = (Element) con.newInstance(env, c, Movement.Down);
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			else {
				/* Si l'element a ajouter est un objet */
				Constructor con = null;
				try {
					con = selectionPanel.getObjectToAdd().getConstructor(Coord.class, Environment.class);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				
				toAdd = null;
				try {
					toAdd = (Element) con.newInstance(c, env);
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			env.putAt(toAdd, c);
			selectionPanel.setObjectToAdd(null);
			selectionPanel.setCurrentPanel(selectionPanel.getPanelMap().get(null));
			Window.getInstance().getMainPane().repaint();
		}
		/* L'element sous le curseur est place en mode actif s'il ne l'est pas encore */
		if(el != null && selectionPanel.getSelected() == null && !el.isActive()) {
			el.setActive(true);			
		}
		/* 
		 * Si l'utilisateur avait selectionne quelque chose auparavant et clique dans le vide, 
		 * la selection est annule et l'element est remis en etat inactif.
		 */
		else if(selectionPanel.getSelected() != null && el == null) {
			selectionPanel.getSelected().setActive(false);
		}
		else {
			el = null;
		}
		selectionPanel.setSelected(el);
	}
	public void mouseReleased(MouseEvent arg0) {

	}

}
