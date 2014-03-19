package controll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Egg;
import model.Item;
import model.Pokemon;
import View.SelectionPanel;
import View.Window;

public class SelectionPanelListener implements ActionListener {
	private Controller control;
	private SelectionPanel selectionPanel;
	
	public SelectionPanelListener(Controller control) {
		this.control = control;
		selectionPanel = Window.getInstance().getSelectionPanel();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String buttonName = ((JButton)arg0.getSource()).getName();
		if(buttonName.equals("levelUp")) {
			/* Si on clique sur le bouton "Level Up", on fait monter le pokemon d'un niveau */
			Pokemon upgraded = control.levelUp((Pokemon) selectionPanel.getSelected());			
			selectionPanel.setSelected(upgraded);
		}
		else if(buttonName.equals("hatch")) {
			/* Bouton "Eclore" */
			((Egg) selectionPanel.getSelected()).hatch();
			Window.getInstance().getMainPane().repaint();
			selectionPanel.setSelected(null);

		}
		else if(buttonName.equals("evolve")) {
			Pokemon upgraded = control.forceEvolution((Pokemon) selectionPanel.getSelected());
			selectionPanel.setSelected(upgraded);
		}
		else if(buttonName.equals("addObject")) {
			selectionPanel.setCurrentPanel(selectionPanel.getPanelMap().get(Item.class));
		}
		else {
			for(Class cl : Window.getInstance().getMainPane().getItemsList()) {
				if(cl.getSimpleName().equals(buttonName)) {
					selectionPanel.setObjectToAdd(cl);	//Mettre a  jour le panneau selection
					selectionPanel.setCurrentPanel(selectionPanel.getObjectToAddPanel());
				}
			}
			if(buttonName.equals("addEgg")) {
				selectionPanel.setObjectToAdd(Egg.class);				
				selectionPanel.setCurrentPanel(selectionPanel.getObjectToAddPanel());
			}
			else if(buttonName.equals("addPokemon")) {
				selectionPanel.popupPokemon();
			}
			else if(buttonName.equals("addRandomPokemon")) {
				selectionPanel.popupRandomPokemon();
			}
		}
	
	}
	
	

}
