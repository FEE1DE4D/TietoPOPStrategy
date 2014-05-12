package cz.tieto.academy.tietopopstrategy.obstacles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import cz.tieto.academy.tietopopstrategy.MapField;
import cz.tieto.academy.tietopopstrategy.PrinceClass;
import cz.tieto.academy.tietopopstrategy.StrategyClass;
import cz.tieto.academy.tietopopstrategy.Util;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.action.Use;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;

public class ObstacleDragon {
	public static Action executeStrategy(List<Field> fieldList, PrinceClass princeInstance, List<MapField> gameMap) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Orientation princeOrientation = princeInstance.getPrinceOrientation();
		Field nextField = fieldList.get(0);
		Field previousField = fieldList.get(2);
		Field previousPreviousField = fieldList.get(3);
		Obstacle knightInstance = nextField.getObstacle();
		Equipment equipmentSword = Util.getEquipment(princeInstance.getInventory(), "sword");
		if(equipmentSword == null){
			princeInstance.switchPrinceOrientation();
			return StrategyClass.getMove(gameMap, princeInstance);
		}
		else{
			if(princeInstance.getHealth() > 5){
				return new Use(equipmentSword, knightInstance);
			}
			Class<?> c = Class.forName("cz.tieto.academy.tietopopstrategy.PrinceClass");
			if(previousField != null && previousField.getObstacle() != null){
				String obstacleName = previousField.getObstacle().getName();
				if(obstacleName.equals(Util.PITFALL) || (obstacleName.equals(Util.CHOPPER) && previousField.getObstacle().getProperty("opening").equals("true"))){
					princeInstance.setAmmountToHeal(princeInstance.getMaxHealth() - princeInstance.getHealth() + 1);
					Method  ifForwardMethod = c.getDeclaredMethod ("princeJumpBackward", null);
					Method  ifBackwardMethod = c.getDeclaredMethod ("princeJumpForward", null);
					return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
				}
				if(obstacleName.equals(Util.CHOPPER) && previousField.getObstacle().getProperty("opening").equals("false")){
					return new Heal();
				}
			}
			if(previousPreviousField != null){
				Obstacle previousPreviousObstacle = previousPreviousField.getObstacle();
				if(previousPreviousObstacle != null && StrategyClass.isObstacleDead(previousPreviousObstacle) == false){
					princeInstance.setAmmountToHeal(princeInstance.getMaxHealth() - princeInstance.getHealth() + 1);
					Method  ifForwardMethod = c.getDeclaredMethod ("princeMoveBackward", null);
					Method  ifBackwardMethod = c.getDeclaredMethod ("princeMoveForward", null);
					return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
				}
				princeInstance.setAmmountToHeal(princeInstance.getMaxHealth() - princeInstance.getHealth() + 1);
				Method  ifForwardMethod = c.getDeclaredMethod ("princeJumpBackward", null);
				Method  ifBackwardMethod = c.getDeclaredMethod ("princeJumpForward", null);
				return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
			}
			princeInstance.setAmmountToHeal(princeInstance.getMaxHealth() - princeInstance.getHealth() + 1);
			Method  ifForwardMethod = c.getDeclaredMethod ("princeMoveBackward", null);
			Method  ifBackwardMethod = c.getDeclaredMethod ("princeMoveForward", null);
			return Util.actionBasedOnOrientation(princeInstance, ifForwardMethod, ifBackwardMethod);
		}
	}
}
