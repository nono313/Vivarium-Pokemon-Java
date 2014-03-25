import java.awt.event.ActionListener;
import java.util.Scanner;

import model.*;
import Parse.SimpleContentHandler;
import Parse.SimpleSaxParser;
import View.Window;
import controll.Controller;
import controll.MouseManager;
import controll.PositionListener;
import controll.SelectionPanelListener;


/**
 * Main class containing the main function initializing the different elements of the MVC pattern
 * 
 * @author Julien ROBERT
 * @author Nathan OLFF
 *
 */
public class Main {

	public static void main(String[] args) {
		String uri;
		uri = "Pokemon.xml";
		try {
			SimpleSaxParser parser = new SimpleSaxParser(uri);
			parser.getContent();
			Environment env = SimpleContentHandler.getEnv();
			
			Window view = new Window(env);

			Controller control = Controller.getInstance();
			Controller.setEnv(env);
			PositionListener.setView(view);
			
			MouseManager mouse = new MouseManager(env);
			view.getMainPane().addMouseListener(mouse);
			ActionListener actionList = new SelectionPanelListener(control);
			
			view.getSelectionPanel().setListener(actionList);
			MovementManager moveMana = new MovementManager(env, control);
			moveMana.start();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		
		
	}
	
	
	

}
