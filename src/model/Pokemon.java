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

public abstract class Pokemon extends Living {
	protected short hp; //points de vie du Pokémon
	protected short height; //taille du pokémon, correspondant a la taille en pied
	protected short vision; //longueur du champ de vision
	
	protected short level; //niveau du pokémon
	protected int experience; //experience du pokemon
	protected int expUp; //experience necessaire pour pour augmenter de niveau
	protected CurveType expCurve; //courbe d'experience determinant expup
	protected int baseExperience; //experience de base donne lorsque le pokemon est vaincu
	
	protected boolean beingAttacked = false; // boolean gerant le clignotement du pokemon

	protected EnumMap<Feature, Short> features = new EnumMap<Feature, Short>(Feature.class); //statistiques du pokemon
	//niveau faisant varier les stats du pokemon durant le combat
	protected EnumMap<Feature, Short> featureLevels = new EnumMap<Feature, Short>(Feature.class);
	//effort value de chaque statistique, servant dans le calcul des statistiques
	protected EnumMap<Feature, Short> effortValue = new EnumMap<Feature, Short>(Feature.class);
	//statistiques de base du pokemon servant dans le calcul des statistiques
	protected EnumMap<Feature, Short> statsBase = new EnumMap<Feature,Short>(Feature.class); 
	
	/*
	 * level pour evoluer et forme d'evolution, si evolutionLevel est a MAXVALUE et
	 * que nextEvolution n'est pas null le pokemon n'evolue qu'avec une pierre
	 */
	protected short evolutionLevel;
	protected Class<? extends Pokemon> nextEvolution;
	
	//map des interets du pokemon le plus haut sera la destination
	protected Coord destination;
	protected Map<Element, Short> interest = new TreeMap<Element, Short>();

	protected List<Type> types = new ArrayList<Type>(); //type du pokemon
	
	protected Map<Attack, Short> attacks = new HashMap<Attack, Short>();//attaque que le pokemon peut debloquer et niveau de debloquage
	private List<Attack> attacksAvailable = new ArrayList<Attack>();//attaque disponible
	
	protected Movement lookingAt;//direction du pokemon

	protected AccessibleBackgrounds accessibleBackground = new AccessibleBackgrounds();//background accessible du Pokemon


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
		
		//le random sert a donner des statistiques differentes a deux pokemons de la meme espece
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
	//met a jour les Features
	public void updateFeatures() {
		updateFeature(Feature.Attack);
		updateFeature(Feature.Defense);
		updateFeature(Feature.Speed);
		updateFeature(Feature.Special);
		updateFeature(Feature.HpMax);
	}
	//met a jour une Feature f en fonction des maps statsBase et effortValue
	public void updateFeature(Feature f){
		if(f==Feature.HpMax){
			features.put(f,(short)((statsBase.get(f)+Math.sqrt(effortValue.get(f)+3)/4)*level/100+level+10));
		}
		else{
			features.put(f,(short)((statsBase.get(f)+Math.sqrt(effortValue.get(f)+3)/4)*level/100+5));
		}
	}
	//met a jour la liste des attaques disponibles
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
	//differencie les pokemons n'etant pas d'une forme d'origine
	public static boolean isOriginalForm() {
		return false;
	}
	//met a jour le maxCycle servant a gerer la vitesse de deplacement du pokemon
	public void updateMaxCycle() {
		this.maxCycle = (int) ((255 - getFeatureValue(Feature.Speed)) / 4 + 10);
	}
	
	//met a jour l'experience necessaire pour monter en niveau
	public void updateExpUp() {
		expUp = expNeeded(level);
	}
	
	//calcule l'experience necessaire pour monter en niveau en fonction de la courbe d'experience
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
	
	/*
	 * met a jour le niveau du pokemon (avec les changement correspondant
	 * les stats, l'experience necessaire...
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
	
	public void getValuesFrom(Pokemon poke) {
		featureLevels = poke.featureLevels;
		level = poke.level;
		updateAttacksAvailable();
		/*
		 * Recupere les donnees de PP et PP max des attaques toujours
		 * presentes dans le pokemon evolue
		 */
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
	//experience gagne a la fin d'un combat
	public void gainExp(Pokemon defeated) {
		int gain = (int) (defeated.getBaseExperience() * defeated.getLvl());
		this.experience += gain;
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
		/*
		 * set les HP en vérifiant qu'il ne dépasse pas le niveau maximum
		 */
		if(hp>this.getFeatureValue(Feature.HpMax)){
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

	public void setFeatureLevel(Feature f, short accuracyLevel) {
		featureLevels.put(f, accuracyLevel);
	}
	
	public short getFeatureLevel(Feature f) {
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
	public void setEffortValue(Feature f, short s) {
		/*set l'EfforValue d'une feature en sachant que
		 * -une EV ne peut dépasser 255
		 * -la somme de toute les EV ne peut dépasser 510
		 */
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

	public short sumEffortValue() {
		short sum = 0;
		for (Feature f : Feature.values()) {
			sum += effortValue.get(f);
		}
		return sum;
	}

	public Map<Element, Short> getInterest() {
		return interest;
	}
	//met a jour les interest
	public void setInterest() {
		Map<Element,Short> newInterest=new TreeMap<Element,Short>();
		List<Element> oldInterest = new ArrayList<Element>();
		int i;
		List<Coord> visionZone = visionZone(); // on cree le champ de vision
		short value = 0;
		if (interest.isEmpty() == false) {// MAJ des interet existants
			Set<Element> keys = interest.keySet();
			Iterator<Element> it = keys.iterator();
			while (it.hasNext()) {
				Element key = it.next();
				value=-1;
				// ajoute l'interet a oldInterest si le pokemon est sur sa case
				if (key.getPosition().equals(position)) {
					oldInterest.add(key);
				}
				// MAJ de la value de chaque cle de la Map interest
				else if (key instanceof UsableItem) {
					value = (short) (((UsableItem) key).getBaseInterest() - (key
							.getPosition().distanceTo(position)));
				} 
				else if(key instanceof Pokemon){
					//si le pokemon n'est pas en combat ou selectionne par l'utilisateur
					if(key.isActive()==false){
						//si le pokemon n'est pas dans la meme direction que la vitesse du pokémon est deux fois superieur a celle de l'adversaire
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

				if (value > 0) {// insertion de la nouvelle valeur dans la Map
					newInterest.put(key, value);
					if (key instanceof Pokemon) {// remove si key est un Pokemon et sort du champ de vison
						if (!visionZone.contains(key.getPosition())) {
							oldInterest.add(key);
						}
						//remove si le Pokemon adverse est tourné dans la même direction et une vitesse proche
						if(((Pokemon)(key)).getLookingAt().equals(lookingAt)&&((Pokemon)(key)).getFeatureValue(Feature.Speed)>0.7*getFeatureValue(Feature.Speed)){
							oldInterest.add(key);
						}
					}
				} else {// ajoute a oldInterest key si la value est egal a
						// 0 ou negative
					oldInterest.add(key);
				}
			}
			interest.putAll(newInterest);
			// supprime toute les valeurs de oldInterest
			for (i = 0; i < oldInterest.size(); ++i) {
				interest.remove(oldInterest.get(i));
			}
		}

		Iterator<Coord> it2 = visionZone.iterator();
		while (it2.hasNext()) { // creation des nouveaux interet
			Coord c = (Coord) it2.next();
			Element el = env.getAt(c);
			value=-1;
			if (el != null) {	
				// ajoute el dans interest avec un value en fonction de sa classe
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
		// si interest est non vide, cherche l'interet le plus fort
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
			 // change la destination avec la Coord du premier intï¿½rï¿½t
			setDestination(dest);
		} else {
			setDestination(null);
		}
		
	}
	public short getLvl() {
		return level;
	}

	public void setLvl(short lvl) {
		this.level = lvl;
	}

	private Attack hasAttack(Feature f, boolean b) {
		short comp = 0;
		Attack att_tmp = null;
		List<Attack> tmp = new ArrayList<Attack>();
		//cherche une attaque affectant le feature f et ayant un AffectItself = b
		for (Attack att : attacksAvailable) {
			if (att.isAffectItself() == b && att.getAffectOn() == f) {
				tmp.add(att);
			}
		}
		//si tmp est non vide cherche la plus puissante
		if (tmp.size() != 0) {
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

	private Attack hasAttack(Pokemon poke) {
		Attack att_tmp = null;
		List<Attack> atts = new ArrayList<Attack>();
		short comp = 0;
		//cherche une attaque agissant sur les PV adverse
		for (Attack att : attacksAvailable) {
			if (att.isAffectItself() == false
					&& att.getAffectOn() == Feature.HpMax) {
				atts.add(att);
			}
		}
		//cherche l'attaque la plus efficace sur le pokémon adverse
		for (Attack att : atts) {
			if (att.getType().efficencyOn(poke.getTypes()) * att.getPower() > comp
					&& att.getPowerPoint() != 0) {
				att_tmp = att;
			}
		}
		return att_tmp;
	}

	private Attack chooseAttack(Pokemon poke) {
		Attack tmp = null;
		//defense trop faible
		if (getFeatureValue(Feature.Defense)
				/ poke.getFeatureValue(Feature.Attack) < 0.4) {
			tmp = hasAttack(Feature.Attack, false); //cherche a baisser attaque adverse
			if (tmp == null) {//si pas d'attaque
				tmp = hasAttack(Feature.Defense, true); //cherche a augmenter sa defense
				if (tmp == null) {//si pas d'attaque
					tmp = hasAttack(Feature.Accuracy, false); //cherche a baisser la precision adverse
				}
			}
		}
		//si pas d'attaque
		if (tmp == null) {
			//attaque trop faible
			if(features.get(Feature.Attack)
					/ poke.getFeatureValue(Feature.Defense) < 0.4){
				tmp = hasAttack(Feature.Defense, false);//cherche a baisser defense adverse
				if(tmp==null){//si pas d'attaque
					tmp=hasAttack(Feature.Attack,true); //cherche a augmenter son attaque
				}
			}
			//si pas d'attaque
			if(tmp==null){
				//si vitesse trop faible
				if (getFeatureValue(Feature.Speed)
						/ poke.getFeatureValue(Feature.Speed) < 0.4) {
					tmp = hasAttack(Feature.Speed, false);//cherche a baisser vitesse adverse
					if (tmp == null) {//si pas d'attaque
						tmp = hasAttack(Feature.Speed, true);//cherche a augmenter sa vitesse
					}
				}
				//si pas d'attaque
				if (tmp == null) {
					//si special trop faible
					if (getFeatureValue(Feature.Special)
							/ poke.getFeatureValue(Feature.Special) < 0.4) {
						tmp = hasAttack(Feature.Special, false);//cherche a baisser special adverse
						if (tmp == null) {//si pas d'attaque
							tmp = hasAttack(Feature.Special, true);//cherche a augmenter son special
						}
					}
					if (tmp == null) {//si pas d'attaque
						//si vie <40%
						if (hp < 0.4 * getFeatureValue(Feature.HpMax)) {
							tmp = hasAttack(Feature.HpMax, true);//cherche a se soigner
						}
						//si pas d'attaque
						if (tmp == null) {
							tmp = hasAttack(poke);//cherche attaque offensive
						}
					}
				}
			}
			

		}
		if(tmp == null) {//si pas d'attaque lance Lutte (attaque par défaut)
			tmp = Lutte.getInstance();
		}
		//return la premiere attaque trouvee
		return tmp;
	}
	//lance une attaque sur poke
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

	public boolean canGoTo(Coord c) {
		//accessible si background accessible et case vide ou contenant un item utilisatable
		return c.isOnMap(env)
				&& accessibleBackground.contains(env.getBackground().get(c)) 
				&& (env.getAt(c) == null);
	}

	public List<Movement> directionAvailable() {//case accessible autour du pokemon
		List<Movement> possibleMovement = new ArrayList<Movement>();
		for (Movement m : Movement.values()) {
			Coord test = position.plus(m);
			if (canGoTo(test)) {
				possibleMovement.add(m);
			}
		}
		return possibleMovement;
	}
	
	public Coord nextPlaceAstar(){
		/*closedList = liste des cases avec leur parent dans le chemin
		 * tmpMap = liste des cases avec la case qui les a "ouverte"
		 * openList = liste des cases suceptible d'appartenir au chemin
		 */
		Map<Coord,Coord> closedList= new TreeMap<Coord,Coord>();
		Map<Coord,Coord> tmpMap = new TreeMap<Coord,Coord>();
		Map<Coord, Integer> openList= new TreeMap<Coord,Integer>();
		int value = Integer.MAX_VALUE;
		Coord tmp = null,current_position = position;
		while(current_position.equals(destination)==false){
			value=Integer.MAX_VALUE;
			tmp=null;
			for(Movement m : Movement.values()){//ajoute les cases adjacentes dans openList
				if(canGoTo(current_position.plus(m))&&closedList.containsKey(current_position.plus(m))==false){
					openList.put(current_position.plus(m), destination.distanceTo(current_position.plus(m)));
					tmpMap.put(current_position.plus(m),current_position);
				}
			}
			for(Coord c : openList.keySet()){//cherche la case la plus proche de la destination et n'étant pas dans le chemin
				if(openList.get(c)<value&&closedList.containsKey(c)==false){
					tmp = c;
					value = openList.get(c);
				}
			}
			if(tmp!=null){//ajoute tmp à la closedList
				closedList.put(tmp, tmpMap.get(tmp));
				current_position=tmp;
			}
			else{
				return null;
			}
		}
		while(closedList.get(closedList.get(tmp))!=null){//cherche la case suivante dans le chemin
			tmp=closedList.get(tmp);
		}
		return tmp;
	}
	public Coord nextPlace() {
		Coord newC = null;
		boolean test = false;
		if (destination != null) {// cherche la prochaine Coord pour aller ï¿½
									// la destination
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

		if (test == false) {// choisis alï¿½atoirement une Coord s'il n'y a pas
							// de destination
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
	//remet les levels à 0
	private void initLevels() {
		setFeatureLevel(Feature.Accuracy, (short) 0);
		setFeatureLevel(Feature.Attack, (short) 0);
		setFeatureLevel(Feature.Defense, (short) 0);
		setFeatureLevel(Feature.Speed, (short) 0);
		setFeatureLevel(Feature.Special, (short) 0);
	}

	public void changeFeatureLevel(Feature f, short n) {
		short currentLevel = featureLevels.get(f);
		featureLevels.put(f, (short) (currentLevel + n));
	}
	//renvoie le pourcentage à appliquer sur la statistiques
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

	private List<Coord> visionZone() {// creeune liste de Coord simulant le
										// champ de vision du Pokemon
		int i, k;
		Coord newC = null;
		List<Coord> visionZone = new ArrayList<Coord>();

		if (lookingAt == Movement.Down || lookingAt == Movement.Up) {// ajoute
																		// les
																		// deux
																		// cases
																		// a
																		// cote
																		// du
																		// Pokemon
			visionZone.add(position.plus(Movement.Right));
			visionZone.add(position.plus(Movement.Left));
		} else {
			visionZone.add(position.plus(Movement.Up));
			visionZone.add(position.plus(Movement.Down));
		}

		for (i = 1; i <= vision; i++) {// creeun cone de vision devant le
										// Pokemon
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
				if (newC!=null&&newC.isOnMap(env)) {
					visionZone.add(newC);
				}
				k++;
			}
		}

		List<Coord> hidden = hideZone(visionZone); // cree une liste des
													// coord cachees par les
													// obstacles

		for (i = 0; i < hidden.size(); ++i) { // supprime les Coord cachees
												// par des obstacles
			if (visionZone.contains(hidden.get(i))) {
				visionZone.remove(hidden.get(i));
			}
		}
		return visionZone;
	}

	public List<Attack> getAttacksAvailable() {
		return attacksAvailable;
	}

	public void setAttacksAvailable(List<Attack> attacksAvailable) {
		this.attacksAvailable = attacksAvailable;
	}

	private List<Coord> hideZone(List<Coord> visionZone) {
		List<Coord> obstacle = new ArrayList<Coord>();
		List<Coord> hidden = new ArrayList<Coord>();
		int i, j, k, l, obstacleSize;

		for (i = 0; i < visionZone.size(); ++i) {// ajoute a hidden les Coord
													// contenant des objets
													// inutilisables
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

		for (i = 0; i < obstacleSize; ++i) {// ajoute les cases caches par un
											// objet inutilisable a hidden
			Coord c = obstacle.get(i);
			Coord diff = c.minus(position);

			switch (lookingAt) {// traitement en fonction de LookingAt
			case Up:// traitement en fonction de la position de la Coord et de
					// la position du Pokemon
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
}
