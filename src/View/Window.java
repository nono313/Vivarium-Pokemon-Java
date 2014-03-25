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

/**
 * General window used for the graphical user interface.
 */
public class Window extends JFrame {
	/** Window is a singleton */ 
	private static Window instance;
	
	private Environment env;
	
	/** Main pane containing the world */
	private WorldPanel mainPane;
	
	/** Right panel with informations about the current actions and the user's interactions */
	private JPanel rightContainer;
	
	/** News feed of the actions happening between the pokemon */
	private NewsPanel news;
	
	/** Selection panel used for the user's interactions */
	private SelectionPanel selectionPanel;
	
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
	    
	    mainPane.setBackground(Color.blue);   
	    
	    news = NewsPanel.getInstance();
	    news.setBackground(Color.red);
	    selectionPanel = new SelectionPanel();
	    rightContainer = new JPanel();
	    rightContainer.setLayout(new BorderLayout());
	    rightContainer.add(news, BorderLayout.CENTER);
	    rightContainer.add(selectionPanel, BorderLayout.SOUTH);
	    
	    rightContainer.setPreferredSize(new Dimension(300, this.getHeight()));
	    
	    setLayout(new BorderLayout());
	    
	    add(mainPane, BorderLayout.CENTER);
	    add(rightContainer, BorderLayout.LINE_END);

		this.setVisible(true);
		(new Thread(news)).start();
		instance = this;
	}
	
	/* Getters and setters */
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
