package cz.tieto.academy.tietopopstrategy;

import java.util.Collection;

import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.action.JumpBackward;
import cz.tieto.princegame.common.action.JumpForward;
import cz.tieto.princegame.common.action.MoveBackward;
import cz.tieto.princegame.common.action.MoveForward;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Prince;

/**
 *  This class represents our hero, his position, inventory
 */
public class PrinceClass {
	
	private int princePosition;
	private Orientation princeOrientation;
	private Prince realPrince;
	private int ammountToHeal;
	
	public PrinceClass(){
		ammountToHeal = 0;
		princePosition = 0;
		princeOrientation = Orientation.FORWARD;
	}
	
	public Orientation getPrinceOrientation(){
		return princeOrientation;
	}
	
	public void setPrinceOrientation(Orientation argOrientation){
		princeOrientation = argOrientation;
	}
	
	public void switchPrinceOrientation(){
		if(princeOrientation == Orientation.FORWARD){
			princeOrientation = Orientation.BACKWARD;
		}
		else{
			princeOrientation = Orientation.FORWARD;
		}
	}
	public int getPrincePosition(){
		return princePosition;
	}
	
	public void setPrincePosition(int pos){
		princePosition = pos;
	}
	
	public int getAmmountToHeal(){
		return ammountToHeal;
	}
	
	public void setAmmountToHeal(int argAmmount){
		ammountToHeal = argAmmount;
	}
	
	public int setPrincePositionAndReturn(int pos){
		princePosition = pos;
		return princePosition;
	}
	
	public void setRealPrinc(Prince argPrince){
		realPrince = argPrince;
	}
	
	public int getHealth(){
		return realPrince.getHealth();
	}
	
	public int getMaxHealth(){
		return realPrince.getMaxHealth();
	}
	
	public Collection<Equipment> getInventory(){
		return realPrince.getInventory();
	}
	
	public Action princeMoveForward(){
		princePosition++;
		return new MoveForward();
	}
	
	public Action princeJumpForward(){
		princePosition += 2;
		return new JumpForward();
	}
	
	public Action princeMoveBackward(){
		princePosition--;
		return new MoveBackward();
	}
	
	public Action princeJumpBackward(){
		princePosition -= 2;
		return new JumpBackward();
	}
	
	public Action princeHeal(){
	    ammountToHeal--;
	    return new Heal();
	}
	
}
