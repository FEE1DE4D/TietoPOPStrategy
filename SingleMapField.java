package cz.tieto.academy.prince.persianoffensive;


import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;


/**
 * This class represents single field in map.
 * 
 */

public class SingleMapField implements Updatable{
	
	public static final String OPENING = "opening";
	public static final String CLOSING = "closing";
	
	private String fieldEquipment;
	private MapFieldObstacle fieldObstacle;
	private boolean isGate;
	private boolean isEnd;
	
	
	
	public boolean isEnd() {
		return isEnd;
	}
	

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public String getFieldEquipment() {
		return fieldEquipment;
	}

	public void setFieldEquipment(String fieldEquipment) {
		this.fieldEquipment = fieldEquipment;
	}

	public MapFieldObstacle getFieldObstacle() {
		return fieldObstacle;
	}

	public void setFieldObstacle(Obstacle argObstacle) throws ObstaclePropertyException {
		this.fieldObstacle = new MapFieldObstacle(argObstacle);
	}

	public boolean isGate() {
		return isGate;
	}

	public void setGate(boolean isGate) {
		this.isGate = isGate;
	}
	
	public SingleMapField(){
	}

	@Override
	public void update() throws ObstaclePropertyException {
		if(fieldObstacle != null)
		{
			switch(fieldObstacle.getName())
			{
			case ObstacleUtil.CHOPPER:
				String chopperOpening = fieldObstacle.getProperty(OPENING);
				String chopperClosing = fieldObstacle.getProperty(CLOSING);
				if(chopperOpening == null || chopperClosing == null)
					throw new ObstaclePropertyException("CHOPPER fieldObstacle lacks property opening/closing");
				else
					fieldObstacle.setProperty(OPENING, PrinceUtil.reverseStringBool(OPENING));
					fieldObstacle.setProperty(CLOSING, PrinceUtil.reverseStringBool(CLOSING));
				break;
				
			default:
				break;	
			}
		}
	}
	
	public SingleMapField(Field argField) throws ObstaclePropertyException{
		if(argField.isGate())
			setGate(true);
		
		Obstacle obstacleInstance = argField.getObstacle();
		if(obstacleInstance != null)
			setFieldObstacle(obstacleInstance);
		
		Equipment equipmentInstance = argField.getEquipment();
		if(equipmentInstance != null)
			setFieldEquipment(equipmentInstance.getName());
	}
	
	public SingleMapField(Field argField, boolean isEnd) throws ObstaclePropertyException{
		this(argField);
		setEnd(true);
	}

}
