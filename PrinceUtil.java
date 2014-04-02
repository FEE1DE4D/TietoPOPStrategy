package cz.tieto.academy.prince.persianoffensive;

public class PrinceUtil {

	
	static public enum Orientation{
		LEFT,RIGHT;
	}
	
	public static String reverseStringBool(String trueFalse){
		if(trueFalse.equals("true")){
			return "false";
		}
		if(trueFalse.equals("false")){
			return "true";
		}
		else throw new IllegalArgumentException("reverseStringBool argument invalid");
	}
	
	public static Orientation reverseOrientation(Orientation o){
		if(o == Orientation.LEFT){
			return Orientation.RIGHT;
		}
		if(o == Orientation.RIGHT){
			return Orientation.LEFT;
		}
		else throw new IllegalArgumentException("reverseOrientation argument invalid");
	}
	
}
