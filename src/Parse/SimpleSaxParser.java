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
	
	private SimpleContentHandler content;
	
	public SimpleSaxParser(String uri) throws SAXException, IOException {
         XMLReader saxReader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
         content = new SimpleContentHandler();
         saxReader.setContentHandler(content);
         saxReader.parse(uri);
	 }

	public SimpleContentHandler getContent() {
		return content;
	}
}
