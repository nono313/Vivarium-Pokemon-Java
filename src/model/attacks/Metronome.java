package model.attacks;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Attack;
import model.Coord;
import model.Environment;
import model.Movement;
import model.Pokemon;
import model.Type;

public class Metronome extends Attack {
	private static List<Class<? extends Attack>> listAttacks;
	static {
		listAttacks = new ArrayList<Class<? extends Attack>>();
		File folder = new File("src/model/attacks");
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        try {
		        	listAttacks.add((Class<? extends Attack>) Class.forName("model.attacks."+listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf('.'))));
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		    }
	}
	public Metronome() {
		super((short) Short.MAX_VALUE, (float) Float.MAX_VALUE, (short) 10, Type.Normal);
	}
	public void affect(Pokemon offender, Pokemon defender) {
		Collections.shuffle(listAttacks);
		Class<? extends Attack> chosenAttack = listAttacks.get(0);
		Constructor<? extends Attack> construct = null;
		try {
			construct = chosenAttack.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Attack actualAttack = null;
		try {
			actualAttack = construct.newInstance();
		} catch (InstantiationException
				| IllegalAccessException
				| IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actualAttack.affect(offender, defender);
	}
}