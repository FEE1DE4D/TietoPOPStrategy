package cz.tieto.academy.prince.persianoffensive;

import cz.tieto.princegame.common.GameStrategy;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.MoveForward;
import cz.tieto.princegame.common.gameobject.Prince;

public class PersianOffensive implements GameStrategy {

	private PrinceMap gameMap = PrinceMap.getInstance();
	
	public Action step(Prince princeInstance) {
		try {
			gameMap.updateMap(princeInstance);
		} catch (ObstaclePropertyException e) {
			e.printStackTrace();
		}
		return new DefaultGateRushStrategy().execute();
	}

}
