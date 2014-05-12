package cz.tieto.academy.tietopopstrategy;

import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;

/**
 * Representation of single field of map
 */
public class MapField {
	
	private boolean isGate;
	private String equipmentName;
	private String obstacleName;
	private Field fieldInstance;
	
	public MapField(Field argField){
		isGate = argField.isGate();
		
		if(argField.getEquipment() != null){
			equipmentName = argField.getEquipment().getName();
		}
		
		if(argField.getObstacle() != null){
			obstacleName = argField.getObstacle().getName();
		}
		
		fieldInstance = argField;
	}

	public boolean isGate() {
		return isGate;
	}

	public boolean hasEquipment(){
		if(equipmentName == null){
			return false;
		}
		return true;
	}
	
	public boolean hasObstacle(){
		if(obstacleName == null){
			return false;
		}
		return true;
	}
	
	public String getEquipmentName() {
		return equipmentName;
	}

	public String getObstacleName() {
		return obstacleName;
	}
	
	public Field getFieldInstance() {
		return fieldInstance;
	}
	
	
}
