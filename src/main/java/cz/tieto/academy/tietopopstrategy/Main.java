package cz.tieto.academy.tietopopstrategy;



import cz.tieto.princegame.common.GameStrategy;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.gameobject.Prince;

public class Main implements GameStrategy {
	
	PrinceClass princeInstance = new PrinceClass();
	MapClass gameMap = new MapClass(princeInstance);
	
	public Action step(Prince argPrince) {
		princeInstance.setRealPrinc(argPrince);
		gameMap.updateMap(argPrince);
		gameMap.drawMap();
		return StrategyClass.getMove(gameMap.getMap(), princeInstance);
	}
}