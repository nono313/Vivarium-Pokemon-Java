package model;
import java.util.EnumMap;
import java.util.List;
public class TypeAttack {
	private Type type;
	private EnumMap<Type,Float> efficiency = new EnumMap<Type, Float>(Type.class);
	public TypeAttack(Type type) {
		this.type=type;
		switch(type){
		case Combat:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 2);
			efficiency.put(Type.Insecte,(float) 0.5);
			efficiency.put(Type.Normal,(float) 2);
			efficiency.put(Type.Plante,(float) 1);
			efficiency.put(Type.Poison,(float) 0.5);
			efficiency.put(Type.Psy,(float) 0.5);
			efficiency.put(Type.Roche,(float) 2);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 0);
			efficiency.put(Type.Vol,(float) 0.5);
			break;
		case Dragon:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 2);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 1);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 1);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 1);
			break;
		case Eau:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 0.5);
			efficiency.put(Type.Eau,(float) 0.5);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 2);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 0.5);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 2);
			efficiency.put(Type.Sol,(float) 2);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 1);
			break;
		case Electricite:
			efficiency.put(Type.Combat, (float) 1);
			efficiency.put(Type.Dragon,(float) 0.5);
			efficiency.put(Type.Eau,(float) 2);
			efficiency.put(Type.Electricite,(float) 0.5);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 0.5);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 1);
			efficiency.put(Type.Sol,(float) 0);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 2);
			break;
		case Feu:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 0.5);
			efficiency.put(Type.Eau,(float) 0.5);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 0.5);
			efficiency.put(Type.Glace,(float) 2);
			efficiency.put(Type.Insecte,(float) 2);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 2);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 0.5);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 1);
			break;
		case Glace:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 2);
			efficiency.put(Type.Eau,(float) 0.5);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 0.5);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 2);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 1);
			efficiency.put(Type.Sol,(float) 2);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 2);
			break;
		case Insecte:
			efficiency.put(Type.Combat,(float) 0.5);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 0.5);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 2);
			efficiency.put(Type.Poison,(float) 2);
			efficiency.put(Type.Psy,(float) 2);
			efficiency.put(Type.Roche,(float) 1);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 0.5);
			efficiency.put(Type.Vol,(float) 0.5);
			break;
		case Normal:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 1);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 0.5);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 0);
			efficiency.put(Type.Vol,(float) 1);
			break;
		case Plante:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 0.5);
			efficiency.put(Type.Eau,(float) 2);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 0.5);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 0.5);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 0.5);
			efficiency.put(Type.Poison,(float) 0.5);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 2);
			efficiency.put(Type.Sol,(float) 2);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 0.5);
			break;
		case Poison:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 2);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 2);
			efficiency.put(Type.Poison,(float) 0.5);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 0.5);
			efficiency.put(Type.Sol,(float) 0.5);
			efficiency.put(Type.Spectre,(float) 0.5);
			efficiency.put(Type.Vol,(float) 1);
			break;
		case Psy:
			efficiency.put(Type.Combat,(float) 2);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 1);
			efficiency.put(Type.Poison,(float) 2);
			efficiency.put(Type.Psy,(float) 0.5);
			efficiency.put(Type.Roche,(float) 1);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 1);
			break;
		case Roche:
			efficiency.put(Type.Combat,(float) 0.5);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 2);
			efficiency.put(Type.Glace,(float) 2);
			efficiency.put(Type.Insecte,(float) 2);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 1);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 1);
			efficiency.put(Type.Sol,(float) 0.5);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 2);
			break;
		case Sol:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 2);
			efficiency.put(Type.Feu,(float) 2);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 0.5);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 0.5);
			efficiency.put(Type.Poison,(float) 2);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 2);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 0);
			break;
		case Spectre:
			efficiency.put(Type.Combat,(float) 1);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 1);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 1);
			efficiency.put(Type.Normal,(float) 0);
			efficiency.put(Type.Plante,(float) 1);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 0);
			efficiency.put(Type.Roche,(float) 1);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 2);
			efficiency.put(Type.Vol,(float) 1);
			break;
		case Vol:
			efficiency.put(Type.Combat,(float) 2);
			efficiency.put(Type.Dragon,(float) 1);
			efficiency.put(Type.Eau,(float) 1);
			efficiency.put(Type.Electricite,(float) 0.5);
			efficiency.put(Type.Feu,(float) 1);
			efficiency.put(Type.Glace,(float) 1);
			efficiency.put(Type.Insecte,(float) 2);
			efficiency.put(Type.Normal,(float) 1);
			efficiency.put(Type.Plante,(float) 2);
			efficiency.put(Type.Poison,(float) 1);
			efficiency.put(Type.Psy,(float) 1);
			efficiency.put(Type.Roche,(float) 0.5);
			efficiency.put(Type.Sol,(float) 1);
			efficiency.put(Type.Spectre,(float) 1);
			efficiency.put(Type.Vol,(float) 1);
			break;
		}
		
		
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public EnumMap<Type, Float> getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(EnumMap<Type, Float> efficiency) {
		this.efficiency = efficiency;
	}
	
	public float efficencyOn(Type t1){
		float eff=efficiency.get(t1);
		return eff;
	}
	
	public float efficencyOn(Type t1, Type t2){
		float eff=efficiency.get(t1)*efficiency.get(t2);
		return eff;
	}
	public float efficencyOn(List<Type> list){
		if(list.size() == 1)
			return efficencyOn(list.get(0));
		else if(list.size() == 2)
			return efficencyOn(list.get(0), list.get(1));
		return 0;
	}
	public static float compareEfficiency(Pokemon a, Pokemon b) {
		/* Test type A sur B */
		TypeAttack testAonB1 = new TypeAttack(a.getTypes().get(0));
		TypeAttack testAonB2 = null;
		float effiAonB = testAonB1.efficencyOn(b.getTypes());

		if(a.getTypes().size() == 2) {
			testAonB2 = new TypeAttack(a.getTypes().get(1));
			effiAonB *= testAonB2.efficencyOn(b.getTypes());
		}
		
		/* Test type B sur A */
		TypeAttack testBonA1 = new TypeAttack(a.getTypes().get(0));
		TypeAttack testBonA2 = null;
		float effiBonA = testBonA1.efficencyOn(b.getTypes());
		if(a.getTypes().size() == 2) {
			testBonA2 = new TypeAttack(a.getTypes().get(1));
			effiBonA *= testBonA2.efficencyOn(b.getTypes());
		}
			
		return effiAonB - effiBonA;
		/* > 0 si A a l'avantage
		 * < 0 si B a l'avantage
		 * = 0 si même coeffs
		 */
	}
	
}
