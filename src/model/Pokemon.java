package model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import model.attacks.Lutte;
import model.items.Pierre;

/**
 * Base class of every pokemons implemented
 */
public abstract class Pokemon extends Living {
	/** Health points */
	private short hp;
	
	/** Height of the pokemon : represents a general order of the height of the pokemon */
	private short height;
	
	/** 
	 * Number used to compute the size of the viewing angle.<br>
	 * Arbitrary value fixed by us while implemented each pokemon.
	 */
	private short vision;
	
	/**
	 * Value representing the experience gained by the pokemon until now.<br>
	 * Each pokemon gains experience when it wins a fight.
	 */
	protected int experience;
	
	/** 
	 * Level of the pokemon. <br>
	 * This value increases when the pokemon gains experience.
	 */
	private short level;
	
	/**
	 * Value of experience necessary to increment the pokemon's level.<br>
	 * At each rise of level, the value of the next expUp is computed using the expCurve value.
	 */
	private int expUp;
	
	/**
	 * CurveType giving the curve (and the function) used to get the next expUp value
	 */
	protected CurveType expCurve;
	
	/**
	 * When a pokemon is vanquished, the enemy winner gains experience proportionally to this value. 
	 */
	private int baseExperience;
	
	/**
	 * Boolean value that is used by the view to make it blink.
	 */
	private boolean beingAttacked = false;

	/**
	 * A pokemon has many characteristics, all stored in this Map<Feature, Short>.<br>
	 * Theses values depends on the level of the pokemon.
	 */
	private EnumMap<Feature, Short> features = new EnumMap<Feature, Short>(Feature.class);
	
	/**
	 * featureLevels affects the effective value of the features by a percentage of bonus of malus.
	 */
	private EnumMap<Feature, Short> featureLevels = new EnumMap<Feature, Short>(Feature.class);
	
	/**
	 * Bonus and malus applied on the feature values
	 */
	private EnumMap<Feature, Short> effortValue = new EnumMap<Feature, Short>(Feature.class);
	
	/**
	 * Base statistics of the pokemon.<br>
	 * The values of features are computed from this map and the current level of the pokemon.
	 */
	private EnumMap<Feature, Short> statsBase = new EnumMap<Feature,Short>(Feature.class); 
	
	/**
	 * Level at which the pokemon evolves.<br>
	 * If its value is Short.MAX_VALUE, the pokemon has no evolution (or doesn't evolve naturally by gaining levels).
	 */
	private short evolutionLevel;
	
	/**
	 * Class of the pokemon in which this pokemon can evolve.
	 */
	protected Class<? extends Pokemon> nextEvolution;
	
	/**
	 * Map containing the elements in the viewing angle and giving each one a value representing the interest of the pokemon for this object / pokemon.<br>
	 * The pokemon will generally move to the target with the best interest value. 
	 */
	private Map<Element, Short> interest = new TreeMap<Element, Short>();
	
	/**
	 * Destination, generally computed from the interest map
	 */
	protected Coord destination;

	/**
	 * A pokemon can have either one or two types.
	 */
	protected List<Type> types = new ArrayList<Type>();
	
	/**
	 * Map representing every accessible attack and the level at which it becomes usable.
	 */
	protected Map<Attack, Short> attacks = new HashMap<Attack, Short>();
	
	/**
	 * Map containing the attacks already available at the current from the current level.
	 */
	private List<Attack> attacksAvailable = new ArrayList<Attack>();
	
	/** Direction at which the pokemon is looking. */
	private Movement lookingAt;

	/**
	 * List of background on which the pokemon can be and move on.
	 */
	protected AccessibleBackgrounds accessibleBackground = new AccessibleBackgrounds();


	public Pokemon(Environment env, Coord position, Movement lookingAt,
			short hp, short heightP, short vision, 
			short attack, short defense, short speed, short special, 
			short level, int baseExperience,
			int experience, CurveType curve, short evolutionLevel,
			Class<? extends Pokemon> next) {
		super(position, env);
		this.height = heightP;
		this.vision = vision;
		this.level = level;
		
		/*
		 * A random value is added to the base stat so that two pokemon of the same specie have different effective feature values 
		 */
		this.statsBase.put(Feature.Attack,(short) (attack*2+Math.random() % 32));
		this.statsBase.put(Feature.Defense,(short) (defense*2+Math.random() % 32));
		this.statsBase.put(Feature.Speed,(short) (speed*2+Math.random() % 32));
		this.statsBase.put(Feature.Special,(short) (special*2+Math.random() % 32));
		this.statsBase.put(Feature.HpMax,(short) (hp*2+Math.random() % 32));
		
		for(Feature f : Feature.values()) {
			this.effortValue.put(f,(short) (0));
		}
		
		features.put(Feature.Accuracy, (short) 1);
		setFeatureLevel(Feature.HpMax, (short) 0);
		
		initLevels();
		
		updateFeatures();
		
		this.hp = getFeatureValue(Feature.HpMax);
		this.evolutionLevel = evolutionLevel;
		this.destination = null;
		this.expCurve = curve;
		this.experience = expNeeded(level-1);
		this.baseExperience = baseExperience;
		this.nextEvolution = next;
		if(lookingAt == null)
			this.lookingAt = Movement.Down;
		else
			this.lookingAt = lookingAt;
		
		
		updateMaxCycle();
		updateExpUp();
	}
	/**
	 * Update every feature values
	 */
	private void updateFeatures() {
		updateFeature(Feature.Attack);
		updateFeature(Feature.Defense);
		updateFeature(Feature.Speed);
		updateFeature(Feature.Special);
		updateFeature(Feature.HpMax);
	}

	/**
	 * Update a specific value of the feature map
	 * @param f : Feature
	 */
	private void updateFeature(Feature f){
		if(f==Feature.HpMax){	// Different computation for the health point max value and the other features
			features.put(f,(short)((statsBase.get(f)+Math.sqrt(effortValue.get(f)+3)/4)*level/100+level+10));
		}
		else{
			features.put(f,(short)((statsBase.get(f)+Math.sqrt(effortValue.get(f)+3)/4)*level/100+5));
		}
	}

	/**
	 * Update the attacks available list from the current level and the list of all attacks
	 */
	public void updateAttacksAvailable() {
		Iterator<Attack> it = attacks.keySet().iterator();
		Attack tmp = null;
		getAttacksAvailable().clear();
		while (it.hasNext()) {
			tmp = it.next();
			if (attacks.get(tmp) <= level) {
				getAttacksAvailable().add(tmp);
			}
		}
	}
	
	/**
	 * Used to know if the pokemon is an evolution of an other.<br>
	 * It is overriden in the pokemon implementated that are at their basic form (e.g : Charmander/Salameche)
	 * @return boolean (false if not overriden)
	 */
	public static boolean isOriginalForm() {
		return false;
	}

	/**
	 * Update the maxCycle value by looking at the speed feature of the pokemon
	 */
	private void updateMaxCycle() {
		this.maxCycle = (int) ((255 - getFeatureValue(Feature.Speed)) / 4 + 10);
	}
	
	/**
	 * Update the expUp attribute that gives the experience needed to gain a level.
	 */
	private void updateExpUp() {
		expUp = expNeeded(level);
	}
	
	/**
	 * Compute the experience needed in order to increment its level.<br>
	 * The computation is different regarding the CurveType.
	 * @param n : current level
	 * @return int : the experience needed to reach the n+1 level.
	 */
	public int expNeeded(int n) {
		int next = Integer.MAX_VALUE;
		switch (expCurve) {
		case Slow:
			next = (int) (1.25 * Math.pow(n, 3));
			break;
		case Parabolic:
			next = (int) (1.2 * Math.pow(n, 3) - 15 * Math.pow(n, 2)
					+ 100 * n - 140);
			break;
		case Average:
			next = (int) (Math.pow(n, 3));
			break;
		case Fast:
			next = (int) (0.8 * Math.pow(n, 3));
			break;
		}
		return next;
	}
	
	/**
	 * Increment the level and update features values, attacks lists, next experience...
	 */
	public void levelUp() {
		if(level<100){
			level++;
			short hp_tmp=features.get(Feature.HpMax);
			updateFeatures();
			hp = (short) (hp+(features.get(Feature.HpMax)%hp_tmp));

			updateAttacksAvailable();
			updateMaxCycle();
			updateExpUp();
		}
	}
	
	/**
	 * Get the features values, level, attacks and experience from a pokemon sent in parameter and copy them to this.
	 * @param poke : Pokemon
	 */
	public void cloneValuesFrom(Pokemon poke) {
		featureLevels = poke.featureLevels;
		level = poke.level;
		updateAttacksAvailable();

		for (Attack a : attacksAvailable) {
			for (Attack srcA : poke.getAttacksAvailable()) {
				if (srcA.getClass().equals(a.getClass())) {
					a.setValuesFrom(srcA);
				}
			}
		}

		experience = poke.experience;
		hp = getFeatureValue(Feature.HpMax);
		updateAttacksAvailable();
		updateFeatures();
	}

	/**
	 * Compute the experience gained when a fight is won. 
	 * @param defeated
	 */
	public void gainExp(Pokemon defeated) {
		int gain = (int) (defeated.getBaseExperience() * defeated.getLvl());
		this.experience += gain;
	}
	

	
	/**
	 * Set the effort value of a feature.<br>
	 * There are some constraints that need to be respected :
	 * <li>an effort value cannot be greater than 255</li>
	 * <li>the sum of all effort values cannot be greater than 510</li>
	 * @param f : Feature
	 * @param s : short value
	 */
	public void setEffortValue(Feature f, short s) {
		short x, newStat;
		short sum = sumEffortValue();
		if (effortValue.get(f) + s >= 255) {
			x = 255;
		} else if (sum + s >= 510) {
			x = (short) (effortValue.get(f) - 510 + sum + s);
		} else {
			x = (short) (effortValue.get(f) + s);
		}
		effortValue.put(f, x);
		newStat = (short) (features.get(f) + x * level / 100.);
		features.put(f, newStat);
	}

	/**
	 * @return the sum of all effort values
	 */
	private short sumEffortValue() {
		short sum = 0;
		for (Feature f : Feature.values()) {
			sum += effortValue.get(f);
		}
		return sum;
	}

	
	/**
	 * Updates the list of interest by looking at the environment from the current position.
	 */
	public void setInterest() {
		Map<Element,Short> newInterest=new TreeMap<Element,Short>();
		List<Element> oldInterest = new ArrayList<Element>();
		int i;
		List<Coord> visionZone = visionZone(); // ocreation of field of vision
		short value = 0;
		if (interest.isEmpty() == false) { // update of the existing interests
			Set<Element> keys = interest.keySet();
			Iterator<Element> it = keys.iterator();
			while (it.hasNext()) {
				Element key = it.next();
				value=-1;
				// add the interest to oldInterest if the pokemon is on its square (and therefore beat it).
				if (key.getPosition().equals(position)) {
					oldInterest.add(key);
				}
				// Update the values of every elements of the map interest
				else if (key instanceof UsableItem) {
					value = (short) (((UsableItem) key).getBaseInterest() - (key
							.getPosition().distanceTo(position)));
				} 
				else if(key instanceof Pokemon){
					// If the pokemon is not is fight or selected by the user
					if(key.isActive()==false){
						// If the two pokemon are not looking in the same direction and the speed of this pokemon is twice as much as its adversary's speed
						if(((Pokemon)(key)).getLookingAt().equals(lookingAt)&&((Pokemon)(key)).getFeatureValue(Feature.Speed)>0.5*getFeatureValue(Feature.Speed)){
							if (((Pokemon) (key)).getLvl() < level + 5 && ((Pokemon) key).getLvl() > level - 5) {
								value = (short) (10 - key.getPosition().distanceTo(position));
		
							} else if (((Pokemon) (key)).getLvl() < level - 5) {
								value = (short) (4 - key.getPosition().distanceTo(position));
							} else {
								value = -1;
							}
						}
					}
				}

				if (value > 0) { // Insert new value to the map
					newInterest.put(key, value);
					if (key instanceof Pokemon) {	// Remove if the key is a pokemon which has gone out of the field of vision
						if (!visionZone.contains(key.getPosition())) {
							oldInterest.add(key);
						}
						// Remove if the adversary is looking in the same direction and and has a speed of comparable values
						if(((Pokemon)(key)).getLookingAt().equals(lookingAt)&&((Pokemon)(key)).getFeatureValue(Feature.Speed)>0.7*getFeatureValue(Feature.Speed)){
							oldInterest.add(key);
						}
					}
				} else { // Add an oldInterest key if the value of interest is equal to 0 or negative
					oldInterest.add(key);
				}
			}
			interest.putAll(newInterest);
			// Remove all element of oldInterest from interest
			for (i = 0; i < oldInterest.size(); ++i) {
				interest.remove(oldInterest.get(i));
			}
		}

		Iterator<Coord> it2 = visionZone.iterator();
		while (it2.hasNext()) { 	// Creation of the new interest
			Coord c = (Coord) it2.next();
			Element el = env.getAt(c);
			value=-1;
			if (el != null) {	
				// Add el in the interest map with a value corresponding to its class
				if (interest.containsKey(el)==false) {
					if (el instanceof LearningItem || el instanceof UpgradingItem||el instanceof Pierre) {
						value = (short) (((UsableItem) el).getBaseInterest() - (el
								.getPosition().distanceTo(position)));
					} else if (el instanceof HealingItem) {
						if (hp > 0.8 * features.get(Feature.HpMax)) {
							value = (short) (((UsableItem) el)
									.getBaseInterest() - ((el.getPosition()
									.distanceTo(position))));
						} else if (hp > 0.5 * features.get(Feature.HpMax)) {
							value = (short) (3 + ((UsableItem) el)
									.getBaseInterest() - (el.getPosition()
									.distanceTo(position)));
						} else if (hp > 0.2 * features.get(Feature.HpMax)) {
							value = (short) (5 + ((UsableItem) el)
									.getBaseInterest() - (el.getPosition()
									.distanceTo(position)));
						} else {
							value = (short) (9 + ((UsableItem) el)
									.getBaseInterest() - (el.getPosition()
									.distanceTo(position)));
						}
					} else if (el instanceof Pokemon&&el.isActive()==false) {
						if (((Pokemon) (el)).getLvl() < level + 5
								&& ((Pokemon) el).getLvl() > level - 5) {
							value = (short) (10 - (el.getPosition()
									.distanceTo(position)));
						} else if (((Pokemon) (el)).getLvl() < level - 5) {
							value = (short) (4 - (el.getPosition()
									.distanceTo(position)));
						}
					}
				}
				if (value > 0) {
					interest.put(el, value);
				}

			}
		}
		// If the interest map if not empty, we look for the biggest interest value in the map
		if (interest.size() != 0) {
			Iterator<Element> it = interest.keySet().iterator();
			Element firstinterest = null;
			value = -1;
			while (it.hasNext()) {
				Element key = it.next();
				
				try {
					if (value <= interest.get(key)) {
						for(Movement m : Movement.values()){
							if(canGoTo(key.getPosition().plus(m))){
								value = interest.get(key);
								firstinterest = key;
							}
						}
					}
				}catch(NullPointerException e) {}
			}
			Coord dest = null;
			if (firstinterest == null) {
				dest = null;
			} else if (firstinterest instanceof Pokemon
					|| firstinterest instanceof UsableItem) {
				int min = Integer.MAX_VALUE;
				for (Movement itMove : Movement.values()) {
					if(canGoTo(firstinterest.getPosition().plus(itMove))){
						int distance = position.distanceTo(firstinterest
							.getPosition().plus(itMove));
						if (distance < min) {
							dest = firstinterest.getPosition().plus(itMove);
							min = distance;
						}
					}
				}
			} else {
				dest = firstinterest.getPosition();
			}
			 // Change the destination to get closer to the target
			setDestination(dest);
		} else {
			setDestination(null);
		}
		
	}
	
	/**
	 * Checks if the pokemon has an attack that is affecting a specific feature and affecting either itself or the adversary (depending on the parameter b).
	 * @param f : Feature
	 * @param b : boolean compared to the affectItself variable of the attack class
	 * @return the attack if there is one, null otherwise
	 */
	private Attack hasAttack(Feature f, boolean b) {
		short comp = 0;
		Attack att_tmp = null;
		List<Attack> tmp = new ArrayList<Attack>();
		
		for (Attack att : attacksAvailable) {
			if (att.isAffectItself() == b && att.getAffectOn() == f) {
				tmp.add(att);
			}
		}
		// If an attack corresponding has been found
		if (tmp.size() == 1) {
			att_tmp = tmp.get(0);
		}
		else if(tmp.size() > 1){	// If multiple attacks have been found, we choose the more powerful
			for (Attack att : tmp) {
				if (b) {
					if (att.getPowerPoint() != 0 && att.getPower() > comp) {
						att_tmp = att;
						comp = att_tmp.getPower();
					}
				} else {
					if (att.getPowerPoint() != 0 && att.getPower() < comp) {
						att_tmp = att;
						comp = att_tmp.getPower();
					}
				}
			}
		}
		return att_tmp;
	}

	/**
	 * Checks if the pokemon has an attack able to affect the health points of the adversary (and chooses the most powerful).
	 * @param poke : adversary
	 * @return the selected attack
	 */
	private Attack hasAttack(Pokemon poke) {
		Attack att_tmp = null;
		List<Attack> atts = new ArrayList<Attack>();
		short comp = 0;
		// Looking for an attack affecting the health points
		for (Attack att : attacksAvailable) {
			if (att.isAffectItself() == false
					&& att.getAffectOn() == Feature.HpMax) {
				atts.add(att);
			}
		}
		// Looking for the most efficient attack from those found above
		for (Attack att : atts) {
			if (att.getType().efficencyOn(poke.getTypes()) * att.getPower() > comp
					&& att.getPowerPoint() != 0) {
				att_tmp = att;
			}
		}
		return att_tmp;
	}

	/**
	 * Chooses the attack to use by calling {@link #hasAttack(Pokemon)} and {@link #hasAttack(Feature, boolean)} with different parameters.
	 * @param poke : adversary
	 * @return the attack selected
	 */
	private Attack chooseAttack(Pokemon poke) {
		Attack tmp = null;
		// If the defense value is too small
		if (getFeatureValue(Feature.Defense)
				/ poke.getFeatureValue(Feature.Attack) < 0.4) {
			tmp = hasAttack(Feature.Attack, false); // Looking to lower the attack feature of the adversary
			
			if (tmp == null) {	// If no attack found
				tmp = hasAttack(Feature.Defense, true); // Looking to increase its defense value
				if (tmp == null) {	// Still nothing...
					tmp = hasAttack(Feature.Accuracy, false); // Looking to decrease the precision of the adversary
				}
			}
		}
		// No attack found yet
		if (tmp == null) {
			// If the attack value is too small
			if(features.get(Feature.Attack)
					/ poke.getFeatureValue(Feature.Defense) < 0.4){
				tmp = hasAttack(Feature.Defense, false);	// Looking to decrease the enemy defense
				if(tmp==null){
					tmp=hasAttack(Feature.Attack,true); 	// Looking to increase its own attack feature
				}
			}
			
			if(tmp==null){
				// If speed is too low
				if (getFeatureValue(Feature.Speed)
						/ poke.getFeatureValue(Feature.Speed) < 0.4) {
					tmp = hasAttack(Feature.Speed, false);	// Looking to lower the speed of the enemy
					if (tmp == null) {
						tmp = hasAttack(Feature.Speed, true);	// Looking to increase its own speed
					}
				}

				if (tmp == null) {
					// If special is too low
					if (getFeatureValue(Feature.Special)
							/ poke.getFeatureValue(Feature.Special) < 0.4) {
						tmp = hasAttack(Feature.Special, false); // Looking for lowering the adversary's special feature
						if (tmp == null) {
							tmp = hasAttack(Feature.Special, true);	// Looking to increase its own special feature
						}
					}
					
					if (tmp == null) {
						// If the health of the pokemon is lower than 40%
						if (hp < 0.4 * getFeatureValue(Feature.HpMax)) {
							tmp = hasAttack(Feature.HpMax, true);	// Looking to healing attacks
						}
						
						if (tmp == null) {
							tmp = hasAttack(poke);	// Looking for an offensive attack
						}
					}
				}
			}
			

		}
		
		if(tmp == null) {	// If no attack available (no more power point), the default attaqu called Lutte (struggle), is used
			tmp = Lutte.getInstance();
		}
		
		return tmp;
	}
	
	/**
	 * Launches an attack on an other pokemon
	 * @param poke : the adversary
	 * @return boolean value : true if the attacks succeded
	 */
	public boolean attack(Pokemon poke){
		
		Random rand = new Random();
		Attack att=chooseAttack(poke);
		att.setPowerPoint((short) (att.getPowerPoint()-1));
		float r=rand.nextFloat();
		if(att.getAccuracy() == Float.MAX_VALUE || r < att.getAccuracy() * getFeatureValue(Feature.Accuracy)){//test si attaque touche
			att.affect(this, poke);
			return true;
		}
		else
			return false;	
	}

	/**
	 * Check if the coord c is free and compatible with the accessible backgrounds of the pokemon
	 * @param c : destination coord
	 * @return boolean value saying if the movement is possible
	 */
	public boolean canGoTo(Coord c) {
		return c.isOnMap(env)
				&& accessibleBackground.contains(env.getBackground().get(c)) 
				&& (env.getAt(c) == null);
	}

	/**
	 * @return list of directions in which the pokemon can move
	 */
	public List<Movement> directionAvailable() {
		List<Movement> possibleMovement = new ArrayList<Movement>();
		for (Movement m : Movement.values()) {
			Coord test = position.plus(m);
			if (canGoTo(test)) {
				possibleMovement.add(m);
			}
		}
		return possibleMovement;
	}
	
	/**
	 * Uses the A* algorithm to compute the next square it has to go in order to join its current target (= element with the biggest value in the map interest).
	 * @return Coord of the next square
	 */
	private Coord nextPlaceAstar(){
		Map<Coord,Coord> closedList= new TreeMap<Coord,Coord>();	// List of the squares with their parents in the path
		Map<Coord,Coord> tmpMap = new TreeMap<Coord,Coord>();		// List of the squares with the square that "opened" them
		Map<Coord, Integer> openList= new TreeMap<Coord,Integer>();	// List of the squares capable of belonging to the path
		int value = Integer.MAX_VALUE;
		Coord tmp = null,current_position = position;
		
		while(current_position.equals(destination)==false){
			value=Integer.MAX_VALUE;
			tmp=null;
			for(Movement m : Movement.values()){	// Adds the adjacent squares to openList
				if(canGoTo(current_position.plus(m))&&closedList.containsKey(current_position.plus(m))==false){
					openList.put(current_position.plus(m), destination.distanceTo(current_position.plus(m)));
					tmpMap.put(current_position.plus(m),current_position);
				}
			}
			for(Coord c : openList.keySet()){	// Looking for the closest square of the destination that is not in the path
				if(openList.get(c)<value&&closedList.containsKey(c)==false){
					tmp = c;
					value = openList.get(c);
				}
			}
			if(tmp!=null){	//Adds tmp to closedList
				closedList.put(tmp, tmpMap.get(tmp));
				current_position=tmp;
			}
			else{
				return null;
			}
		}
		while(closedList.get(closedList.get(tmp))!=null){	// Looking for the next square in the path
			tmp=closedList.get(tmp);
		}
		return tmp;
	}
	
	/**
	 * Compute the coordinate of the next square.<br>
	 * @return the next Coord
	 */
	public Coord nextPlace() {
		Coord newC = null;
		boolean test = false;
		if (destination != null) {	// Looking for the next square to go nearer the destination
			if (destination.equals(position)) {
				destination = null;
			}
			else{
				newC = nextPlaceAstar();
				if(newC!=null){
					test = true;
				}
			}
		}

		if (test == false) {	// Chooses randomly a direction if there is no destination
			Random rnd = new Random();
			List<Movement> possiblemovement = directionAvailable();
			Movement m = null;
			if(possiblemovement.size()!=0){
				do {
					m = possiblemovement.get(rnd.nextInt(possiblemovement.size()));
				} while (possiblemovement.size() > 1 && m == this.lookingAt.opposite());
				newC = position.plus(m);
			}
		}
		return newC;
	}
	
	/**
	 * Initialize the feature levels at 0
	 */
	private void initLevels() {
		setFeatureLevel(Feature.Accuracy, (short) 0);
		setFeatureLevel(Feature.Attack, (short) 0);
		setFeatureLevel(Feature.Defense, (short) 0);
		setFeatureLevel(Feature.Speed, (short) 0);
		setFeatureLevel(Feature.Special, (short) 0);
	}

	/**
	 * Compute the effective value of a feature, given the feature map and the featureLevels bonus and malus
	 * @param feature
	 * @return short value of the feature
	 */
	public short getFeatureValue(Feature feature){
		short lvl = featureLevels.get(feature);
		short stats = features.get(feature);
		if(feature == Feature.Accuracy){
			switch(lvl){
			case -6:
				stats*=0.333;
				break;
			case -5:
				stats*=0.375;
				break;
			case -4:
				stats*=0.429;
				break;
			case -3:
				stats*=0.5;
				break;
			case -2:
				stats*=0.56;
				break;
			case -1:
				stats*=0.75;
				break;
			case 1:
				stats*=1.333;
				break;
			case 2:
				stats*=1.667;
				break;
			case 3:
				stats*=2;
				break;
			case 4:
				stats*=2.333;
				break;
			case 5:
				stats*=2.667;
				break;
			case 6:
				stats*=3;
				break;
			default:
				break;
			}
		}
		else{
			switch(lvl){
			case -6:
				stats*=0.25;
				break;
			case -5:
				stats*=0.29;
				break;
			case -4:
				stats*=0.33;
				break;
			case -3:
				stats*=0.40;
				break;
			case -2:
				stats*=0.50;
				break;
			case -1:
				stats*=0.67;
				break;
			case 1:
				stats*=1.5;
				break;
			case 2:
				stats*=2;
				break;
			case 3:
				stats*=2.5;
				break;
			case 4:
				stats*=3;
				break;
			case 5:
				stats*=3.5;
				break;
			case 6:
				stats*=4;
				break;
			default:
				break;
			}
		}
		return stats;
	}

	/**
	 * Create the field of vision
	 * @return list of Coord
	 */
	private List<Coord> visionZone() { 	// Create a list of Coord simulating a vision's field
		int i, k;
		Coord newC = null;
		List<Coord> visionZone = new ArrayList<Coord>();

		if (lookingAt == Movement.Down || lookingAt == Movement.Up) {	// Adds the two sides of the pokemon
			visionZone.add(position.plus(Movement.Right));
			visionZone.add(position.plus(Movement.Left));
		} else {
			visionZone.add(position.plus(Movement.Up));
			visionZone.add(position.plus(Movement.Down));
		}

		for (i = 1; i <= vision; i++) {		// Create a cone of vision in front of the pokemon
			k = -i;
			while (k <= i) {
				if(lookingAt!=null){
					switch (lookingAt) {
					case Down:
						newC = position.plus(new Coord(k, i));
						break;
					case Up:
						newC = position.plus(new Coord(k, -i));
						break;
					case Right:
						newC = position.plus(new Coord(i, k));
						break;
					case Left:
						newC = position.plus(new Coord(-i, k));
						break;
					default:
						break;
					}
				}
				if (newC != null && newC.isOnMap(env)) {
					visionZone.add(newC);
				}
				k++;
			}
		}

		List<Coord> hidden = hideZone(visionZone); 	// Create a list of coordinates hidden by objects / pokemon

		for (i = 0; i < hidden.size(); ++i) { // Remove the hidden coordinates
			if (visionZone.contains(hidden.get(i))) {
				visionZone.remove(hidden.get(i));
			}
		}
		return visionZone;
	}

	/**
	 * Compute the list of coord that are masked by an object given the viewpoint of the pokemon
	 * @param visionZone : list of Coord corresponding to the field of vision
	 * @return list of coord from the parameter without the coord hidden by objects
	 */
	private List<Coord> hideZone(List<Coord> visionZone) {
		List<Coord> obstacle = new ArrayList<Coord>();
		List<Coord> hidden = new ArrayList<Coord>();
		int i, j, k, l, obstacleSize;

		for (i = 0; i < visionZone.size(); ++i) {	// Add to hidden the coordinates of the squares containing unusable items
			Element key = env.getAt(visionZone.get(i));
			if (key != null) {
				if (key instanceof UnusableItem) {
					obstacle.add(visionZone.get(i));
				}
				if(key instanceof Pokemon && height<((Pokemon) key).getHeight()){
					obstacle.add(visionZone.get(i));
				}
			}
		}

		obstacleSize = obstacle.size();

		for (i = 0; i < obstacleSize; ++i) {	// Adds the squares behind the obstacles to the hidden list
			Coord c = obstacle.get(i);
			Coord diff = c.minus(position);

			/*
			 * Different treatement givent the direction in which the pokemon is and its position.
			 */
			switch (lookingAt) {
			case Up:
				if (diff.getX() == diff.getY() || diff.getX() == -diff.getY()
						|| diff.getX() != 0) {
					l = c.getX();
					if (diff.getX() < 0) {
						l--;
						for (j = c.getY() - 1; j >= position.getY() - vision; --j, --l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(l, j);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					} else {
						l++;
						for (j = c.getY() - 1; j >= position.getY() - vision; --j, ++l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(l, j);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				} else if (diff.getY() != 0) {
					if (diff.getX() == 0) {
						for (j = c.getY() - 1; j >= position.getY() - vision; --j) {
							for (k = 0; k < visionZone.size(); ++k) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(c.getX(), j);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				}
				break;
			case Down:
				if (diff.getX() == diff.getY() || diff.getX() == -diff.getY()
						|| diff.getX() != 0) {
					l = c.getX();
					if (diff.getX() < 0) {
						l--;
						for (j = c.getY() + 1; j <= position.getY() + vision; ++j, --l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(l, j);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					} else {
						l++;
						for (j = c.getY() + 1; j <= position.getY() + vision; ++j, ++l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(l, j);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				} else if (diff.getY() != 0) {
					if (diff.getX() == 0) {
						for (j = c.getY() + 1; j <= position.getY() + vision; ++j) {
							for (k = 0; k < visionZone.size(); ++k) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(c.getX(), j);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				}
				break;
			case Left:
				if (diff.getX() == diff.getY() || diff.getX() == -diff.getY()
						|| diff.getY() != 0) {
					l = c.getY();
					if (diff.getY() < 0) {
						l--;
						for (j = c.getX() - 1; j >= position.getX() - vision; --j, --l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(j, l);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					} else {
						l++;
						for (j = c.getX() - 1; j >= position.getX() - vision; --j, ++l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(j, l);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				} else if (diff.getX() != 0) {
					if (diff.getY() == 0) {
						for (j = c.getX() - 1; j >= position.getX() - vision; --j) {
							for (k = 0; k < visionZone.size(); ++k) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(j, c.getY());
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				}
				break;
			case Right:
				if (diff.getX() == diff.getY() || diff.getX() == -diff.getY()
						|| diff.getY() != 0) {
					l = c.getY();
					if (diff.getY() < 0) {
						l--;
						for (j = c.getX() + 1; j <= position.getX() + vision; ++j, --l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(j, l);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					} else {
						l++;
						for (j = c.getX() + 1; j <= position.getX() + vision; ++j, ++l) {
							for (k = 0; k < visionZone.size(); k++) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(j, l);
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				} else if (diff.getX() != 0) {
					if (diff.getY() == 0) {
						for (j = c.getX() + 1; j <= position.getX() + vision; ++j) {
							for (k = 0; k < visionZone.size(); ++k) {
								Coord tmp = visionZone.get(k);
								Coord test = new Coord(j, c.getY());
								if (tmp.equals(test)) {
									hidden.add(tmp);
								}
							}
						}
					}
				}
				break;
			}
		}
		return hidden;

	}
	
	/* Getters and setters */
	
	public Class<? extends Pokemon> getNextEvolution() {
		return nextEvolution;
	}

	public int getExperience() {
		return experience;
	}

	public int getBaseExperience() {
		return baseExperience;
	}

	public EnumMap<Feature, Short> getFeatures() {
		return features;
	}

	public EnumMap<Feature, Short> getFeatureLevels() {
		return featureLevels;
	}

	public int getExpUp() {
		return expUp;
	}

	public boolean isBeingAttacked() {
		return beingAttacked;
	}

	public void setBeingAttacked(boolean beingAttacked) {
		this.beingAttacked = beingAttacked;
	}

	public short getHp() {
		return hp;
	}

	public void setHp(short hp) {
		if(hp>this.getFeatureValue(Feature.HpMax)){	// Checks that the hp value is not greater than HpMax
			this.hp=this.getFeatureLevel(Feature.HpMax);
		}
		else{
			this.hp = hp;
		}
	}

	public void healHp() {
		this.hp = getFeatureValue(Feature.HpMax);
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}

	public short getVision() {
		return vision;
	}

	public void setVision(short vision) {
		this.vision = vision;
	}

	public boolean canGoOn(BackgroundType bg) {
		return accessibleBackground.contains(bg);
	}

	private void setFeatureLevel(Feature f, short accuracyLevel) {
		featureLevels.put(f, accuracyLevel);
	}
	
	private short getFeatureLevel(Feature f) {
		return featureLevels.get(f);
	}

	public short getEvolutionLevel() {
		return evolutionLevel;
	}

	public Coord getDestination() {
		return destination;
	}

	public void setDestination(Coord destination) {
		this.destination = destination;
	}

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	public Map<Attack, Short> getAttacks() {
		return attacks;
	}

	public void setAttacks(HashMap<Attack, Short> attacks) {
		this.attacks = attacks;
	}

	public Movement getLookingAt() {
		return lookingAt;
	}

	public void setLookingAt(Movement lookingAt) {
		this.lookingAt = lookingAt;
	}
	
	public EnumMap<Feature,Short> getEffortValue(){
		return effortValue;
	}
	
	public Map<Element, Short> getInterest() {
		return interest;
	}
	
	public short getLvl() {
		return level;
	}

	public void setLvl(short lvl) {
		this.level = lvl;
	}
	
	/**
	 * Change the level of a specific feature
	 * @param f : Feature
	 * @param n : value
	 */
	public void changeFeatureLevel(Feature f, short n) {
		short currentLevel = featureLevels.get(f);
		featureLevels.put(f, (short) (currentLevel + n));
	}
	
	public List<Attack> getAttacksAvailable() {
		return attacksAvailable;
	}

	public void setAttacksAvailable(List<Attack> attacksAvailable) {
		this.attacksAvailable = attacksAvailable;
	}

}
