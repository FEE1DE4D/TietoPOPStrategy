package obstacles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.StrategyClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Use;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;

public class ObstacleThornbush {
	public static Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		Field nextField = fieldList.get(0);
		Class<?> c = Class.forName("cz.tieto.academy.tietopopstrategy.PrinceClass");
		Method  ifForwardMethod = c.getDeclaredMethod ("princeMoveForward", null);
		Method  ifBackwardMethod = c.getDeclaredMethod ("princeMoveBackward", null);
		if(nextField.getObstacle().getProperty("burnt").equals("true"))
		{
			return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
		}
		else{
			Equipment equipmentMatches = Util.getEquipment(princeInstance.getInventory(), "matches");
			if(equipmentMatches == null){
				princeInstance.switchPrinceOrientation();
				return StrategyClass.getMove(gameMap, princeInstance);
			}
			return new Use(equipmentMatches, nextField.getObstacle());
		}
	}
}
