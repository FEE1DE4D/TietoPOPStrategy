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
		int position = instance.getCurrentPosition();
		
		if(mapLinkedList.isEmpty()){
			for(Field f : sightList){
				mapLinkedList.addLast(new SingleMapField(f));
			}
		}
		else
		{
			int size = sightList.size();
			int middle = size-instance.princeSight-1;
			mapLinkedList.set(position, new SingleMapField(sightList.get(middle)));
			
			for(int i = middle-1;i >= 0;i--){
				Field newField = sightList.get(i);
				try{
					mapLinkedList.set(position-i, new SingleMapField(newField));
				}
				catch (IndexOutOfBoundsException e){
					mapLinkedList.addFirst(new SingleMapField(newField));
				}
				if(newField == null)
					break;
			}
			
			for(int i = middle+1;i < size;i++){
				Field newField = sightList.get(i);
				try{
					mapLinkedList.set(position+i, new SingleMapField(newField));
				}
				catch (IndexOutOfBoundsException e){
					mapLinkedList.addLast(new SingleMapField(newField));
				}
				if(newField == null)
					break;
			}
		}
		
	}
	
	public void updateMap(Prince princeInstance) throws ObstaclePropertyException{
		updateUnseen();
		updateSeen(princeInstance);
	}

	
}
