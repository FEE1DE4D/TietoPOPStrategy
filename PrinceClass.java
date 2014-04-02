package cz.tieto.academy.prince.persianoffensive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cz.tieto.academy.prince.persianoffensive.PrinceUtil.Orientation;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Prince;

public class PrinceClass {
	
	public static enum Moves{
		MOVEFORWARD, MOVEBACKWARD, JUMPFORWARD, JUMPBACKWARD, HEAL, GRAB, USE;
	}
	
	private static PrinceClass instance;
	private Orientation princeOrientation;
	private int currentPosition;
    public final int princeSight = 1;

	
	public static PrinceClass getInstance() {
	      if(instance == null) {
	         instance = new PrinceClass();
	      }
	      return instance;
	   }
	/**
	 * Determine prince starting direction
	 */
	private PrinceClass(){
		currentPosition = 0;
		princeOrientation = Orientation.RIGHT;
	}
	
	public Orientation getPrinceOrientation() {
		return princeOrientation;
	}

	public void setPrinceOrientation(Orientation princeOrientation) {
		this.princeOrientation = princeOrientation;
	}
	

	public int getPrinceMaxHealth(Prince princeInstance) {
		return princeInstance.getMaxHealth();
	}
	
	public int getPrinceCurrentHealth(Prince princeInstance) {
		return princeInstance.getHealth();
	}
	
	public Equipment getEquipment(Prince princeInstance, String equipmentName){
		Collection<Equipment> princeInventory = princeInstance.getInventory();
		for(Equipment e : princeInventory){
			if(e.getName().equals(equipmentName)){
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Inclusive
	 */
	public List<Field> lookAround(Prince princeInstance)
	{
		if(princeSight < 1){
			throw new IllegalArgumentException("Interval not in correct range");
		}
		List<Field> retList = new ArrayList<>();
		
		for(int i = -princeSight; i <= princeSight; i++){
			retList.add(princeInstance.look(i));
		}
		
		return retList;
	}
	
	public int getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
}
