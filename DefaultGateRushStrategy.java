package cz.tieto.academy.prince.persianoffensive;



import java.util.List;

import cz.tieto.academy.prince.persianoffensive.PrinceUtil.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.EnterGate;
import cz.tieto.princegame.common.action.MoveBackward;
import cz.tieto.princegame.common.action.MoveForward;

public class DefaultGateRushStrategy implements Executable{
	
	List<SingleMapField> gameMap = PrinceMap.getInstance().getMap();
	
	@Override
	public Action execute() {
		Integer gatePosition = StrategyUtil.getGate(gameMap);
		PrinceClass princeInstance = PrinceClass.getInstance();
		int pos = princeInstance.getCurrentPosition();
		if(gatePosition != null){
			Orientation gateWay = StrategyUtil.findPathTo(gatePosition, princeInstance.getCurrentPosition());
			if(gateWay == null){
				return new EnterGate();
			}
			else{
				Orientation way = StrategyUtil.findPathTo(gatePosition,pos);
				return princeInstance.getMoveBySide(way, StrategyUtil.getJumpOrMove(gameMap, pos, way));
			}
		}
		else{
			if(StrategyUtil.leftEnd(gameMap)){
				return princeInstance.getMoveBySide(Orientation.RIGHT,StrategyUtil.getJumpOrMove(gameMap, pos, Orientation.RIGHT));
			}
			if(StrategyUtil.rightEnd(gameMap)){
				return princeInstance.getMoveBySide(Orientation.LEFT,StrategyUtil.getJumpOrMove(gameMap, pos, Orientation.LEFT));
			}
			Orientation activeSide = StrategyUtil.getActiveSide(gameMap, princeInstance.getCurrentPosition());
			Orientation returnSide;
			if(activeSide == null){
				returnSide = StrategyUtil.getRandomSide();
			}
			else{
				returnSide = activeSide;
			}
			return princeInstance.getMoveBySide(returnSide, StrategyUtil.getJumpOrMove(gameMap, pos, returnSide));
		}
	}

	
}
