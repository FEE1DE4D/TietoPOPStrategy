package obstacles;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.gameobject.Field;

public class ObstacleChopper{
	public static Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		Field nextField = fieldList.get(0);
		if(nextField.getObstacle().getProperty("opening").equals("false")){
			return new Heal();
		}
		Class<?> c = Class.forName("cz.tieto.academy.tietopopstrategy.PrinceClass");
		Method  ifForwardMethod = c.getDeclaredMethod ("princeJumpForward", null);
		Method  ifBackwardMethod = c.getDeclaredMethod ("princeJumpBackward", null);
		return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
	}
}
