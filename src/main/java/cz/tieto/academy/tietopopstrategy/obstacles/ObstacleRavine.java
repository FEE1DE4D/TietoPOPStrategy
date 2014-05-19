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


public class ObstacleRavine implements ExecutableStrategy{
	
	/*@Override
	public Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap){

		return Util.actionBasedOnOrientation(princeInstance, Move.JUMPFORWARD, Move.JUMPBACKWARD);
	}*/
	
	@Override
	public Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap){

		Field nextField = fieldList.get(0);
		
		if(nextField.getObstacle().getProperty("bridged").equals("true")){
			return Util.actionBasedOnOrientation(princeInstance, Move.MOVEFORWARD, Move.MOVEBACKWARD);
		}
		
		Equipment equipmentPlank = Util.getEquipment(princeInstance.getInventory(), "plank");
		if(equipmentPlank != null){
			return new Use(equipmentPlank, nextField.getObstacle());
		}
		
		princeInstance.switchPrinceOrientation();
		return StrategyClass.getMove(gameMap, princeInstance);
		
	}
	
}

