package cz.tieto.academy.prince.persianoffensive;

import java.util.List;

import cz.tieto.academy.prince.persianoffensive.zaloha.MapFieldObstacle;
import cz.tieto.academy.prince.persianoffensive.zaloha.ObstacleUtil;
import cz.tieto.academy.prince.persianoffensive.zaloha.SingleMapField;
import cz.tieto.academy.prince.persianoffensive.zaloha.PrinceUtil.Move;
import cz.tieto.academy.prince.persianoffensive.zaloha.PrinceUtil.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.EnterGate;
import cz.tieto.princegame.common.action.Grab;
import cz.tieto.princegame.common.action.Heal;
import cz.tieto.princegame.common.action.Use;
import cz.tieto.princegame.common.gameobject.Obstacle;

public class StrategyClass {
	
	public static enum Orientation{
		LEFT,RIGHT;
	}
	public static enum Move{
		WALK,JUMP,REVERSE_WALK,REVERSE_JUMP,HEAL,SWORD_KNIGHT_RIGHT,SWORD_KNIGHT_LEFT;
	}
	public static enum Goal{
		GATE,SWORD;
	}
	
	/*
	 * Returns gate position or null
	 */
	public static Integer getGatePosition(List<MapField> mapList){
		
		int counter = 0;
		for(MapField mf : mapList){
			
			if(mf.isGate()){
				return counter;
			}
			counter++;
		}
		return null;
	}
	
	public static Integer getSwordPosition(List<MapField> mapList){
		int counter = 0;
		for(MapField mf : mapList){
			
			if(("sword").equals(mf.getEquipmentName())){
				return counter;
			}
			counter++;
		}
		return null;
	}
	
		
	public static Orientation findPathTo(int end, int pos){
		if(end == pos){
			return null;
		}
		if(end < pos){
			return Orientation.LEFT;
		}
		return Orientation.RIGHT;
	}
		
	public static boolean leftEnd(List<MapField> mapList){
		if(mapList.get(0).isEnd()){
			return true;
		}
		return false;
	}
		
	public static boolean rightEnd(List<MapField> mapList){
		if(mapList.get(mapList.size()-1).isEnd()){
			return true;
		}
		return false;
	}
	
	public static boolean leftKnight(List<MapField> mapList, PrinceClass princeInstance){
		
		for(int i = princeInstance.getCurrentPosition();i >= 0;i--){
			if(ObstacleUtil.KNIGHT.equals(mapList.get(i).getObstacleName()) && princeInstance.getEquipment("sword") == null){
				return true;
			}
		}
		return false;
	}
	
	public static boolean rightKnight(List<MapField> mapList, PrinceClass princeInstance){
		
		for(int i = princeInstance.getCurrentPosition();i <= mapList.size()-1;i++){
			if(ObstacleUtil.KNIGHT.equals(mapList.get(i).getObstacleName()) && princeInstance.getEquipment("sword") == null){
				return true;
			}
		}
		return false;
	}
		
	public static Orientation getRandomSide(){
		if((Math.random() < 0.5)){
			return Orientation.LEFT;
		}
		return Orientation.RIGHT;
	}
		
	public static Orientation getActiveSide(List<MapField> mapList, int pos){
		int mapSize = mapList.size();
		int size = mapSize/2;
		if(mapSize % 2 == 1){		
			if(size == pos){
				return null;
			}
		}
		if(size <= pos){
			return Orientation.RIGHT;
		}
		return Orientation.LEFT;
	}
	public static Move getMove(List<MapField> mapList, int pos, Orientation ori){
		int size = mapList.size();
		PrinceClass princeInstance = PrinceClass.getInstance();
		
		if(ori == Orientation.RIGHT){
			
			boolean isSecondVisible = (pos + 2) <= (size - 1);
			MapField tempObstacle = mapList.get(pos+1);
			String obstacleName = tempObstacle.getObstacleName();
			
			if(obstacleName == null){
				if(isSecondVisible){
					if(mapList.get(pos+2).getObstacleName() == null && mapList.get(pos+2).getEquipmentName() == null){
						return Move.JUMP;
					}
					return Move.WALK;
				}
				return Move.WALK;
			}

			if(obstacleName.equals(ObstacleUtil.PITFALL)){
				return Move.JUMP;
			}
			if(obstacleName.equals(ObstacleUtil.CHOPPER)){
					if(tempObstacle.getObstacleProperty("opening").equals("true")){
						return Move.JUMP;
					}
					return Move.HEAL;
			}
			if(obstacleName.equals(ObstacleUtil.KNIGHT)){
				int knightHealth = Integer.parseInt(tempObstacle.getObstacleProperty("health"));
				int princeHealth = princeInstance.getHealth();
				
				if(princeHealth <= knightHealth){
					princeInstance.setHealAmmount(knightHealth - princeHealth + 3);
					return reverseMove(getMove(mapList,pos,Orientation.LEFT));
				}
				return Move.SWORD_KNIGHT_RIGHT;
			}
		}
		else{
			boolean isSecondVisible = (pos - 2) >= 0;
			MapField tempObstacle = mapList.get(pos-1);
			String obstacleName = tempObstacle.getObstacleName();
			
			if(obstacleName == null){
				if(isSecondVisible){
					if(mapList.get(pos-2).getObstacleName() == null && mapList.get(pos-2).getEquipmentName() == null){
						return Move.JUMP;
					}
					return Move.WALK;
				}
				return Move.WALK;
			}
			
			if(obstacleName.equals(ObstacleUtil.PITFALL)){
				return Move.JUMP;
			}
			if(obstacleName.equals(ObstacleUtil.CHOPPER)){
					if(tempObstacle.getObstacleProperty("opening").equals("true")){
						return Move.JUMP;
					}
					return Move.HEAL;
			}
			if(obstacleName.equals(ObstacleUtil.KNIGHT)){
				int knightHealth = Integer.parseInt(tempObstacle.getObstacleProperty("health"));
				int princeHealth = princeInstance.getHealth();
				
				if(princeHealth <= knightHealth){
					princeInstance.setHealAmmount(knightHealth - princeHealth + 3);
					return reverseMove(getMove(mapList,pos,Orientation.RIGHT));
				}
				return Move.SWORD_KNIGHT_LEFT;
			}
		}
		throw new IllegalArgumentException("Some error - woud return null");
	}
	
	
	public static Action gateSearchStrategy(List<MapField> mapList, PrinceClass princeInstance){
		int currentPosition = princeInstance.getCurrentPosition();
		Integer gatePosition = getGatePosition(mapList);
		Integer swordPosition = getSwordPosition(mapList);
		
		if(princeInstance.shoudHeal()){
			return new Heal();
		}
		if(gatePosition != null){
			
			Orientation pathToGate = findPathTo(gatePosition, currentPosition);
			if(pathToGate == null){
				
				return new EnterGate();
			}
			return princeInstance.getMoveBySide(pathToGate, getMove(mapList,currentPosition,pathToGate));
		}
		if(swordPosition != null){
			
			Orientation pathToSword = findPathTo(swordPosition, currentPosition);
			if(pathToSword == null){
				return new Grab();
			}
			return princeInstance.getMoveBySide(pathToSword, getMove(mapList,currentPosition,pathToSword));
		}
		
		else{
			if(rightEnd(mapList) || rightKnight(mapList,princeInstance)){
				
				return princeInstance.getMoveBySide(Orientation.LEFT, getMove(mapList,currentPosition,Orientation.LEFT));
			}
			if(leftEnd(mapList) || leftKnight(mapList,princeInstance)){
				
				return princeInstance.getMoveBySide(Orientation.RIGHT, getMove(mapList,currentPosition,Orientation.RIGHT));
			}
			
			Orientation activeSide = getActiveSide(mapList,princeInstance.getCurrentPosition());
			if(activeSide == null){
				Orientation randomSide = getRandomSide();
				return princeInstance.getMoveBySide(randomSide,getMove(mapList,currentPosition,randomSide));
			}
			else{
				return princeInstance.getMoveBySide(activeSide, getMove(mapList,currentPosition,activeSide));
			}
			
		}
		
	}
	
	public static Move reverseMove(Move argMove){
		if(argMove == Move.WALK){
			return Move.REVERSE_WALK;
		}
		if(argMove == Move.JUMP){
			return Move.REVERSE_JUMP;
		}
		throw new IllegalArgumentException("bazinha!");
	}

}
