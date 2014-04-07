package cz.tieto.academy.prince.persianoffensive;

import java.util.LinkedList;
import java.util.List;

import cz.tieto.academy.prince.persianoffensive.zaloha.SingleMapField;
import cz.tieto.princegame.common.gameobject.Field;

/*
 *Class representing game map
 */
public class MapClass {
	
	/*
	 * Singleton instance of MapClass
	 */
	private static MapClass instance;
	
	/*
	 * Game map
	 */
	private List<MapField> mapList;
	
	private String emptyTile  = "|_|";
	private String gateTile   = "|G|";
	private String princeTile = "|P|";
	private String endTile    = "|E|";
	private String obsTile   =  "|O|";
	
	/*
	 * Constructor
	 */
	private MapClass(){
		
		mapList = new LinkedList<>();
	}
	
	public static MapClass getInstance() {
		
	      if(instance == null) {
	         instance = new MapClass();
	      }
	      return instance;
	   }
	
	
	public List<MapField> getMap(){
		
		return mapList;
	}
	public void UpdateMap(PrinceClass princeInstance){
		
		FieldOfSight tempFos = princeInstance.lookAround();
		Field actualField = tempFos.getActualField();
		List<Field> leftSide = tempFos.getLeftSide();
		List<Field> rightSide = tempFos.getRightSide();
		int counter = 0;
		
		/*
		 * Convert list to linkedlist
		 */
		LinkedList<MapField> updatedMapList = new LinkedList<>(mapList);
		
		/*
		 * First turn, maplist is empty -> initialize it with the field prince is currently standing on. It cant be null - no reason to check.
		 * If its not empty ( not first turn ), set actual field prince is standing on to field we obtained by look(0)
		 */
		if(mapList.isEmpty()){
			
			updatedMapList.addLast(new MapField(actualField));
		}
		else{
			
			/*
			 * Checks if prince jumped out of known map
			 */
			int princePosition = princeInstance.getCurrentPosition();
			if(princePosition == updatedMapList.size()){
				
				updatedMapList.addLast(new MapField(actualField));
			}
			else if(princePosition == -1){
				
				updatedMapList.addFirst(new MapField(actualField));
				princeInstance.incCurrentPosition();
			}
			else{
				
				updatedMapList.set(princeInstance.getCurrentPosition(), new MapField(actualField));
			}
		}
		
		/*
		 * Process both left and right side and update map accordingly
		 */
		for(Field f : leftSide){
			if(f == null){
				
				updatedMapList.get(princeInstance.getCurrentPosition() - counter).setEnd(true);
				break;
			}
			
			try{
				
				updatedMapList.set(princeInstance.getCurrentPosition() - counter - 1, new MapField(f));
			}
			catch(IndexOutOfBoundsException e){
			
				updatedMapList.addFirst(new MapField(f));
				PrinceClass.getInstance().incCurrentPosition();
			}
			counter++;
		}
		
		counter = 0;
		
		for(Field f : rightSide){
			
			if(f == null){
				
				updatedMapList.get(PrinceClass.getInstance().getCurrentPosition() + counter).setEnd(true);
				break;
			}
			
			try{
				
				updatedMapList.set(princeInstance.getCurrentPosition() + counter + 1, new MapField(f));
			}
			catch(IndexOutOfBoundsException e){
			
				updatedMapList.addLast(new MapField(f));
			}
			counter++;
		}
		
		/*
		 * Update map
		 */
		mapList = updatedMapList;
	}
	
	
	/*
	 * Simple function for creating graphic representation of map
	 */
	public void drawMap(){
		int counter = 0;
		for(MapField mf : mapList){
			
			if(counter == PrinceClass.getInstance().getCurrentPosition()){
				
				System.out.print(princeTile);
			}
			else if(mf.isGate()){
				
				System.out.print(gateTile);
			}
			else if(mf.isEnd()){
				
				System.out.print(endTile);
			}
			else if(mf.getObstacleName() != null){
				
				System.out.print(obsTile);
			}
			else{
				
				System.out.print(emptyTile);
			}
			
			counter++;
		}
		System.out.println("");
	}
	
}
