package model;

public abstract class Attack {
	protected short power;
	protected short powerPoint;
	protected float accuracy;
	protected short powerPointMax;
	protected short powerPointBegin;
	protected boolean affectItself;	/* vrai si l'attaque affecte les stats du pokemon qui l'utilise */
	protected Feature affectOn;
	protected TypeAttack type;
	
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
	public Attack(short power, float accuracy, short powerPointMax, Type type) {	/* attaque offensive : domamge sur les hp de l'ennemi */
		this(power, accuracy, powerPointMax, type, Feature.HpMax, false);
	}
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
		if(powerPointMax<powerPoint){
			this.powerPoint=powerPointMax;
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
	
	public void affect(Pokemon offender, Pokemon defender) {
		short damage;
		if(affectItself) {	/* augmenter une stat */
			offender.changeFeatureLevel(affectOn, power);
		}
		else if(affectOn == Feature.HpMax){	/* attaque frontal sur les hp de l'adversaire */
			if(power==Short.MAX_VALUE){
				defender.setHp((short) 0);
			}
			else{
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
		else {/*dimunuer une stat*/
			defender.changeFeatureLevel(affectOn, power);
		}

	}
	public boolean equals(Attack a) {
		return this.getClass().getSimpleName().equals(a.getClass().getSimpleName());
	}
	
	
}
