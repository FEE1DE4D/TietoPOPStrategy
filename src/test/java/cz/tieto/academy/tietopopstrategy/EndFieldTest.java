package cz.tieto.academy.tietopopstrategy;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import cz.tieto.academy.tietopopstrategy.Main;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.GameStrategy;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.JumpForward;
import cz.tieto.princegame.common.action.MoveForward;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Prince;


public class EndFieldTest {
	
	@Test
	public void notGettingStuckInTheRightMostField(){
		Map<String,GameStrategy> gameStrategies = new HashMap<>();
		
		Main forwardStrategy = new Main();
		forwardStrategy.princeInstance.setPrinceOrientation(Orientation.FORWARD);
		
		Main backwardStrategy = new Main();
		forwardStrategy.princeInstance.setPrinceOrientation(Orientation.FORWARD);
		
		gameStrategies.put("forwardStrategy", forwardStrategy);
		gameStrategies.put("backwardStrategy", backwardStrategy);
		
		for(Entry<String, GameStrategy> mapEntry : gameStrategies.entrySet()){
			notGettingStuckInTheRightMostField(mapEntry.getKey(), mapEntry.getValue());
		}
	}
	
	public void notGettingStuckInTheRightMostField(String gameStrategyName, GameStrategy argStrategy){
		//GIVEN: The prince stands at the right most field
		Prince princeMock = mock(Prince.class);
		
		when(princeMock.look(1)).thenReturn(null);
		
		Field fieldMockCurrent = mock(Field.class);
		when(princeMock.look(0)).thenReturn(fieldMockCurrent);
		
		Field fieldMockBackward = mock(Field.class);
		when(princeMock.look(-1)).thenReturn(fieldMockBackward);
		
		
		//WHEN:  He is going to make a step
		Action action = argStrategy.step(princeMock);
		//THEN:  He dooes not move in the forward direction
		assertFalse(action instanceof MoveForward);
		//AND:   He does not jump in the foward direction
		assertFalse(action instanceof JumpForward);
	}
}
