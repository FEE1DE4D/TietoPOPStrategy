package cz.tieto.academy.tietopopstrategy;

import java.util.ArrayList;
import java.util.List;
import cz.tieto.academy.tietopopstrategy.Util.Move;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.academy.tietopopstrategy.obstacles.ExecutableStrategy;
import cz.tieto.academy.tietopopstrategy.obstacles.ObstacleChopper;
import cz.tieto.academy.tietopopstrategy.obstacles.ObstacleDragon;
import cz.tieto.academy.tietopopstrategy.obstacles.ObstacleKnight;
import cz.tieto.academy.tietopopstrategy.obstacles.ObstaclePitfall;
import cz.tieto.academy.tietopopstrategy.obstacles.ObstacleThornbush;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.EnterGate;
import cz.tieto.princegame.common.action.Grab;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;

/**
 * This class encapsulate the decision making functions
 */
public class StrategyClass {
	
	public static Action getMove(List<MapField> gameMap, PrinceClass princeInstance){
		
		int princePosition = princeInstance.getPrincePosition();
		
		Action currentFieldAction = solveCurrentField(gameMap.get(princePosition).getFieldInstance());
		if(currentFieldAction != null){
			return currentFieldAction;
		}
		
		Field nextField = getNextField(gameMap, princeInstance);
		Field nextNextField = getNextNextField(gameMap, princeInstance);
		Field previousField = getPreviousField(gameMap, princeInstance);
		Field previousPreviousField = getPreviousPreviousField(gameMap, princeInstance);
		
		List<Field> fieldList = new ArrayList<Field>();
		fieldList.add(nextField);
		fieldList.add(nextNextField);
		fieldList.add(previousField);
		fieldList.add(previousPreviousField);
		
		if(princeInstance.getAmmountToHeal() > 0){
			if(nextNextField == null || nextNextField.getObstacle() == null || !nextNextField.getObstacle().getName().equals(Util.DRAGON)){
				return princeInstance.princeHeal();
			}
			princeInstance.switchPrinceOrientation();
			Action returnAction = solveObstacle(previousField.getObstacle(), fieldList, princeInstance, gameMap);
			princeInstance.switchPrinceOrientation();
			return returnAction;
		}
		
		if(nextField == null){
			 princeInstance.switchPrinceOrientation();
			 return getMove(gameMap, princeInstance);
		}
		
		Obstacle nextFieldObstacle = nextField.getObstacle();
		if (nextFieldObstacle != null){
			if(!isObstacleDead(nextFieldObstacle)){
				return solveObstacle(nextFieldObstacle, fieldList, princeInstance, gameMap);
			}
		}
		
		
		if(nextNextField != null){
			Obstacle nextNextObstacle = nextNextField.getObstacle();
			if(nextNextObstacle == null || isObstacleDead(nextNextObstacle)){
				return Util.actionBasedOnOrientation(princeInstance, Move.JUMPFORWARD, Move.JUMPBACKWARD);
			}
		}
		return Util.actionBasedOnOrientation(princeInstance, Move.MOVEFORWARD, Move.MOVEBACKWARD);
		
		
	}
	
	public static Action solveCurrentField(Field currentField){
		if(currentField.isGate()){
			return new EnterGate();
		}
		
		if(currentField.getEquipment() != null){
			return new Grab();
		}
		return null;
		
	}
	
	public static Field getNextField(List<MapField> gameMap, PrinceClass princeInstance){
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		int princePosition = princeInstance.getPrincePosition();
		
		if(princeOrientation == Orientation.FORWARD){
			if(princePosition == gameMap.size()-1){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition+1));
		}
		
		else{
			if(princePosition == 0){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition-1));
		}
		
	}
	
	public static Field getNextNextField(List<MapField> gameMap, PrinceClass princeInstance){
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		int princePosition = princeInstance.getPrincePosition();
		
		if(princeOrientation == Orientation.FORWARD){
			if(princePosition >= gameMap.size()-2){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition+2));
		}
		
		else{
			if(princePosition <= 1){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition-2));
		}
		
	}
	
	public static Field getPreviousField(List<MapField> gameMap, PrinceClass princeInstance){
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		int princePosition = princeInstance.getPrincePosition();
		
		if(princeOrientation == Orientation.FORWARD){
			if(princePosition == 0){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition-1));
		}
		
		else{
			if(princePosition == gameMap.size()-1){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition+1));
		}
		
	}
	
	public static Field getPreviousPreviousField(List<MapField> gameMap, PrinceClass princeInstance){
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		int princePosition = princeInstance.getPrincePosition();
		
		if(princeOrientation == Orientation.FORWARD){
			if(princePosition <= 1){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition-2));
		}
		
		else{
			if(princePosition >= gameMap.size()-2){
				return null;
			}
			return nullOrFieldInstance(gameMap.get(princePosition+2));
		}
		
	}
	
	public static Action solveObstacle(Obstacle nextFieldObstacle,List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap){
		
		String obstacleName = nextFieldObstacle.getName();
		ExecutableStrategy strategy;
		
		switch(obstacleName){
		case Util.CHOPPER:
			strategy = new ObstacleChopper();
			break;
		case Util.PITFALL:
			strategy = new ObstaclePitfall();
			break;
		case Util.KNIGHT:
			strategy = new ObstacleKnight();
			break;
		case Util.DRAGON:
			strategy = new ObstacleDragon();
			break;
		case Util.THORNBUSH:
			strategy = new ObstacleThornbush();
			break;
		default:
			throw new IllegalArgumentException("Unknown obstacle");
		}
		
		return strategy.executeStrategy(fieldList, princeInstance, gameMap);
	}
	
	public static boolean isObstacleDead(Obstacle argObstacle){
		String obstacleName = argObstacle.getName();
		if(obstacleName.equals(Util.KNIGHT) || obstacleName.equals(Util.DRAGON)){
			if(argObstacle.getProperty("dead").equals("true")){
				return true;
			}
		}
		return false;
	}
	
	public static Field nullOrFieldInstance(MapField argMapField){
		
		if(argMapField != null){
			return argMapField.getFieldInstance();
		}
		return null;
	}
}
