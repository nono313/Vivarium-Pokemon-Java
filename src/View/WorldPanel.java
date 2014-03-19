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

public class WorldPanel extends JPanel {
	private Environment env;
	private int width;
	private int height;
	private int pixel = 24;
	private Map<Class<? extends Pokemon>, EnumMap<Movement, Image>> pokemonImages;
	private Image[] egg = new Image[6];
	private Map<Class, Image> imgItems = new HashMap<Class, Image>();
	Image imgBackGroundByDefault;

	private Map<BackgroundType, Map<Corner, Image>> bgImages = new EnumMap<BackgroundType, Map<Corner, Image>>(BackgroundType.class);
	private Map<Coord, Image> backgroundMap = new TreeMap<Coord, Image>();
	private List<Class> itemsList;
	private List<Class<? extends Pokemon>> pokemonList;
	private List<Class<? extends Pokemon>> originalFormsList;
	
	public Map<Class<? extends Pokemon>, EnumMap<Movement, Image>> getPokemonImages() {
		return pokemonImages;
	}
	public List<Class<? extends Pokemon>> getPokemonList() {
		return pokemonList;
	}
	public List<Class> getItemsList() {
		return itemsList;
	}
	public Map<Class, Image> getImgItems() {
		return imgItems;
	}

	public Image[] getEgg() {
		return egg;
	}
	public int getCoord(int x) {
		return x*pixel;
	}
	public int getPixel() {
		return pixel;
	}
	public void setPixel(int pixel) {
		this.pixel = pixel;
	}
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
			/*pokemonList.add(Salameche.class);
			pokemonList.add(Reptincel.class);*/
			
			/* List automatique des classes du package model.pokemons */
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
			try {
				for(int i = 0; i < 6; ++i) {
					egg[i] = ImageIO.read(new File("sprites/egg/egg-" + i + ".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			      } else if (listOfFiles[i].isDirectory()) {
			      }
			    }
			for(Class cl : itemsList) {
				try {
					img = ImageIO.read(new File("sprites/item/" + cl.getSimpleName().toLowerCase() + ".png"));
					imgItems.put(cl, img);
	
				} catch (IOException e) {
					//e.printStackTrace();
				}
			}
			
			generateBackground();

	}
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
					e.printStackTrace();
				}	
			}
		}
		return originalFormsList;
	}
	public void generateBackground() {
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
}
