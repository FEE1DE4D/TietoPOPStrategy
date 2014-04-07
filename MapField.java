package cz.tieto.academy.prince.persianoffensive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;

/*
 * Class representing single map field
 */
public class MapField {
	
	/*
	 * Contains gate
	 */
	private boolean isGate = false;
	
	/*
	 * End of map
	 */
	private boolean isEnd = false;
	
	/*
	 * Name of possible obstacle on this field
	 */
	private String obstacleName;
	
	private Obstacle obstacleInstance;
	
	/*
	 * Properties of possible obstacle on this field
	 */
	private Map<String,String> obstacleProperties;

	/*
	 * Name of possible equipment
	 */
	private String equipmentName;
	
	/*
	 * Constructor
	 */
	public MapField(Field argField){
		
		setGate(argField.isGate());
		
		Equipment tempEquip = argField.getEquipment();
		if(tempEquip != null){
			
			setEquipmentName(tempEquip.getName());
		}
		
		Obstacle tempObstacle = argField.getObstacle();
		if(tempObstacle != null){
			
			if(!ObstacleUtil.KNIGHT.equals(tempObstacle.getName()) || !"true".equals(tempObstacle.getProperty("dead"))){
				
				setObstacleInstance(tempObstacle);
				setObstacleName(tempObstacle.getName());
				List<String> propertyList = ObstacleUtil.getPropertiesAsList(obstacleName);
				setObstacleProperties(tempObstacle,propertyList);
			}
		}
		
	}
	
	/*
	 * Getters and setters
	 */
	public boolean isGate() {
		return isGate;
	}

	public void setGate(boolean isGate) {
		this.isGate = isGate;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	
	public void setEquipmentName(String equipmentName){
		this.equipmentName = equipmentName;
	}
	
	public String getEquipmentName(){
		return equipmentName;
	}
	
	public String getObstacleName(){
		return obstacleName;
	}
	public void setObstacleName(String obstacleName){
		this.obstacleName = obstacleName;
	}
	
	public void setObstacleProperties(Obstacle argObstacle, List<String> propertyList){
		
		obstacleProperties = new HashMap<String,String>();
		
		for(String s : propertyList){
			
			obstacleProperties.put(s, argObstacle.getProperty(s));
		}
	}
	
	public String getObstacleProperty(String argProperty){
		return obstacleProperties.get(argProperty);
	}
	
	public void setObstacleInstance(Obstacle obstacleInstance){
		this.obstacleInstance = obstacleInstance;
	}
	
	public Obstacle getObstacleInstance(){
		return obstacleInstance;
	}
}
