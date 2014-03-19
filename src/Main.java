import java.awt.event.ActionListener;

import model.*;
import model.pokemons.*;
import View.Window;
import controll.Controller;
import controll.MouseManager;
import controll.PositionListener;
import controll.SelectionPanelListener;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Environment env = new Environment(40, 25, BackgroundType.Earth);
		Coord c = new Coord(1,1);
		
		Window view;
		try {
			view = new Window(env);
			Controller.setEnv(env);
			Controller control = Controller.getInstance();
			PositionListener.setView(view);
			
			MouseManager mouse = new MouseManager(env);
			view.getMainPane().addMouseListener(mouse);
			ActionListener actionList = new SelectionPanelListener(control);
			view.getSelectionPanel().setListener(actionList);
			MovementManager moveMana = new MovementManager(env, control);
			
			moveMana.start();
			
			
			env.generatePokemons(20);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	
	

}
