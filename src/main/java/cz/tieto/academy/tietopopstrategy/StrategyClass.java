package cz.tieto.academy.tietopopstrategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import obstacles.ObstacleChopper;
import obstacles.ObstacleDragon;
import obstacles.ObstacleKnight;
import obstacles.ObstaclePitfall;
import obstacles.ObstacleThornbush;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.EnterGate;
import cz.tieto.princegame.common.action.Grab;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;
import cz.tieto.princegame.common.gameobject.Prince;

public class StrategyClass {
	
	public static Action getMove(List<MapField> gameMap, PrinceClass princeInstance) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		int princePosition = princeInstance.getPrincePosition();
		
		Action currentFieldAction = solveCurrentField(gameMap.get(princePosition).getFieldInstance());
		if(currentFieldAction != null){
			return currentFieldAction;
		}
		
		Field nextField = getNextField(gameMap, princeInstance);
		Field nextNextField = getNextNextField(gameMap, princeInstance);
		Field previousField = getPreviousField(gameMap, princeInstance);
		Field previousPreviousField = getPreviousPreviousField(gameMap, princeInstance);
		Class<?> c = Class.forName("cz.tieto.academy.tietopopstrategy.PrinceClass");
		
		if(princeInstance.getAmmountToHeal() > 0){
			if(nextNextField != null){
				Obstacle nextNextObstacle = nextNextField.getObstacle();
				if(nextNextObstacle != null && nextNextObstacle.getName().equals(Util.DRAGON)){
					if(previousField != null && previousField.getObstacle() != null){
						String obstacleName = previousField.getObstacle().getName();
						if(obstacleName.equals(Util.PITFALL) || (obstacleName.equals(Util.CHOPPER) && previousField.getObstacle().getProperty("opening").equals("true"))){
							Method  ifForwardMethod = c.getDeclaredMethod ("princeJumpBackward", null);
							Method  ifBackwardMethod = c.getDeclaredMethod ("princeJumpForward", null);
							return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
						}
						if(obstacleName.equals(Util.CHOPPER) && previousField.getObstacle().getProperty("opening").equals("false")){
							return new Heal();
						}
					}
					Method  ifForwardMethod = c.getDeclaredMethod ("princeMoveBackward", null);
					Method  ifBackwardMethod = c.getDeclaredMethod ("princeMoveForward", null);
					return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
					
				}
			}
			return princeInstance.princeHeal();
		}
		if(nextField == null){
			 princeInstance.switchPrinceOrientation();
			 return getMove(gameMap, princeInstance);
		}
		
		Obstacle nextFieldObstacle = nextField.getObstacle();
		if (nextFieldObstacle != null){
			if(!isObstacleDead(nextFieldObstacle)){
				List<Field> fieldList = new ArrayList<Field>();
				fieldList.add(nextField);
				fieldList.add(nextNextField);
				fieldList.add(previousField);
				fieldList.add(previousPreviousField);
				return solveObstacle(nextFieldObstacle, fieldList, princeInstance, gameMap);
			}
		}
		
		
		if(nextNextField != null){
			Obstacle nextNextObstacle = nextNextField.getObstacle();
			if(nextNextObstacle == null || isObstacleDead(nextNextObstacle)){
				Method  ifForwardMethod = c.getDeclaredMethod ("princeJumpForward", null);
				Method  ifBackwardMethod = c.getDeclaredMethod ("princeJumpBackward", null);
				return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
			}
		}
		
		Method  ifForwardMethod = c.getDeclaredMethod ("princeMoveForward", null);
		Method  ifBackwardMethod = c.getDeclaredMethod ("princeMoveBackward", null);
		return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
		
		
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
	
	public static Action solveObstacle(Obstacle nextFieldObstacle,List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String obstacleName = nextFieldObstacle.getName();
		
		switch(obstacleName){
		case Util.CHOPPER:
			return ObstacleChopper.executeStrategy(fieldList, princeInstance, gameMap);
		case Util.PITFALL:
			return ObstaclePitfall.executeStrategy(fieldList, princeInstance, gameMap);
		case Util.KNIGHT:
			return ObstacleKnight.executeStrategy(fieldList, princeInstance, gameMap);
		case Util.DRAGON:
			return ObstacleDragon.executeStrategy(fieldList, princeInstance, gameMap);
		case Util.THORNBUSH:
			return ObstacleThornbush.executeStrategy(fieldList, princeInstance, gameMap);
		default:
			throw new IllegalArgumentException("Unknown obstacle");
		}
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
