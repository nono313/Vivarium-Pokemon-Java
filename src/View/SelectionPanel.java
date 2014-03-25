package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import model.Egg;
import model.Element;
import model.Feature;
import model.Item;
import model.Pokemon;
import model.Type;

/**
 * This panel manages the user's actions. Its content changes when a user selects something.
 */
public class SelectionPanel extends JPanel {
	
	private Map<Class<?>, JPanel> panelMap;
	
	/** Represents an elements already on the map that has been clicked by the user */
	private Element selected = null;
	
	/** Store the current panel displayed */
	private JPanel currentPanel = null;	
	
	/** Title of the panel, displayed on the top of it. */
	private JLabel title = new JLabel();
	
	/** ActionListener called when a button is clicked */
	private ActionListener listener;
	
	
	/* When a pokemon is selected, these elements are displayed : */
	/** Force a pokemon to gain a level, whatever its experience is, and update its exp value to what it is supposed to be at that new level */
	private JButton levelUpButton;
	/** 
	 * Progress bar showing the experience gained since the last upgrade in level.<br>
	 * When the bar is full, it means that the pokemon has reached expUp and therefore gain a level.
	 */
	private JProgressBar expProgress;
	/** Progress bar showing the health point of the pokemon out of its maximum health point */
	private JProgressBar progressBar;
	/** A button that force a pokemon to evolve (when it has an evolution).*/
	private JButton evolveButton;
	/** Array of JLabel showing the effective values of the features of the selected pokemon */
	private JLabel[] features;
	/** Panel containing the images corresponding to the type(s) of the selected pokemon */
	private JPanel types = new JPanel();
	/** General map that link each type to its corresponding image */
	private Map<Type, Image> typesImages = new EnumMap<Type, Image>(Type.class);
	
	
	/** When the "add object" button is clicked, the objectToAddPanel is displayed showing every items available. **/
	private JPanel objectToAddPanel;
	/** Class of the object that will be placed on the map */
	private Class<? extends Element> objectToAdd = null;
	/** Name of the object that will be added to the map */
	private JLabel labelObjectToAdd = new JLabel();
	
	
	public SelectionPanel() {
		this.setPreferredSize(new Dimension(300, 250));
		panelMap = new HashMap<Class<?>, JPanel>();
		this.setLayout(new BorderLayout(5,5));
		features = new JLabel[4];
		title.setHorizontalAlignment(JLabel.CENTER);
		this.add(title, BorderLayout.PAGE_START);
		/* Filling the typesImages map */
		Image img;
		for(Type t : Type.values()) {
			try {
				img = ImageIO.read(new File("sprites/type/"+ t +".png"));
				typesImages.put(t, img);
			}catch(IOException e) { }
		}
	}
	/**
	 * Adds a button to the panel
	 * @param str : String displayed in the button
	 * @param name of the button
	 * @return the JButton created
	 */
	private JButton newButton(String str, String name) {
		JButton button = new JButton(str);
		button.setName(name);
		button.addActionListener(listener);
		return button;
	}
	/**
	 * Adds a button with only its content string and no name.<br>
	 * In this case, the string is also used as a name
	 * @param str : String displayed in the button
	 * @return the JButton
	 */
	public JButton newButton(String str) {
		return newButton(str, str);
	}
	
	/** 
	 * Generate every panels that can be displayed in the SelectionPanel area.
	 */
	private void generatePanels() {
		JPanel tmpPanel = new JPanel();
		tmpPanel.setLayout(new BoxLayout(tmpPanel, BoxLayout.Y_AXIS));
		tmpPanel.setAlignmentX(CENTER_ALIGNMENT);
		levelUpButton = newButton("Level Up", "levelUp");
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(100, 13));
		
		
		/* Pokemon selected */
		JPanel displayHP = new JPanel();
		displayHP.setLayout(new FlowLayout());
		displayHP.add(new JLabel("PV : "));
		displayHP.add(progressBar);
		displayHP.setAlignmentX(CENTER_ALIGNMENT);
		
		features[0] = new JLabel();
		features[1] = new JLabel();
		features[2] = new JLabel();
		features[3] = new JLabel();
		
		tmpPanel.add(types);
		
		tmpPanel.add(displayHP);
		
		JPanel pan = new JPanel();
		pan.add(new JLabel("Attaque : "));
		pan.add(features[0]);
		tmpPanel.add(pan);
		
		pan = new JPanel();
		pan.add(new JLabel("Defence : "));
		pan.add(features[1]);
		tmpPanel.add(pan);
		
		pan = new JPanel();
		pan.add(new JLabel("Vitesse : "));
		pan.add(features[2]);
		tmpPanel.add(pan);
		
		pan = new JPanel();
		pan.add(new JLabel("Special : "));
		pan.add(features[3]);
		tmpPanel.add(pan);
		
		pan = new JPanel();
		pan.add(new JLabel("Experience : "));
		expProgress = new JProgressBar();
		expProgress.setPreferredSize(new Dimension(100, 13));
		pan.add(expProgress);
		tmpPanel.add(pan);
		
		evolveButton = newButton("Evoluer", "evolve");

		JPanel buttons = new JPanel();
		buttons.add(levelUpButton);
		buttons.add(evolveButton);
		tmpPanel.add(buttons);
		
		panelMap.put(Pokemon.class, tmpPanel);		
		
		/* Egg selected */
		tmpPanel = new JPanel();
		levelUpButton = newButton("Eclore", "hatch");
		tmpPanel.add(levelUpButton);
		panelMap.put(Egg.class, tmpPanel);
		
		/* Panel of object adding */
		tmpPanel = new JPanel();
		Map<Class<Item>, Image> imgItems = Window.getInstance().getMainPane().getImgItems();
		for(Class<Item> cl : imgItems.keySet()) {
			ImageIcon icon = new ImageIcon(imgItems.get(cl));
			JButton but = new JButton(new ImageIcon((icon.getImage()).getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
			but.addActionListener(listener);
			but.setName(cl.getSimpleName());
			tmpPanel.add(but);
		}
		ImageIcon icon = new ImageIcon(Window.getInstance().getMainPane().getEgg()[0]);
		JButton but = new JButton(new ImageIcon((icon.getImage()).getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
		
		but.addActionListener(listener);
		but.setName("addEgg");
		tmpPanel.add(but);
		panelMap.put(Item.class, tmpPanel);
		
		/* Add the selected object to a square on the map */
		objectToAddPanel = new JPanel();
		objectToAddPanel.add(new JLabel("Vous avez sélectionné :"));
		objectToAddPanel.add(labelObjectToAdd);
		objectToAddPanel.add(new JLabel("Veuillez sélectionner une case vide."));
		
		/* Nothing selected : 3 buttons displayed */
		tmpPanel = new JPanel();
		tmpPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 35));
		JButton buttonObject = newButton("Ajouter objet", "addObject");
		tmpPanel.add(buttonObject);
		buttonObject = newButton("Ajouter pokemon", "addPokemon");
		tmpPanel.add(buttonObject);
		buttonObject = newButton("Ajouter aléatoirement des pokemons", "addRandomPokemon");
		tmpPanel.add(buttonObject);
		panelMap.put(null, tmpPanel);

		updateValues();
		setCurrentPanel(panelMap.get(null));

	}

	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	}
	
	/** Display a popup askig the user which pokemon he wants to add to the map */
	public void popupPokemon() {
		List<String> possibilities = new ArrayList();
		for(Class cl : Window.getInstance().getMainPane().getPokemonList()) {
			possibilities.add(cl.getSimpleName());
		}
		Collections.sort(possibilities);
		String s = (String) JOptionPane.showInputDialog(
		                    Window.getInstance(),
		                    "Quel pokemon voulez-vous ajouter ?",
		                    "Ajout de pokemon",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities.toArray(),
		                    possibilities.get(0));

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
		    try {
		    	setObjectToAdd((Class<? extends Element>) Class.forName("model.pokemons." + s));
		    	setCurrentPanel(getObjectToAddPanel());
		    	repaint();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * Popup displayed when a user wants to random pokemons.<br>
	 * It asks the user how many pokemon he wants to add to the map.
	 */
	public void popupRandomPokemon() {
		List<String> possibilities = new ArrayList();
		for(Class cl : Window.getInstance().getMainPane().getPokemonList()) {
			possibilities.add(cl.getSimpleName());
		}
		String s = (String) JOptionPane.showInputDialog(
		                    Window.getInstance(),
		                    "Combien de pokemons voulez-vous ajouter ?");

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			try  {
			    int n = Integer.parseInt(s);
				Window.getInstance().getEnv().generatePokemons(n);
				repaint();
			} catch(Exception e) { }
		}
	}

	/** Update the strings of the current panel */
	public void updateValues() {
		if(objectToAdd != null) {
			title.setText("");
			labelObjectToAdd.setText(objectToAdd.getSimpleName());
		}
		else if(selected != null) {
			title.setText(selected.getClass().getSimpleName());
			if(selected instanceof Pokemon) {
				Pokemon poke = ((Pokemon)selected);
				title.setText(selected.getClass().getSimpleName() + " ( niv. " + poke.getLvl() + " )");
				progressBar.setMaximum(poke.getFeatureValue(Feature.HpMax));
				progressBar.setValue(poke.getHp());
				features[0].setText("" + poke.getFeatureValue(Feature.Attack));
				features[1].setText("" + poke.getFeatureValue(Feature.Defense));
				features[2].setText("" + poke.getFeatureValue(Feature.Speed));
				features[3].setText("" + poke.getFeatureValue(Feature.Special));
				int previousExp = poke.expNeeded(poke.getLvl()-1);
				expProgress.setMaximum(poke.getExpUp()-previousExp);
				expProgress.setValue(poke.getExperience()-previousExp);
				
				types.removeAll();
				
				for(Type t : poke.getTypes()) {
					types.add(new JLabel(new ImageIcon(typesImages.get(t))));
				}
				/* Display the evolve button only if the pokemon has an evolution */
				evolveButton.setVisible(poke.getNextEvolution() != null);
			}
		}
		else {
			title.setText("Séléctionner une action");
		}
	}

	/**
	 * Set the selected element and display the corresponding panel
	 * @param selected : Element
	 */
	public void setSelected(Element selected) {
		if(this.selected != null) {
			this.selected.setActive(false);
		}
		this.selected = selected;
		if(selected != null) {
			objectToAdd = null;
			selected.setActive(true);
			updateValues();
			if(selected instanceof Pokemon) {
				/* Update the progress bars before displaying them */
				setCurrentPanel(panelMap.get(Pokemon.class));
				updateValues();
				
			}
			else if(selected instanceof Egg) {
				setCurrentPanel(panelMap.get(Egg.class));
			}
		}
		else {
			setCurrentPanel(panelMap.get(null));
			updateValues();
		}

		this.revalidate();
		this.repaint();
		
	}
	
	/* Getters and setters */
	public void setListener(ActionListener listener) {
		this.listener = listener;
		generatePanels();
	}
	
	public JButton getLevelUpButton() {
		return levelUpButton;
	}
	
	public JPanel getObjectToAddPanel() {
		return objectToAddPanel;
	}
	
	public Map<Class<?>, JPanel> getPanelMap() {
		return panelMap;
	}
	
	public Class<? extends Element> getObjectToAdd() {
		return objectToAdd;
	}
	
	public void setObjectToAdd(Class<? extends Element> objectToAdd) {
		this.selected = null;
		this.objectToAdd = objectToAdd;
	}
	
	public JPanel getCurrentPanel() {
		return currentPanel;
	}
	
	public Element getSelected() {
		return selected;
	}
	
	public void setCurrentPanel(JPanel current) {
		currentPanel = current;
		if(this.getComponentCount() > 1)
			this.remove(1);
		updateValues();
		this.add(currentPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
}
