package cz.tieto.academy.prince.persianoffensive;

import java.util.List;

import cz.tieto.princegame.common.gameobject.Field;

/*
 * Class representing prince field of sight
 */
public class FieldOfSight {
	
	/*
	 * List of fields on the left side of prince
	 */
	private List<Field> leftSide;
	
	/*
	 * List of fields on the right side of prince
	 */
	private List<Field> rightSide;
	
	/*
	 * Actual field prince is standing on
	 */
	private Field actualField;

	
	/*
	 * Getters and setters
	 */
	public List<Field> getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(List<Field> leftSide) {
		this.leftSide = leftSide;
	}

	public List<Field> getRightSide() {
		return rightSide;
	}

	public void setRightSide(List<Field> rightSide) {
		this.rightSide = rightSide;
	}

	public Field getActualField() {
		return actualField;
	}

	public void setActualField(Field actualField) {
		this.actualField = actualField;
	}
	
	
	
}
