package model;

import java.util.ArrayList;
import java.util.Iterator;

import View.Window;
import controll.Controller;

public class MovementManager extends Thread {
	private Environment env;
	private Controller controll;
	
	public MovementManager(Environment env, Controller control) {
		this.env = env;
		this.controll = control;
	}
	
	public void run() {
		try {
		Thread.currentThread().setPriority(NORM_PRIORITY);
		ArrayList<Pokemon> pokemonToMove = new ArrayList<Pokemon>();
		long lastUpdate;
		int timeElapsed = 1;
		Living liv;
		while(true) {
			liv = null;
			pokemonToMove.clear();
				Iterator<Living> it = env.getListLiving().iterator();
				while(it.hasNext()) {
					liv = it.next();
					if(!liv.isActive() && liv.update(timeElapsed)){
						if(liv instanceof Pokemon) {
							pokemonToMove.add((Pokemon) liv);
						}
						else if(liv instanceof Egg) {
							((Egg) liv).hatch();
						}
					}
				}
				lastUpdate = System.nanoTime();
				
				if(!pokemonToMove.isEmpty()) {
					for(Pokemon p : pokemonToMove) {
						p.setInterest();
						Coord m = p.nextPlace();
						if(m != null)
							controll.move(p, m);

						
						if(p.getDestination() != null && p.getPosition().equals(p.getDestination())) {
							p.setDestination(null);
						}
					}
					// verifier collisions
					for(Pokemon p : pokemonToMove) {
						for(Movement m : Movement.values()) {
							Element beside = env.getAt(p.getPosition().plus(m));
							if(beside != null) {
								if(beside instanceof Pokemon) {
									if(!p.isActive() && !beside.isActive()) {
										p.setActive(true);
										beside.setActive(true);
										Fight f = new Fight(env, controll, p, (Pokemon) beside);
										f.start();
									}
								}
								else if(beside instanceof UsableItem) {
									if(!p.isActive() && !beside.isActive()) {
										beside.setActive(true);
										p.setLookingAt(p.getPosition().getDirectionTo(beside.getPosition()));
										boolean success = ((UsableItem)beside).affect(p);
										p.resetCycle();
										if(success) {
											Window.getInstance().addNews(p.getClass().getSimpleName() + " utilise " + beside.getClass().getSimpleName());
											env.putAt(null, beside.getPosition());
										}
										else {
											Window.getInstance().addNews(p.getClass().getSimpleName() + " utilise " + beside.getClass().getSimpleName());
											Window.getInstance().addNews(beside.getClass().getSimpleName() + " n'a aucun effet.");
											env.putAt(null, beside.getPosition());
										}
									}
								}
							}
						}
					}
				}
			try {
				long diff = Math.abs(System.nanoTime()-lastUpdate);
				timeElapsed = (int) (diff/(1000000*10));
				if(timeElapsed < 1)
					timeElapsed = 1;
				if(diff <= 1000000*10)
					Thread.sleep(10-diff/1000000, (int) (diff%1000000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		}catch(Exception e ) {
			e.printStackTrace();

		}
	}
	
}
