package cz.tieto.academy.tietopopstrategy;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import cz.tieto.princegame.common.GameStrategy;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.JumpForward;
import cz.tieto.princegame.common.action.MoveForward;
import cz.tieto.princegame.common.gameobject.Equipment;
import cz.tieto.princegame.common.gameobject.Field;
import cz.tieto.princegame.common.gameobject.Obstacle;
import cz.tieto.princegame.common.gameobject.Prince;


public class RavineTest {

	@Test
	public void notGettingStuckInTheRightMostField(){
		Map<String,GameStrategy> gameStrategies = TestUtil.buildStrategies();
		
		for(Entry<String, GameStrategy> mapEntry : gameStrategies.entrySet()){
			notFallingIntoRavine(mapEntry.getKey(), mapEntry.getValue());
		}
	}
	
	private void notFallingIntoRavine(String gameStrategyName, GameStrategy argStrategy){
		//GIVEN: The prince stands in front of ravine
		Prince princeMock = mock(Prince.class);
		Collection<Equipment> ravineCollection = new ArrayList<>();
		Equipment equipmentPlank = mock(Equipment.class);
		ravineCollection.add(equipmentPlank);
		when(equipmentPlank.getName()).thenReturn("plank");
		when(princeMock.getInventory()).thenReturn(ravineCollection);
		
		Field ravineFieldMock = mock(Field.class);
		Obstacle ravineObstacleMock = mock(Obstacle.class);
		when(ravineObstacleMock.getProperty("bridged")).thenReturn("false");
		when(ravineObstacleMock.getName()).thenReturn("ravine");
		when(ravineFieldMock.getObstacle()).thenReturn(ravineObstacleMock);
		
		when(princeMock.look(1)).thenReturn(ravineFieldMock);
		
		Field fieldMockCurrent = mock(Field.class);
		when(princeMock.look(0)).thenReturn(fieldMockCurrent);
		
		when(princeMock.look(-1)).thenReturn(null);
		
		
		//WHEN:  He is going to make a step
		Action action = argStrategy.step(princeMock);
		//THEN:  He dooes not step into ravine
		assertFalse(action instanceof MoveForward);
		//AND: He does not jump into ravine
		assertFalse(action instanceof JumpForward);
	}
	
}
