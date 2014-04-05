package cz.tieto.academy.prince.persianoffensive;

import java.util.List;

import cz.tieto.academy.prince.persianoffensive.PrinceUtil.Move;
import cz.tieto.academy.prince.persianoffensive.PrinceUtil.Orientation;
import cz.tieto.princegame.common.action.Action;
import cz.tieto.princegame.common.action.MoveBackward;
import cz.tieto.princegame.common.action.MoveForward;

public class StrategyUtil {
	
	public static Integer getGate(List<SingleMapField> mapList){
		for(int i = 0;i < mapList.size();i++){
			if(mapList.get(i).isGate())
				return i;
		}
		return null;
		
	}
	
	public static Orientation findPathTo(int end, int pos){
		if(end == pos){
			return null;
		}
		if(end < pos){
			return Orientation.LEFT;
		}
		return Orientation.RIGHT;
	}
	
	public static boolean leftEnd(List<SingleMapField> mapList){
		if(mapList.get(0).isEnd()){
			return true;
		}
		return false;
	}
	
	public static boolean rightEnd(List<SingleMapField> mapList){
		if(mapList.get(mapList.size()-1).isEnd()){
			return true;
		}
		return false;
	}
	
	public static Orientation getRandomSide(){
		if((Math.random() < 0.5)){
			return Orientation.LEFT;
		}
		return Orientation.RIGHT;
	}
	
	public static Orientation getActiveSide(List<SingleMapField> mapList, int pos){
		int size = mapList.size()/2;
		if(size == pos){
			return null;
		}
		if(size < pos){
			return Orientation.RIGHT;
		}
		return Orientation.LEFT;
	}
	
	public static Move getJumpOrMove(List<SingleMapField> mapList, int pos, Orientation ori){
		if(ori == Orientation.RIGHT){
			if(pos+2 > (mapList.size()-1)){
				return Move.WALK;
			}
			return Move.JUMP;
		}
		if(ori == Orientation.LEFT){
			if(pos-2 < 0){
				return Move.WALK;
			}
			return Move.JUMP;
		}
		throw new IllegalArgumentException("Orientation cant be null");
	}
	
}
