package View;


import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.*;
import model.items.*;
import model.pokemons.*;

/**
 * Panel displaying the environment with the backgrounds and every elements on it.
 */
public class WorldPanel extends JPanel {
	
	private Environment env;
	
	/* Dimensions of the panel */
	private int width;
	private int height;
	
	/** Size of a square */
	private int pixel = 24;
	
	/** Double map containing for each pokemon class, a map of images depending on the direction they are looking at */
	private Map<Class<? extends Pokemon>, EnumMap<Movement, Image>> pokemonImages;
	
	/** The six states of an egg */
	private Image[] egg = new Image[6];
	
	/** Each item class is linked to an image */
	private Map<Class<Item>, Image> imgItems = new HashMap<Class<Item>, Image>();
	
	/** Image of the default background */
	private Image imgBackGroundByDefault;

	/** 
	 * Each background type is linked to a map of key-value where keys are Corner and values are images.<br>
	 * Most of the backgrounds looks different when they are in the middle of a zone of the same type or next to an other type of background.
	 */
	private Map<BackgroundType, Map<Corner, Image>> bgImages = new EnumMap<BackgroundType, Map<Corner, Image>>(BackgroundType.class);
	
	/** Actual map of the background of the environment */
	private Map<Coord, Image> backgroundMap = new TreeMap<Coord, Image>();
	
	/** List of items on the world */
	private List<Class> itemsList;
	
	/** List of every pokemon available in the game */
	private List<Class<? extends Pokemon>> pokemonList;
	
	/** List of all pokemon available that are not an evolution of an other */
	private List<Class<? extends Pokemon>> originalFormsList;
	
	
	public WorldPanel(Environment env) {
		super();
		this.env = env;
		
		try {
			this.imgBackGroundByDefault = ImageIO.read(new File("sprites/background/" + env.getBgByDefault().toString().toLowerCase() + ".png"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		width = env.getWidth();
		height = env.getHeight();
		pokemonImages = new HashMap<Class<? extends Pokemon>, EnumMap<Movement, Image> >();
		
		Image img;
		pokemonList = new ArrayList<Class<? extends Pokemon>>();
		
		/* List the classes present in the package model.pokemons to get every pokemons available in the program */
		File folder = new File("src/model/pokemons");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        try {
					pokemonList.add((Class<? extends Pokemon>) Class.forName("model.pokemons."+listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf('.'))));
					
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
		      }
		    }
		
		for(Class<? extends Pokemon> cl : pokemonList) {
			pokemonImages.put(cl, new EnumMap<Movement, Image>(Movement.class));
			for(Movement m : Movement.values()) {
				try {
					img = ImageIO.read(new File("sprites/pokemon/"+ cl.getSimpleName().toLowerCase() +"-idle-" + m.toString().toLowerCase() + ".png"));
					pokemonImages.get(cl).put(m, img);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* Create the image list of the egg states */
		try {
			for(int i = 0; i < 6; ++i) {
				egg[i] = ImageIO.read(new File("sprites/egg/egg-" + i + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* Generate the background images map */
		Image bg;
		for(BackgroundType b : BackgroundType.values()) {
			Map m = new EnumMap<Corner, Image>(Corner.class);
			try {
				bg = ImageIO.read(new File("sprites/background/" + b.toString().toLowerCase() + ".png"));
				m.put(Corner.NONE, bg);
			} catch (IOException e) { }
			for(Corner corn : Corner.values()) {
				try {
					bg = ImageIO.read(new File("sprites/background/" + b.toString().toLowerCase() + "-corner-" + corn.toString() + ".png"));
					m.put(corn, bg);

				} catch(IOException e) {}	/* Fichier n'existe pas */
			}
			bgImages.put(b, m);
			
		}
		
		/* Get the list of items implemented in model.items */
		folder = new File("src/model/items");
		listOfFiles = folder.listFiles();
		itemsList = new ArrayList();
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    		try {
	    			itemsList.add((Class<? extends Item>) Class.forName("model.items."+listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf('.'))));
	    		} catch (ClassNotFoundException e1) {
	    			e1.printStackTrace();
	    		}
	    	}
	    }
	    
	    /* Generate the map of item images */
		for(Class cl : itemsList) {
			try {
				img = ImageIO.read(new File("sprites/item/" + cl.getSimpleName().toLowerCase() + ".png"));
				imgItems.put(cl, img);

			} catch (IOException e) {

			}
		}
		generateBackground();
	}
	
	/**
	 * Get the list of pokemon that are not evolutions of others
	 * @return list of Pokemon
	 */
	public List<Class<? extends Pokemon>> getOriginalFormList() {
		if(originalFormsList == null) {
			originalFormsList = new ArrayList<Class<? extends Pokemon>>();
			for(Class<? extends Pokemon> cl : pokemonList) {
				Method m;
				Object ret;
				try {
					m = cl.getDeclaredMethod("isOriginalForm");
					ret = m.invoke(null, null);
					if(((Boolean)ret) == true)
						originalFormsList.add(cl);
	
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					/* 
					 * There's an issue with the non-original forms.
					 * It looks like the method isOriginalForm from the base class pokemon is not found and therefore we get a NoSuchMethodException.
					 * Each of the original pokemons redefined the method so there is no problem and the method return the good list
					 */
					//e.printStackTrace();
				}	
			}
		}
		return originalFormsList;
	}
	
	/** 
	 * Generate the backgroundMap from the informations stored in the environment and the background images map.
	 */
	private void generateBackground() {
		Image img;
		Map<Movement, Boolean> mapBool = new EnumMap<Movement, Boolean>(Movement.class);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				BackgroundType type = env.getBackground().get(new Coord(i,j));
				if(type != env.getBgByDefault()) {
					Coord c = new Coord(i,j);
					if(bgImages.get(type).size() > 1) {
						for(Movement m : Movement.values()) {
							mapBool.put(m, false);
							if(c.plus(m).isOnMap(env) && 
									!type.equals(env.getBackground().get(c.plus(m)))) {
								mapBool.put(m, true);
							}
						}
						
						Corner corn = Corner.getCorner(mapBool.get(Movement.Up), mapBool.get(Movement.Down), mapBool.get(Movement.Left), mapBool.get(Movement.Right));				
						img = bgImages.get(type).get(corn);
						
						if(img != null) {
							backgroundMap.put(new Coord(i,j), img);
						}
						else {
							backgroundMap.put(new Coord(i,j), bgImages.get(type).get(Corner.NONE));
						}
					}
					else
						backgroundMap.put(new Coord(i,j), bgImages.get(type).get(Corner.NONE));
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {				
				g.drawImage(imgBackGroundByDefault, getCoord(i), getCoord(j), pixel, pixel, this);
				g.drawImage(backgroundMap.get(new Coord(i,j)), getCoord(i), getCoord(j), pixel, pixel, this);
			}
		}
		for(Coord c : env.getContains().keySet().toArray(new Coord[0])) {
			Element el = env.getAt(c);
			if(el != null) {
				if(el instanceof Pokemon) {
					Pokemon poke = (Pokemon) el;
					synchronized (poke) {
						if(!poke.isBeingAttacked()){
							g.drawImage(pokemonImages.get(el.getClass()).get(poke.getLookingAt()), getCoord(c.getX()), getCoord(c.getY()), pixel, pixel, this);
						}
					}
				}
				else if(el instanceof Egg) {
					synchronized (el) {
						g.drawImage(egg[((Egg)el).getState()], getCoord(c.getX()), getCoord(c.getY()), pixel, pixel, this);
					}
				}
				else if(el instanceof Item) {
					synchronized (el) {
						g.drawImage(imgItems.get(el.getClass()), getCoord(c.getX()), getCoord(c.getY()), pixel, pixel, this);
					}
				}
			}
		}
	}
	
	/* Getters and setters */
	public Map<Class<? extends Pokemon>, EnumMap<Movement, Image>> getPokemonImages() {
		return pokemonImages;
	}
	
	public List<Class<? extends Pokemon>> getPokemonList() {
		return pokemonList;
	}
	
	public List<Class> getItemsList() {
		return itemsList;
	}
	
	public Map<Class<Item>, Image> getImgItems() {
		return imgItems;
	}

	public Image[] getEgg() {
		return egg;
	}
	
	private int getCoord(int x) {
		return x*pixel;
	}
	
	public int getPixel() {
		return pixel;
	}
	
	public void setPixel(int pixel) {
		this.pixel = pixel;
	}
}
