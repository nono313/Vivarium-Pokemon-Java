package View;


import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel located in the right panel on the top, used to display informations about what is going on in the world.
 */
public class NewsPanel extends JPanel implements Runnable{
	
	/** The NewsPanel is a singleton */
	private static NewsPanel instance = new NewsPanel();
	
	/** Used to force the update of the panel when a new information arrives */
	private boolean wakenUp;
	
	/** Queue representing the different news that need to be displayed */
	private Queue<JLabel> informations;
	
	private NewsPanel() {
		informations = new LinkedList<JLabel>();
	}
	
	public static NewsPanel getInstance() {
		if(instance == null)
			instance = new NewsPanel();
		
		return instance;
	}
	
	/**
	 * Add a news to the list of informations
	 * @param s : String of the new information
	 */
	public synchronized void addInfo(String s) {
		JLabel label = new JLabel(s);
		label.setPreferredSize(new Dimension(290, 14));
		informations.add(label);
		this.add(label);
		if(informations.size() > 16) {	// If there are already 16 elements in the queue, we remove the oldest one
			this.remove(informations.poll());
		}
		wakenUp = true;
		this.notify();
	}

	/** 
	 * News feed updated in real time.<br>
	 * Removal of elements if there is no new information during 2 seconds.
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
