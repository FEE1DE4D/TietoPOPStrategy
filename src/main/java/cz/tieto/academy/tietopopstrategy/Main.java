package cz.tieto.academy.tietopopstrategy;


import java.lang.reflect.InvocationTargetException;

import cz.tieto.princegame.common.GameStrategy;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.MoveForward;
import cz.tieto.princegame.common.gameobject.Prince;

public class Main implements GameStrategy {
	PrinceClass princeInstance = new PrinceClass();
	MapClass gameMap = new MapClass(princeInstance);
	public Action step(Prince argPrince) {
		princeInstance.setRealPrinc(argPrince);
		gameMap.updateMap(argPrince);
		gameMap.drawMap();
		System.out.println(princeInstance.getPrincePosition());
		try {
			return StrategyClass.getMove(gameMap.getMap(), princeInstance);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}