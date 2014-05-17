package cz.tieto.academy.tietopopstrategy.obstacles;

import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Move;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.gameobject.Field;

public class ObstaclePitfall implements ExecutableStrategy{
	
	@Override
	public Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap){

		return Util.actionBasedOnOrientation(princeInstance, Move.JUMPFORWARD, Move.JUMPBACKWARD);
	}
}