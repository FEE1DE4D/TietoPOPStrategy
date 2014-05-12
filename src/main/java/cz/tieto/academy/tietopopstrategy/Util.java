package cz.tieto.academy.tietopopstrategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.gameobject.Equipment;

public class Util {

	public static enum Orientation{
		FORWARD, BACKWARD;
	}
	
	public static final String PITFALL = "pitfall";
	public static final String CHOPPER = "chopper";
	public static final String KNIGHT =  "knight";
	public static final String DRAGON =  "dragon";
	public static final String OPENING = "opening";
	public static final String CLOSING = "closing";
	public static final String DEAD    = "dead";
	public static final String SWORD   = "sword";
	public static final String MATCHES = "matches";
	public static final String THORNBUSH = "thornbush";
	
	public static Equipment getEquipment(Collection<Equipment> princeInventory, String equipmentName){
		Equipment retEquipment = null;
		for(Equipment eq: princeInventory){
			if(eq.getName().equals(equipmentName)){
				retEquipment = eq;
				break;
			}
		}
		return retEquipment;
	}
	
	public static Action actionBasedOnOrientation(PrinceClass princeInstance, Method ifForward, Method ifBackward) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		if(princeOrientation == Orientation.FORWARD){
			return (Action) ifForward.invoke(princeInstance, null);
		}
		else{
		    return (Action)ifBackward.invoke(princeInstance, null);
		}
	}
}
