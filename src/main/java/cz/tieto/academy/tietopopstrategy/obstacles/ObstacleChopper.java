package cz.tieto.academy.tietopopstrategy.obstacles;
import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Move;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.gameobject.Field;

public class ObstacleChopper implements ExecutableStrategy{
	
	@Override
	public Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap){
		
		Field nextField = fieldList.get(0);
		
		if(nextField.getObstacle().getProperty("opening").equals("false")){
			return new Heal();
		}
		
		return Util.actionBasedOnOrientation(princeInstance, Move.JUMPFORWARD, Move.JUMPBACKWARD);
	}
}
