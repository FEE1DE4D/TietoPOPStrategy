package cz.tieto.academy.tietopopstrategy;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import cz.tieto.academy.tietopopstrategy.Main;
import cz.tieto.academy.tietopopstrategy.Util.Orientation;
import cz.tieto.princegame.common.GameStrategy;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.JumpBackward;
import cz.tieto.princegame.common.action.JumpForward;
import cz.tieto.princegame.common.action.MoveBackward;
import cz.tieto.princegame.common.action.MoveForward;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;
import cz.tieto.princegame.common.gameobject.Prince;


public class EndFieldTest {
	
	private Map<String,GameStrategy> buildStrategies(){
		Map<String,GameStrategy> gameStrategies = new HashMap<>();
		
		Main forwardStrategy = new Main();
		forwardStrategy.princeInstance.setPrinceOrientation(Orientation.FORWARD);
		
		Main backwardStrategy = new Main();
		forwardStrategy.princeInstance.setPrinceOrientation(Orientation.FORWARD);
		
		gameStrategies.put("forwardStrategy", forwardStrategy);
		gameStrategies.put("backwardStrategy", backwardStrategy);
		
		return gameStrategies;
	}
	
	@Test
	public void notGettingStuckInTheRightMostField(){
		Map<String,GameStrategy> gameStrategies = buildStrategies();
		
		for(Entry<String, GameStrategy> mapEntry : gameStrategies.entrySet()){
			notGettingStuckInTheRightMostField(mapEntry.getKey(), mapEntry.getValue());
		}
	}
	
	@Test
	public void notGettingStuckInTheLeftMostfield(){
		Map<String,GameStrategy> gameStrategies = buildStrategies();
		
		for(Entry<String, GameStrategy> mapEntry : gameStrategies.entrySet()){
			notGettingStuckInTheLeftMostField(mapEntry.getKey(), mapEntry.getValue());
		}
	}
	
	@Test
	public void notGettingKilledByThePitfall(){
		Map<String,GameStrategy> gameStrategies = buildStrategies();
		
		for(Entry<String, GameStrategy> mapEntry : gameStrategies.entrySet()){
			notGettingKilledByThePitfall(mapEntry.getKey(), mapEntry.getValue());
		}
	}
	
	private void notGettingStuckInTheRightMostField(String gameStrategyName, GameStrategy argStrategy){
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
	
	private void notGettingStuckInTheLeftMostField(String gameStrategyName, GameStrategy gameStrategy){
		//GIVEN: The prince stands at the right most field
		Prince princeMock = mock(Prince.class);
		
		when(princeMock.look(-1)).thenReturn(null);
		
		Field fieldMockCurrent = mock(Field.class);
		when(princeMock.look(0)).thenReturn(fieldMockCurrent);
		
		Field fieldMockBackward = mock(Field.class);
		when(princeMock.look(1)).thenReturn(fieldMockBackward);
		
		
		//WHEN:  He is going to make a step
		Action action = gameStrategy.step(princeMock);
		//THEN:  He dooes not move in backward direction
		assertFalse(gameStrategyName, action instanceof MoveBackward);
		//AND:   He does not jump in the backward direction
		assertFalse(gameStrategyName, action instanceof JumpBackward);
	}
	
	public void notGettingKilledByThePitfall(String gameStrategyName, GameStrategy gameStrategy) {
		//GIVEN: The princ stands to the right of a pitfall
		Field pitfallField = mock(Field.class);
		Field currentField = mock(Field.class);
		Prince princeMock = mock(Prince.class)
				;
		when(princeMock.look(-1)).thenReturn(pitfallField);
		Obstacle pitfallObstacle = mock(Obstacle.class);
		when(pitfallField.getObstacle()).thenReturn(pitfallObstacle);
		when(pitfallObstacle.getName()).thenReturn("pitfall");
		when(princeMock.look(0)).thenReturn(currentField);
		when(princeMock.look(1)).thenReturn(null);
		
		//WHEN: He is going to make a step
		
		
		Action action = gameStrategy.step(princeMock);
		//THEN He does not move to the pitfall
		assertFalse(gameStrategyName, action instanceof MoveBackward);
	}
}
