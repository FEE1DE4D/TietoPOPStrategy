package cz.tieto.academy.tietopopstrategy.obstacles;

import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.gameobject.Field;

public interface ExecutableStrategy {
	
	public Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap);
}
