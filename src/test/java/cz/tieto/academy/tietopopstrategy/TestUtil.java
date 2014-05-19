package cz.tieto.academy.tietopopstrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.GameStrategy;

public class TestUtil {
	
	public static Map<String,GameStrategy> buildStrategies(){
		
		Map<String,GameStrategy> gameStrategies = new HashMap<>();
		
		Main forwardStrategy = new Main();
		forwardStrategy.princeInstance.setPrinceOrientation(Orientation.FORWARD);
		
		Main backwardStrategy = new Main();
		forwardStrategy.princeInstance.setPrinceOrientation(Orientation.FORWARD);
		
		gameStrategies.put("forwardStrategy", forwardStrategy);
		gameStrategies.put("backwardStrategy", backwardStrategy);
		
		return gameStrategies;
	}
	
	public void testStrategies(Testable testClass){
		
		Map<String,GameStrategy> gameStrategies = buildStrategies();
		
		for(Entry<String, GameStrategy> mapEntry : gameStrategies.entrySet()){
			testClass.runTest(mapEntry.getKey(), mapEntry.getValue());
		}
	}
}
