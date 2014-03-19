package View;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

import model.Environment;


public class Window extends JFrame {
	private static Window instance;
	private Environment env;
	private WorldPanel mainPane;
	private JPanel rightContainer;
	private NewsPanel news;
	private SelectionPanel selectionPanel;
	private JSplitPane split, split2, split3;
	  
	public Window(Environment env) throws Exception {
		this.env = env;
		this.setTitle("Vivarium Pokemon");
		
		mainPane = new WorldPanel(env);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		Dimension screenSize = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight()-20);
		
		Dimension windowSize = new Dimension();
		windowSize.setSize(300+mainPane.getPixel()*env.getWidth(), mainPane.getPixel()*(env.getHeight()+1)+2);
		if(windowSize.getWidth() > screenSize.getWidth() || windowSize.getHeight() > screenSize.getHeight())
			throw new Exception("La taille du vivarium est trop grande pour votre taille d'écran.");
		else
			this.setSize(300+mainPane.getPixel()*env.getWidth(), mainPane.getPixel()*(env.getHeight()+1)+2);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		//On crée deux conteneurs de couleurs différentes
	    
	    mainPane.setBackground(Color.blue);   
	    
	    news = NewsPanel.getInstance();
	    news.setBackground(Color.red);
	    selectionPanel = new SelectionPanel();
	    rightContainer = new JPanel();
	    rightContainer.setLayout(new BorderLayout());
	    rightContainer.add(news, BorderLayout.CENTER);
	    rightContainer.add(selectionPanel, BorderLayout.SOUTH);
	    //On construit enfin notre séparateur
	    
	    //split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPane, news);
	    //On place le premier séparateur
	   // split.setEnabled(false);
	    //split.setDividerLocation(80);
	    
	    //split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pan3, pan2);
	    //On place le deuxième séparateur
	    //split2.setDividerLocation(100);
	    //On passe les deux précédents JSplitPane à celui-ci
	    //split3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, split, split2);
	    //On place le troisième séparateur
	  //split3.setDividerLocation(80);

	    //On le passe ensuite au content pane de notre objet Fenetre
	    //placé au centre pour qu'il utilise tout l'espace disponible
	    Container container = this.getContentPane();
	    
	   // mainPane.setPreferredSize(new Dimension(this.getWidth()-200, this.getHeight()));
	    rightContainer.setPreferredSize(new Dimension(300, this.getHeight()));
	    
	    setLayout(new BorderLayout());
	    
	    JScrollPane scrollPane = new JScrollPane();
	    add(mainPane, BorderLayout.CENTER);
	    add(rightContainer, BorderLayout.LINE_END);
		
	   

		this.setVisible(true);
		(new Thread(news)).start();
		instance = this;
	}
	public static Window getInstance() {
		return instance;
	}

	public WorldPanel getMainPane() {
		return mainPane;
	}

	public NewsPanel getNews() {
		return news;
	}
	public void addNews(String s) {
		news.addInfo(s);
	}
	public SelectionPanel getSelectionPanel() {
		return selectionPanel;
	}
	public Environment getEnv() {
		return env;
	}
}
