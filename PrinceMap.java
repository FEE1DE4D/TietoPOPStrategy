package cz.tieto.academy.prince.persianoffensive;

import java.util.LinkedList;
import java.util.List;

import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Prince;

public class PrinceMap {

	private static PrinceMap instance;
	private List<SingleMapField> mapList;
	
	private PrinceMap(){
		mapList = new LinkedList<>();
	}
	
	public static PrinceMap getInstance() {
	      if(instance == null) {
	         instance = new PrinceMap();
	      }
	      return instance;
	   }
	
	
	public List<SingleMapField> getMap(){
		return mapList;
	}
	private void updateUnseen() throws ObstaclePropertyException{
		for(SingleMapField i : mapList){
			i.update();
		}
	}
	
	private void updateSeen(Prince princeInstance) throws ObstaclePropertyException{
		PrinceClass instance = PrinceClass.getInstance();
		List<Field> sightList = instance.lookAround(princeInstance);
		LinkedList<SingleMapField> mapLinkedList =  new LinkedList<SingleMapField>(mapList);
		
		/*if(mapLinkedList.isEmpty()){
			for(Field f : sightList){
				mapLinkedList.addLast(new SingleMapField(f));
			}
			instance.setCurrentPosition((sightList.size()/2));
		}
		else*/
		{
			int size = sightList.size();
			int middle = size-instance.princeSight-1;
			if(!mapLinkedList.isEmpty()){
				mapLinkedList.set(instance.getCurrentPosition(), new SingleMapField(sightList.get(middle)));
			}
			else{
				mapLinkedList.addLast(new SingleMapField(sightList.get(middle)));
			}
			
			for(int i = middle-1;i >= 0;i--){
				Field newField = sightList.get(i);
				if(newField == null)
				{
					mapLinkedList.get(instance.getCurrentPosition()-i).setEnd(true);
					break;
				}
					
				try{
					int currentPosition = instance.getCurrentPosition()-i-1;
					if(mapLinkedList.get(currentPosition).isEnd()){
						mapLinkedList.set(currentPosition, new SingleMapField(newField,true));
					}
					else{
						mapLinkedList.set(currentPosition, new SingleMapField(newField));
					}
				}
				catch (IndexOutOfBoundsException e){
					mapLinkedList.addFirst(new SingleMapField(newField));
					instance.incCurrentPosition();
				}
			}
			
			for(int i = middle+1;i < size;i++){
				Field newField = sightList.get(i);
				if(newField == null)
				{
					mapLinkedList.get(instance.getCurrentPosition()+i-2).setEnd(true);
					break;
				}
				try{
					int currentPosition = instance.getCurrentPosition()+i-1;
					if(mapLinkedList.get(currentPosition).isEnd()){
						mapLinkedList.set(currentPosition, new SingleMapField(newField,true));
					}
					else{
						mapLinkedList.set(currentPosition, new SingleMapField(newField));
					}
				}
				catch (IndexOutOfBoundsException e){
					mapLinkedList.addLast(new SingleMapField(newField));
				}
			}
			
		}
		mapList = mapLinkedList;
		
	}
	
	public void updateMap(Prince princeInstance) throws ObstaclePropertyException{
		updateUnseen();
		updateSeen(princeInstance);
	}

	
	public void drawMap(){
		String emptyTile =  "|_|";
		String princeTile = "|P|";
		String gateTile =   "|G|";
		String endTile =    "|E|";
		
		int counter = 0;
		
		PrinceClass instance = PrinceClass.getInstance();
		for(SingleMapField smf : mapList){
			if(instance.getCurrentPosition() == counter){
				System.out.print(princeTile);
			}
			else if (smf.isEnd()) {
				System.out.print(endTile);
			}
			else if (smf.isGate()) {
				System.out.print(gateTile);
			}
			else{
				System.out.print(emptyTile);
			}
			counter += 1;
		}
		System.out.println("");
	}
	
}
