package cz.tieto.academy.tietopopstrategy;

import java.util.LinkedList;
import java.util.List;

import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Prince;

/*
 *  Represents game map as LinkedList of MapFields
 */
public class MapClass {
	
	private List<MapField> gameMap;
	private PrinceClass princeInstance;
	
	public MapClass(PrinceClass princeArg){
		gameMap = new LinkedList<>();
		princeInstance = princeArg;
	}
	
	/*
	 *  Takes Prince instance as argument and updates map by calling look() function
	 */
	public void updateMap(Prince argPrince){
		LinkedList<MapField> updatedGameMap = new LinkedList<>(gameMap);
		int princePosition = princeInstance.getPrincePosition();
		
		if(gameMap.isEmpty()){
			updatedGameMap.addFirst(isNullOrMapField(argPrince.look(0)));
			updatedGameMap.addFirst(isNullOrMapField(argPrince.look(-1)));
			princePosition = princeInstance.setPrincePositionAndReturn(princePosition + 1);
			updatedGameMap.addLast(isNullOrMapField(argPrince.look(1)));
		}
		else{
			if(princePosition > updatedGameMap.size()-1){
				updatedGameMap.addLast(isNullOrMapField(argPrince.look(0)));
			}
			else if(princePosition < 0){
				updatedGameMap.addFirst(isNullOrMapField(argPrince.look(0)));
				princePosition = princeInstance.setPrincePositionAndReturn(princePosition + 1);
			}
			else{
				updatedGameMap.set(princePosition, isNullOrMapField(argPrince.look(0)));
			}
			
			if(princePosition == 0){
				updatedGameMap.addFirst(isNullOrMapField(argPrince.look(-1)));
				princePosition = princeInstance.setPrincePositionAndReturn(princePosition + 1);
			}
			else{
				updatedGameMap.set(princePosition-1, isNullOrMapField(argPrince.look(-1)));
			}
			
			if(princePosition == updatedGameMap.size()-1){
				updatedGameMap.addLast(isNullOrMapField(argPrince.look(1)));
			}
			else{
				updatedGameMap.set(princePosition+1, isNullOrMapField(argPrince.look(1)));
			}
		}
		
		gameMap = updatedGameMap;
		
	}
	
	public List<MapField> getMap(){
		return gameMap;
	}
	/*
	 *  Prints a simple ascii version of map 
	 */
	public void drawMap(){
		int counter = 0;
		for(MapField mf: gameMap){
			if(mf == null){
				System.out.print("|X");
			}
			else if(princeInstance.getPrincePosition() == counter){
				System.out.print("|P");
			}
			else if(mf.isGate()){
				System.out.print("|G");
			}
			else if(mf.hasEquipment()){
				System.out.print("|E");
			}
			else if(mf.hasObstacle()){
				System.out.print("|O");
			}
			else{
				System.out.print("|_");
			}
			counter++;
		}
		System.out.print("|");
	}
	
	public MapField isNullOrMapField(Field argField){
		if(argField == null){
			return null;	
		}
		return new MapField(argField);
	}
}
