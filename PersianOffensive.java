package cz.tieto.academy.prince.persianoffensive;


import cz.tieto.academy.prince.persianoffensive.StrategyClass.Goal;
import cz.tieto.princegame.common.GameStrategy;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.gameobject.Prince;

public class PersianOffensive implements GameStrategy {

	private MapClass gameMap = MapClass.getInstance();
	
	public Action step(Prince argPrince) {
		PrinceClass princeInstance = PrinceClass.getInstance();
		princeInstance.updatePrinceInstance(argPrince);
		gameMap.UpdateMap(princeInstance);
		gameMap.drawMap();
		
		return StrategyClass.gateSearchStrategy(gameMap.getMap(),princeInstance);
	}
}

