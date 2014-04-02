package cz.tieto.academy.prince.persianoffensive;

import java.util.HashMap;
import java.util.Map;

import cz.tieto.princegame.common.gameobject.Obstacle;

/**
 * This class represents single obstacle
 * 
 */

public class MapFieldObstacle {
	
	
	private String obstacleName;
	private Map<String,String> obstacleProperties;
	
	public MapFieldObstacle(Obstacle obstacleInstance) throws ObstaclePropertyException{

		setName(obstacleInstance.getName());
		
		obstacleProperties = new HashMap<>();
		for(String s : ObstacleUtil.getPropertiesAsList(obstacleInstance.getName())){
			setProperty(s, obstacleInstance.getProperty(s));
		}
		
	}
	
	public String getName(){
		return obstacleName;
	}
	
	public void setName(String name){
		obstacleName = name;
	}
	
	
	public String getProperty(String name){
		return obstacleProperties.get(name);
	}
	
	public void setProperty(String name, String value){
		obstacleProperties.put(name, value);
	}


}
