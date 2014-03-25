package model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import View.NewsPanel;
import View.Window;
import controll.Controller;

/**
 * Each 
 */
public class Fight extends Thread {
	private Environment env;
	private Controller control;
	private Pokemon a, b;
	private Random rnd = new Random();
	private NewsPanel news = NewsPanel.getInstance();
	private int nEscape[] = new int[]{0,0};
	private boolean runAway = false;
	private boolean endOfFight = false;
	private Pokemon pokeByOrder[] = new Pokemon[2];


	public Fight(Environment env, Controller control, Pokemon a, Pokemon b) {
		this.env = env;
		this.control = control;
		this.a = a;
		this.b = b;
	}
	
	public void run() {
		
		pokeByOrder[0] = a;
		
		/* 
		 * Par défaut, A est prioritaire. Cependant il peut arriver que A soit en position d'infériorité 
		 * et ne veuille donc pas attaquer. Dans ce cas on regarde si B a envie d'attaquer et on lance le combat.
		 */
		float diffEfficency = TypeAttack.compareEfficiency(a, b);
		/* B est derrière A : A ne voit pas B */
		if(a.getPosition().plus(a.getLookingAt().opposite()).equals(b.getPosition())) {
			pokeByOrder[0] = null;
		}
		/* Si A n'est pas derrière B */
		else if(!b.getPosition().plus(b.getLookingAt().opposite()).equals(a.getPosition())){

			if(b.getLvl()-a.getLvl() >= 10) {
				pokeByOrder[0] = b;
			}
			else {
				/* eff(A sur B) + 0.25 <= eff(B sur A) */ 
				if(diffEfficency <= -0.25) {
					pokeByOrder[0] = b;
				}
			}
		}
		else {
			pokeByOrder[0] = null;
		}
		
		
		if(pokeByOrder[0] != null) {
				/* Face à face */
				a.setLookingAt(a.getPosition().getDirectionTo(b.getPosition()));
				b.setLookingAt(b.getPosition().getDirectionTo(a.getPosition()));
			
			if(pokeByOrder[0] == a)
				pokeByOrder[1] = b;
			else
				pokeByOrder[1] = a;
			
			int i = 0;
			int j1, j2;
			float[] percentageLife = new float[2];
			percentageLife[0] =((float)pokeByOrder[0].getHp()) / pokeByOrder[0].getFeatureValue(Feature.HpMax);
			percentageLife[1] =((float)pokeByOrder[1].getHp()) / pokeByOrder[1].getFeatureValue(Feature.HpMax);

			do {
				runAway = false;
				j1 = i%2;
				j2 = (i+1)%2;
				percentageLife[j1] =((float)pokeByOrder[j1].getHp()) / pokeByOrder[j1].getFeatureValue(Feature.HpMax);				

				/* tentative de fuite si pv < 20% 
				 * ou si l'adversaire à plus de 30% de vie en plus
				 * ou si le pourcentage de vie est inférieur à celui de l'ennemi, 
				 * 		on a alors une probabilité de
				 * 		%(ennemi) - %(actuel) de tenter une fuite
				 */
				if(percentageLife[j1] < 0.1 || 
						percentageLife[j1] < percentageLife[j2]-0.8 ||
						(percentageLife[j1] < percentageLife[j2] && 
								rnd.nextFloat() < percentageLife[j2] - percentageLife[j1]) ) {
					/* tentative de fuite */
					runAway = true;
				}
				if(runAway) {
					tryToRunAway(j1, j2);
				}
				else {
					attack(j1, j2);
					/* Test si l'adversaire est mort */
					if(pokeByOrder[j2].getHp() <= 0) {
						endOfFight = true;
						news.addInfo(pokeByOrder[j2].getClass().getSimpleName() + " est mort !");
						env.remove(pokeByOrder[j2]);
					}
				}
				i++;
			}while(!endOfFight);
			if(!runAway) {
				pokeByOrder[j1].gainExp(pokeByOrder[j2]);
				/* On boucle tant qu'on peut monter de niveau */
				while(pokeByOrder[j1].getExperience() >= pokeByOrder[j1].getExpUp()) {
					control.levelUp(pokeByOrder[j1]);
				}
				
			}			
		}
		else {
			// Pas de combat
		}
		a.setActive(false);
		b.setActive(false);
	}
	
	public void setRunAway(boolean runAway) {
		this.runAway = runAway;
	}

	/* tentative de fuite */
	private void tryToRunAway(int j1, int j2) {
		nEscape[j1]++;
		float bCalcul = (float) (pokeByOrder[j2].getFeatureValue(Feature.Speed)/4. % 255);
		int f;
		if(bCalcul == 0) {
			f = 256;
		}
		else {
			 f = (int) ((pokeByOrder[j1].getFeatureValue(Feature.Speed) * 32)/bCalcul + 30 * nEscape[j1]);
		}
		if(f > 255 || rnd.nextInt(256) < f) {
			news.addInfo(pokeByOrder[j1].getClass().getSimpleName() +" prend la fuite");
			Coord opposite = pokeByOrder[j1].getPosition().plus(pokeByOrder[j1].getLookingAt().opposite());
			if(pokeByOrder[j1].canGoTo(opposite)) {
				pokeByOrder[j1].resetCycle();
				control.move(pokeByOrder[j1], opposite);
			}
			else {
				List<Movement> directionsToRunAway = pokeByOrder[j1].directionAvailable();
				if(directionsToRunAway.size() > 0) {
					Collections.shuffle(directionsToRunAway);
					opposite = pokeByOrder[j1].getPosition().plus(directionsToRunAway.get(0));
					control.move(pokeByOrder[j1], opposite);
				}
			}
			
			endOfFight = true;
			runAway = true;
		}
		else {
			/* echec de la fuite */
			runAway = false;
		}
	}
	private void attack(int j1, int j2) {
		/* Si l'attaque fonctionne */
		if(pokeByOrder[j1].attack(pokeByOrder[j2])) {
			news.addInfo(pokeByOrder[j1].getClass().getSimpleName() + " attacks " + pokeByOrder[j2].getClass().getSimpleName() + " !");
		}
		else {
			news.addInfo(pokeByOrder[j1].getClass().getSimpleName() + " attacks " + pokeByOrder[j2].getClass().getSimpleName() + "");
			news.addInfo("\tmais l'attaque de " + pokeByOrder[j1].getClass().getSimpleName() + " a échouée.");

		}
		/* Visualisation de l'attaque : pokemon attaqué clignotte */
		pokeByOrder[j2].setBeingAttacked(true);
		Window.getInstance().getMainPane().repaint();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pokeByOrder[j2].setBeingAttacked(false);
		Window.getInstance().getMainPane().repaint();
	}
	
}
