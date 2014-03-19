package Parse;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import model.BackgroundType;
import model.Coord;
import model.Egg;
import model.Element;
import model.Environment;
import model.Item;
import model.Movement;
import model.Pokemon;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.LocatorImpl;

public class SimpleContentHandler implements ContentHandler{
	private Locator locator;
	static Environment env=new Environment(0, 0, BackgroundType.Earth);
	private BackgroundType bg_tmp;
	private int beginx,beginy,endx,endy;
	private Coord c;
	private String instance=null;
	private Movement tmp_m;
	
	public SimpleContentHandler(){
		super();
		locator = new LocatorImpl();
	}
	
	public void setDocumentLocator(Locator value){
		locator = value;
	}
	
	public void startDocument() throws SAXException {
	}
	
	public void endDocument() throws SAXException {
	}
	
	public void startPrefixMapping(String prefix, String URI) throws SAXException {
	}
	
	public void endPrefixMapping(String prefix) throws SAXException {
	}
	
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
        if(localName.equals("world")){
        	int width = 0,height = 0;
        	if(attributs.getLocalName(0).equals("width")){
        		width= Integer.parseInt(attributs.getValue(0));
        		env.setWidth(width);
        	}
        	if(attributs.getLocalName(1).equals("height")){
        		height = Integer.parseInt(attributs.getValue(1));
        		env.setHeight(height);
        	}
        	for(int i=0;i<width;++i){
        		for(int j=0;j<height;++j){
        			c = new Coord(i,j);
        			env.getContains().put(c, null);
        			env.getBackground().put(c, BackgroundType.Earth);
        		}
        	}
        }
        else if(localName.equals("zone")){
        	if(attributs.getLocalName(0).equals("background")){
        		if(attributs.getValue(0).equals("Sea")){
        			bg_tmp=BackgroundType.Sea;
        		}
        		else if(attributs.getValue(0).equals("Grass")){
        			bg_tmp=BackgroundType.Grass;
        		}
        		else if(attributs.getValue(0).equals("Water")){
        			bg_tmp=BackgroundType.Water;
        		}
        		else if(attributs.getValue(0).equals("Sand")){
        			bg_tmp=BackgroundType.Sand;
        		}
        	}
        }
        else if(localName.equals("begin")){
        	if(attributs.getLocalName(0).equals("x")){
        		beginx = new Integer(attributs.getValue(0));
        	}
        	if(attributs.getLocalName(1).equals("y")){
        		beginy = new Integer(attributs.getValue(1));
        	}
        }
        else if(localName.equals("end")){
        	if(attributs.getLocalName(0).equals("x")){
        		endx = new Integer(attributs.getValue(0));
        	}
        	if(attributs.getLocalName(1).equals("y")){
        		endy = new Integer(attributs.getValue(1));
        	}
        }
        else if(localName.equals("square")){
        	if(attributs.getLocalName(0).equals("x")){
        		beginx = new Integer(attributs.getValue(0));
        	}
        	if(attributs.getLocalName(0).equals("y")){
        		beginy = new Integer(attributs.getValue(1));
        	}
        	c = new Coord(beginx,beginy);
        	if(attributs.getLocalName(2).equals("background")){
        		bg_tmp=BackgroundType.valueOf(attributs.getValue(2));
        		
        	}
        	env.getBackground().put(c,bg_tmp);
        }
        
        else if(localName.equals("pokemon")||localName.equals("egg")){
        	if(attributs.getLocalName(0).equals("class")){
        		instance="model.pokemons."+attributs.getValue(0);
        	}
        	
        }
        else if(localName.equals("item")){
        	if(attributs.getLocalName(0).equals("class")){
        		instance="model.items."+attributs.getValue(0);
        	}
        }
        else if(localName.equals("position")){
        	if(attributs.getLocalName(0).equals("x")){
        		beginx = new Integer(attributs.getValue(0));
        	}
        	if(attributs.getLocalName(1).equals("y")){
        		beginy = new Integer(attributs.getValue(1));
        	}
        }
        else if(localName.equals("lookingAt")){
        	if(attributs.getLocalName(0).equals("direction")){
        		if(attributs.getValue(0).equals("DOWN")){
	        		tmp_m=Movement.Down;
	        	}
	        	else if(attributs.getValue(0).equals("UP")){
	        		tmp_m=Movement.Up;
	        	}
	        	else if(attributs.getValue(0).equals("RIGHT")){
	        		tmp_m=Movement.Right;
	        	}
	        	else if (attributs.getValue(0).equals("LEFT")){
	        		tmp_m=Movement.Left;
	        	}
        	}
        }
	}
	
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
        if(localName.equals("zone")){
        	for(int i=beginx;i<=endx;++i){
        		for(int j=beginy;j<=endy;++j){
        			c = new Coord(i,j);
        			env.getBackground().put(c, bg_tmp);
        		}
        	}
        }
        else if (localName.equals("pokemon")){
        	c = new Coord(beginx,beginy);
			Class cl = null;
			try {
				cl = Class.forName(instance);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Constructor con = null;
			try {
				con = cl.getConstructor(Environment.class, Coord.class, Movement.class);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Pokemon poke = null;
			try {
				poke = (Pokemon) con.newInstance(env,c,tmp_m);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else if(localName.equals("egg")){
        	c = new Coord(beginx,beginy);
        	Class cl = null;
			try {
				cl = Class.forName(instance);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Egg(c,cl, env);
        }
        else if(localName.equals("item")){
        	c = new Coord(beginx,beginy);
        	Class cl = null;
        	try {
				cl = Class.forName(instance);
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	Constructor con = null;
        	try {
				con = cl.getConstructor(Coord.class,Environment.class);
			} catch (NoSuchMethodException | SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	Item i = null;
        	try {
        		i=(Item) con.newInstance(c,env);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
	}
	
	public static Environment getEnv() {
		return env;
	}

	public void characters(char[] ch, int start, int end) throws SAXException {
	}
	
	public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
	}
	
	public void processingInstruction(String target, String data) throws SAXException {
	}
	public void skippedEntity(String name) throws SAXException {
		
	}
}	
