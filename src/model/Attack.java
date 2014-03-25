package model;

/**
 * Every attack implemented in the model.attacks packages inherit from this abstract class.<br>
 * It manages the general behavior of an attack.
 */
public abstract class Attack {
	/** Power of the attack */
	protected short power;
	
	/** Every time an attack is used, its powerPoint value is reduced by one */
	protected short powerPoint;
	
	/** 
	 * The accuracy is used to determine if an attack is successful each time a pokemon uses it.<br>
	 * Generally, powerful attacks have a low accuracy whereas weak ones have a accuracy of 100%
	 */
	private float accuracy;
	
	/** 
	 * Each attack has a maximum value of powerpoint.<br>
	 * It is not the original value and can only be reached by using objects.
	 */
	private short powerPointMax;
	
	/** Original powerpoint value before the attack have been used */ 
	private short powerPointBegin;
	
	/** 
	 * True if the attack affects a statistic of the pokemon who uses it.<br>
	 * False if it affects the ennemy. 
	 * */
	private boolean affectItself;
	
	/**
	 * Each attack affects a certain Feature.<br>
	 * Most of them are affecting the health points.
	 */
	private Feature affectOn;
	
	/** 
	 * Type of the attack<br>
	 * The efficiency of the attack depends on the type(s) of the pokemon it is used on.
	 */
	private TypeAttack type;
	
	/**
	 * initialize the attributes' values
	 * @param power
	 * @param accuracy
	 * @param powerPointMax
	 * @param type
	 * @param affectOn
	 * @param affectItself
	 */
	public Attack(short power, float accuracy, short powerPointMax, Type type, Feature affectOn, boolean affectItself) {
		super();
		this.power = power;
		this.powerPoint = powerPointMax;
		this.accuracy = accuracy;
		this.powerPointMax = powerPointMax;
		this.powerPointBegin = powerPointMax;
		this.type = new TypeAttack(type);
		this.affectOn = affectOn;
	}
	public Attack(short power, float accuracy, short powerPointMax, Type type, Feature affectOn) {
		this(power, accuracy, powerPointMax, type, affectOn, false);
	}
	public Attack(short power, float accuracy, short powerPointMax, Type type) {
		// If the affectOn is not specified, we give it HpMax because most of the attacks are affecting directly the health points of the ennemy
		this(power, accuracy, powerPointMax, type, Feature.HpMax, false);
	}
	
	/* Setters and getters */
	public void setValuesFrom(Attack a) {
		this.powerPoint = a.powerPoint;
		this.powerPointMax = a.powerPointMax;
	}
	public short getPower() {
		return power;
	}
	public short getPowerPoint() {
		return powerPoint;
	}
	public void setPowerPoint(short powerPoint) {
		if(powerPointMax < powerPoint){
			this.powerPoint = powerPointMax;
		}
		else{
			this.powerPoint = powerPoint;
		}
	}
	public float getAccuracy() {
		return accuracy;
	}
	public short getPowerPointMax() {
		return powerPointMax;
	}
	public void setPowerPointMax(short pp){
		this.powerPointMax=pp;
	}
	public short getPowerPointBegin(){
		return this.powerPointBegin;
	}
	public TypeAttack getType() {
		return type;
	}
	public boolean isAffectItself() {
		return affectItself;
	}
	public Feature getAffectOn() {
		return affectOn;
	}
	
	/**
	 * When an offender uses an attack in a fight on a pokemon called defender.
	 * @param offender : Pokemon using the attack
	 * @param defender : enemy pokemon
	 */
	public void affect(Pokemon offender, Pokemon defender) {
		short damage;
		if(affectItself) {	// Rising its own feature
			offender.changeFeatureLevel(affectOn, power);
		}
		else if(affectOn == Feature.HpMax){	// Attacking the ennemy's health points
			if(power==Short.MAX_VALUE){
				defender.setHp((short) 0);
			}
			else{
				/*
				 *  The type of the attack changes the way the damages are calculated.
				 *  It depends on whether the attack is physical or not.
				 *  The computation have been taken from an online database. They are directly taken from the original Pokemon games of Game Freak
				 */
				if(Type.Normal.equals(type)||Type.Combat.equals(type)||Type.Vol.equals(type)||Type.Poison.equals(type)||Type.Sol.equals(type)||Type.Roche.equals(type)||Type.Insecte.equals(type)||Type.Spectre.equals(type)){
					short powerAttack = offender.getFeatureValue(Feature.Attack);
					short powerDefense = defender.getFeatureValue(Feature.Defense);
					damage=(short) (((offender.getLvl()*0.4+2)*powerAttack*power)/(powerDefense*50)+2);
				}
				else{
					damage=(short) (((offender.getLvl()*0.4+2)*offender.getFeatureValue(Feature.Special)*power)/(defender.getFeatureValue(Feature.Special)*50)+2);
				}
				defender.setHp((short)(defender.getHp()-damage));
			}
		}
		else {	// Lower a feature of the adversary
			defender.changeFeatureLevel(affectOn, power);
		}

	}
	
	/**
	 * Compare the name of 2 attacks and say if they are the same
	 * @param a : an Attack
	 * @return : boolean value
	 */
	public boolean equals(Attack a) {
		return this.getClass().getSimpleName().equals(a.getClass().getSimpleName());
	}
	
	
}
