package View;


import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NewsPanel extends JPanel implements Runnable{
	private static NewsPanel instance = new NewsPanel();
	private boolean wakenUp;
	Queue<JLabel> informations;
	
	private NewsPanel() {
		informations = new LinkedList<JLabel>();
	}
	
	public static NewsPanel getInstance() {
		if(instance == null)
			instance = new NewsPanel();
		
		return instance;
	}
	
	public synchronized void addInfo(String s) {
		JLabel label = new JLabel(s);
		label.setPreferredSize(new Dimension(290, 14));
		//label.setFont(font);
		informations.add(label);
		this.add(label);
		if(informations.size() > 16) {
			this.remove(informations.poll());
		}
		wakenUp = true;
		this.notify();
	}

	/* 
	 * Fil d'actualite mis a  jour en temps reel
	 * Suppression de la tete si pas de mise a jour depuis plus d'2 secondes
	 */
	public synchronized void run() {
		
		while(true) {
			try {
				wakenUp = false;
				wait(2000);
				if(!wakenUp && informations.size() > 0) {
					this.remove(informations.poll());
				}
				revalidate();
				repaint();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
