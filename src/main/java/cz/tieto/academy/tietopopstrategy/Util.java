package cz.tieto.academy.tietopopstrategy;

import java.util.Collection;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.gameobject.Equipment;

public class Util {

	public enum Orientation{
		FORWARD, BACKWARD;
	}
	
	public enum Move{
		MOVEFORWARD, MOVEBACKWARD, JUMPFORWARD, JUMPBACKWARD;
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
	public static final String MAPLETREE = "maple-tree";
	public static final String BIRCHTREE = "birch-tree";
	public static final String RAVINE = "ravine";
	public static final String PLANK = "plank";
	
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
	
	public static Action actionBasedOnOrientation(PrinceClass princeInstance, Move ifForward, Move ifBackward){
		
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		
		if(princeOrientation == Orientation.FORWARD){
			return convertMoveToAction(ifForward, princeInstance);
		}
		else{
			return convertMoveToAction(ifBackward, princeInstance);
		}
	}
	
	public static Action convertMoveToAction(Move moveToConvert, PrinceClass princeInstance){
		
		switch(moveToConvert){
		case MOVEFORWARD:
			return princeInstance.princeMoveForward();
		case MOVEBACKWARD:
			return princeInstance.princeMoveBackward();
		case JUMPFORWARD:
			return princeInstance.princeJumpForward();
		case JUMPBACKWARD:
			return princeInstance.princeJumpBackward();
		default:
			throw new IllegalArgumentException("convertMoveToAction got unknown Move as parameter");
		}
	}
}
