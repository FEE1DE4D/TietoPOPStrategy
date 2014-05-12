package obstacles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.gameobject.Field;

public class ObstaclePitfall {
	public static Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> c = Class.forName("cz.tieto.academy.tietopopstrategy.PrinceClass");
		Method  ifForwardMethod = c.getDeclaredMethod ("princeJumpForward", null);
		Method  ifBackwardMethod = c.getDeclaredMethod ("princeJumpBackward", null);
		return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
	}
}