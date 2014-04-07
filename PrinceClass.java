package cz.tieto.academy.prince.persianoffensive;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import cz.tieto.academy.prince.persianoffensive.StrategyClass.Move;
import cz.tieto.academy.prince.persianoffensive.StrategyClass.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.action.JumpBackward;
import cz.tieto.princegame.common.action.JumpForward;
import cz.tieto.princegame.common.action.MoveBackward;
import cz.tieto.princegame.common.action.MoveForward;
import cz.tieto.princegame.common.action.Use;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Prince;

/*
 *Class representing prince
 */
public class PrinceClass {
	
	/*
	 * Singleton instance of NewPrinceClass
	 */
	private static PrinceClass instance;
	
	/*
	 * Actual instance of Prince class ( dont mix with this PrinceClass)
	 */
	private Prince princeInstance;
	
	/*
	 * Prince current position
	 */
	private int currentPosition = 0;

	/*
	 * How far prince see
	 */
	private final int sightDistance = 1;
	
	private int healAmmount = 0;
	
	public static PrinceClass getInstance() {
		
	      if(instance == null) {
	         instance = new PrinceClass();
	      }
	      return instance;
	   }
	
	/*
	 * Constructor
	 */
	private PrinceClass(){
	}
	
	/*
	 * Updates prince instance, must be called every turn 
	 */
	public void updatePrinceInstance(Prince argInstance){
		
		princeInstance = argInstance;
	}
	
	/*
	 * Return prince field of sight
	 */
	public FieldOfSight lookAround(){
		
		/*
		 * FieldOfSight object we return and left/right side lists we pass
		 */
		FieldOfSight returnFos = new FieldOfSight();
		List<Field> leftSide = new LinkedList<>();
		List<Field> rightSide = new LinkedList<>();
		
		/*
		 * No reason to check for null, look(0) never returns null;
		 */
		returnFos.setActualField(princeInstance.look(0));
		
		
		/*
		 * Set left and right side of prince field of sight. leftSide and rightSide first element is look(1), second look(2) etc.
		 */
		for(int i = 1;i <= sightDistance;i++ ){
			leftSide.add(princeInstance.look(-i));
			rightSide.add(princeInstance.look(i));
		}
		
		returnFos.setLeftSide(leftSide);
		returnFos.setRightSide(rightSide);
		
		return returnFos;
		
	}
	
	
	public Equipment getEquipment(String equipmentName){
		Collection<Equipment> princeInventory = princeInstance.getInventory();
		for(Equipment e : princeInventory){
			if(e.getName().equals(equipmentName)){
				return e;
			}
		}
		return null;
	}
	
	/*
	 * Functions for managing prince movement
	 */
	public Action moveRight(){
		
		incCurrentPosition();
		return new MoveForward();
	}
	
	public Action jumpRight(){
		
		incCurrentPosition();
		incCurrentPosition();
		return new JumpForward();
	}
	
	public Action moveLeft(){
		
		decCurrentPosition();
		return new MoveBackward();
	}
	
	public Action jumpLeft(){
		
		decCurrentPosition();
		decCurrentPosition();
		return new JumpBackward();
	}
	
	public Action getMoveBySide(Orientation side, Move move){
		if(move == Move.HEAL){
			return new Heal();
		}
		if(move == Move.SWORD_KNIGHT_RIGHT){
			return new Use(instance.getEquipment("sword"),MapClass.getInstance().getMap().get(getCurrentPosition()+1).getObstacleInstance());
		}
		if(move == Move.SWORD_KNIGHT_LEFT){
			return new Use(instance.getEquipment("sword"),MapClass.getInstance().getMap().get(getCurrentPosition()-1).getObstacleInstance());
		}
		if(side == Orientation.RIGHT){
			
			if(move == Move.WALK){
			    return moveRight();
			}
			if(move == Move.REVERSE_WALK){
				return moveLeft();
			}
			if(move == Move.JUMP){
				return jumpRight();
			}
			if(move == Move.REVERSE_JUMP){
				return jumpLeft();
			}
			throw new IllegalArgumentException("omgbazinga");
		}
		else{
			
			if(move == Move.WALK){
			    return moveLeft();
			}
			if(move == Move.REVERSE_WALK){
				return moveRight();
			}
			if(move == Move.JUMP){
				return jumpLeft();
			}
			if(move == Move.REVERSE_JUMP){
				return jumpRight();
			}
			throw new IllegalArgumentException("omgbazinga");
		}
	}
	
	public boolean shoudHeal(){
		if(healAmmount > 0){
			healAmmount--;
			return true;
		}
		return false;
	}
	/*
	 * Getters and setters
	 */
	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	public void decCurrentPosition() {
		this.currentPosition--;
	}
	
	public void incCurrentPosition() {
		this.currentPosition++;
	}
	
	public int getHealth(){
		return princeInstance.getHealth();
	}
	
	public void setHealAmmount(int ammount){
		healAmmount = ammount;
	}
}
