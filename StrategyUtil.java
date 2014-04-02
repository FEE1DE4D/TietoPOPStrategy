package cz.tieto.academy.prince.persianoffensive;

import java.util.List;

public class StrategyUtil {
	public static boolean isGate(PrinceMap gameMap){
		List<SingleMapField> mapList = gameMap.getMap();
		for(SingleMapField smf: mapList){
			if(smf.isGate()){
				return true;
			}
		}
		return false;
	}
	
	public static int getGate(PrinceMap gameMap){
		List<SingleMapField> mapList = gameMap.getMap();
		for(int i = 0;i < mapList.size();i++){
			if(mapList.get(i).isGate())
				return i;
		}
		throw new IllegalArgumentException("getGate coudnt find gate");
		
	}
}
