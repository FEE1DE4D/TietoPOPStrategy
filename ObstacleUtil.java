package cz.tieto.academy.prince.persianoffensive;

import java.util.ArrayList;
import java.util.List;

public class ObstacleUtil {
	
	public static final String PITFALL = "pitfall";
	public static final String CHOPPER = "chopper";
	public static final String KNIGHT =  "knight";
	public static final String OPENING = "opening";
	public static final String CLOSING = "closing";
	public static final String DEAD    = "dead";
	public static final String HEALTH  = "health";
	public static final String SWORD   = "sword";
	
	private static List<String> PITFALL_PROPERTIES = new ArrayList<>();
	private static List<String> CHOPPER_PROPERTIES = new ArrayList<String>(){
		{
			add(OPENING);
			add(CLOSING);
		}
	};
	private static List<String> KNIGHT_PROPERTIES = new ArrayList<String>(){
		{
			add(DEAD);
			add(HEALTH);
		}
	};
	
	public static List<String> getPropertiesAsList(String name){
		
		switch(name){
		case PITFALL:
			return PITFALL_PROPERTIES;
		case CHOPPER:
			return CHOPPER_PROPERTIES;
		case KNIGHT:
			return KNIGHT_PROPERTIES;
		default:
			throw new IllegalArgumentException("Unknown obstacle");
		}
	}
	
}
