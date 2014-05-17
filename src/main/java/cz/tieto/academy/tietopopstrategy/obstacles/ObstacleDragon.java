package cz.tieto.academy.tietopopstrategy.obstacles;

import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.StrategyClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Move;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.action.Use;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;

public class ObstacleDragon implements ExecutableStrategy{
	
	@Override
	public Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap){
		
		Field nextField = fieldList.get(0);
		Field previousField = fieldList.get(2);
		Field previousPreviousField = fieldList.get(3);
		Obstacle dragonInstance = nextField.getObstacle();
		Equipment equipmentSword = Util.getEquipment(princeInstance.getInventory(), "sword");
		
		if(equipmentSword == null){
			princeInstance.switchPrinceOrientation();
			return StrategyClass.getMove(gameMap, princeInstance);
		}
		
		if(princeInstance.getHealth() > 5){
			return new Use(equipmentSword, dragonInstance);
		}
		
		if(previousField == null){
			return new Use(equipmentSword, dragonInstance);
		}
		
		if(previousPreviousField == null){
			return new Use(equipmentSword, dragonInstance);
		}
		
		Obstacle previousFieldObstacle = previousField.getObstacle();
		Obstacle previousPreviousFieldObstacle = previousPreviousField.getObstacle();
		if(previousFieldObstacle == null){
			if(previousPreviousFieldObstacle == null){
				return setMaxHealAndMove(Move.JUMPBACKWARD, Move.JUMPFORWARD, princeInstance);
			}
			return setMaxHealAndMove(Move.MOVEBACKWARD, Move.MOVEFORWARD, princeInstance);
		}
		
		String previousFieldObstacleName = previousFieldObstacle.getName();
		switch(previousFieldObstacleName){
		case Util.CHOPPER:
			if(previousFieldObstacle.getProperty("opening").equals("true")){
				return setMaxHealAndMove(Move.JUMPBACKWARD, Move.JUMPFORWARD, princeInstance);
			}
			return new Heal();
		default:
			return setMaxHealAndMove(Move.JUMPBACKWARD, Move.JUMPFORWARD, princeInstance);
		}
	}
	
	private Action setMaxHealAndMove(Move ifForward, Move ifBackward, PrinceClass princeInstance){
		princeInstance.setAmmountToHeal(princeInstance.getMaxHealth() - princeInstance.getHealth() + 1);
		return Util.actionBasedOnOrientation(princeInstance, ifForward, ifBackward);
	}
}
