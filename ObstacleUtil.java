package cz.tieto.academy.prince.persianoffensive;

import java.util.ArrayList;
import java.util.List;

public class ObstacleUtil {
	
	public static final String PITFALL = "pitfall";
	public static final String CHOPPER = "chopper";
	public static final String KNIGHT =  "knight";
	
	private static List<String> PITFALL_PROPERTIES = new ArrayList<>();
	private static List<String> CHOPPER_PROPERTIES = new ArrayList<String>(){
		{
			add("opening");
			add("closing");
		}
	};
	private static List<String> KNIGHT_PROPERTIES = new ArrayList<String>(){
		{
			add("dead");
			add("health");
		}
	};
	
	public static List<String> getPropertiesAsList(String name) throws ObstaclePropertyException{
		switch(name){
		case PITFALL:
			return PITFALL_PROPERTIES;
		case CHOPPER:
			return CHOPPER_PROPERTIES;
		case KNIGHT:
			return KNIGHT_PROPERTIES;
		default:
			throw new ObstaclePropertyException("Unknown obstacle");
		}
	}
	
}
