package cz.tieto.academy.tietopopstrategy.obstacles;

import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.StrategyClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Move;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Use;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;

public class ObstacleBirchtree implements ExecutableStrategy{
	
	@Override
	public Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap){
		
	Field nextField = fieldList.get(0);
	Obstacle treeObstacle = nextField.getObstacle();
		
	if(treeObstacle.getProperty("burnt").equals("true"))
	{
		return Util.actionBasedOnOrientation(princeInstance, Move.MOVEFORWARD, Move.MOVEBACKWARD);
	}
	
	if(treeObstacle.getProperty("felled").equals("true")){
		Equipment equipmentPlank = Util.getEquipment(princeInstance.getInventory(), "plank");
		if(equipmentPlank != null){
			if(princeInstance.isPrinceMovedFlag())
			{
				return Util.actionBasedOnOrientation(princeInstance, Move.JUMPFORWARD, Move.JUMPBACKWARD);
			}
			
			princeInstance.switchPrinceOrientation();
			return StrategyClass.getMove(gameMap, princeInstance);
		}
		Equipment equipmentSaw = Util.getEquipment(princeInstance.getInventory(), "saw");
		if(equipmentSaw != null){
			return new Use(equipmentSaw, nextField.getObstacle());
		}
		princeInstance.switchPrinceOrientation();
		return StrategyClass.getMove(gameMap, princeInstance);
	}
	
	Equipment equipmentPlank = Util.getEquipment(princeInstance.getInventory(), "plank");
	
	if(equipmentPlank == null){
		Equipment equipmentAxe = Util.getEquipment(princeInstance.getInventory(), "axe");
		if(equipmentAxe != null){
			return new Use(equipmentAxe, nextField.getObstacle());
		}
		Equipment equipmentMatches = Util.getEquipment(princeInstance.getInventory(), "matches");
		if(equipmentMatches != null){
			return new Use(equipmentMatches, nextField.getObstacle());
		}
		
		princeInstance.switchPrinceOrientation();
		return StrategyClass.getMove(gameMap, princeInstance);
	}
	
	Equipment equipmentMatches = Util.getEquipment(princeInstance.getInventory(), "matches");
	if(equipmentMatches != null){
		return new Use(equipmentMatches, nextField.getObstacle());
	}
	
	Equipment equipmentAxe = Util.getEquipment(princeInstance.getInventory(), "axe");
	if(equipmentAxe != null){
		return new Use(equipmentAxe, nextField.getObstacle());
	}
	
	princeInstance.switchPrinceOrientation();
	return StrategyClass.getMove(gameMap, princeInstance);
	
	}
		
}
