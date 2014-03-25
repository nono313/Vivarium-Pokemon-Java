package controll;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.text.View;

import View.NewsPanel;
import View.Window;
import model.*;

public class Controller {
	private static Controller instance;	/* Le controller est un singleton */
	
	private Environment env;
	private NewsPanel news;
	
	/* Singleton */
	private Controller() {
		news = NewsPanel.getInstance();
	}
	public static Controller getInstance() {
		if(instance == null)
			instance = new Controller();
		return instance;
	}
	
	/* Accesseurs et modificateurs */
	public static void setEnv(Environment env) {
		if(instance == null)
			instance = new Controller();
		instance.env = env;
	}
	public Environment getEnv() {
		return env;
	}
	
	/* 
	 * Déplacement d'un element en 2 étapes :
	 * - déplacer l'élement sur la carte
	 * - indiquer la nouvelle position au pokemon 
	 */
	public void move(Element el, Coord dest) {
		Coord src = el.getPosition();
		Movement mov = src.getDirectionTo(dest);
		// Réinitialise à null la case source
		env.putAt(null, src);
		// Place l'element sur la destination (dans la carte)
		env.putAt(el, dest);
		if(el instanceof Pokemon) {
			/* 
			 * S'il s'agit d'un Pokemon, on change son attribut lookingAt de façon à
			 *  se qu'il se tourne dans le sens du mouvement.
			 */
			((Pokemon)el).setLookingAt(mov);
		}
		/* Changement de l'attribut position de l'élément */
		el.moveTo(dest);
	}
	
	/* Le pokemon monte d'un niveau */
	public Pokemon levelUp(Pokemon p) {
		p.levelUp();
		news.addInfo(p + " monte au niveau " + p.getLvl());
		/* Si le pokemon peut evoluer et a atteint son niveau d'evolution, il evolue */
		if(p.getNextEvolution() != null && p.getLvl() >= p.getEvolutionLevel()) {
			Pokemon evol = evolution(p);
			Window.getInstance().getMainPane();
			return evol;
		}
		return p;
	}
	/* 
	 * Evolution du pokemon :
	 * création d'un nouveau pokemon d'une autre race au même endroit que l'ancien
	 */
	public Pokemon evolution(Pokemon p) {
		/* Création du nouveau Pokemon à partir du nom de la classe de l'évolution */	
		Class<? extends Pokemon> cl = p.getNextEvolution();
		Constructor<? extends Pokemon> con = null;
		try {
			con = cl.getConstructor(Pokemon.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		Pokemon evolution = null;
		synchronized (env.getListLiving()) {
			env.remove(p);
		}
		try {
			evolution = con.newInstance(p);
		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		news.addInfo(p.getClass().getSimpleName() + " évolue en " + evolution.getClass().getSimpleName() + " !");
		
		Window.getInstance().getMainPane().repaint();
		return evolution;
	}
	/* Evolution forcee par interaction de l'utilisateur */
	public Pokemon forceEvolution(Pokemon p) {
		/* Si le pokemon peut evoluer naturellement, on l'amene a ce niveau */
		if(p.getEvolutionLevel() != Short.MAX_VALUE) {
			while(p.getLvl() < p.getEvolutionLevel()) {
				p.levelUp();
			}
		}
		Pokemon evol = evolution(p);
		return evol;
	}
}
