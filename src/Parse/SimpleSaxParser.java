package Parse;


import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import model.Coord;
import model.Environment;
import model.Movement;
import model.MovementManager;
import model.Pokemon;
import model.pokemons.Reptincel;
import model.pokemons.Salameche;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import View.Window;
import controll.Controller;
import controll.MouseManager;
import controll.PositionListener;
import controll.SelectionPanelListener;

public class SimpleSaxParser {
	SimpleContentHandler content;
	public SimpleSaxParser(String uri) throws SAXException, IOException {
         XMLReader saxReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
         content = new SimpleContentHandler();
         saxReader.setContentHandler(content);
         saxReader.parse(uri);
	 }

	public SimpleContentHandler getContent() {
		return content;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String uri;
		uri = "Pokemon.xml";
		try {
			SimpleSaxParser parser = new SimpleSaxParser(uri);
			Environment env = parser.getContent().getEnv();
			
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
