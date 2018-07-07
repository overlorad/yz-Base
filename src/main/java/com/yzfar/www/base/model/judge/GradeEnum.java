package com.yzfar.www.base.model.judge;

import java.io.Serializable;

public enum GradeEnum implements Serializable {
	RED("serious", (byte) 4), ORANGE("warn", (byte) 3), YELLOW("general", (byte) 2), BLUE("prompt",
			(byte) 1), NORMAL("normal", (byte) 0);
	private String name;

	private byte level;

	private GradeEnum(String name, byte level) {
		this.name = name;
		this.level = level;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("grade��").append(level).append(", desc��").append(name);
		return sb.toString();
	}

	/**
	 * name
	 * 
	 * @return the name
	 * @since 1.0.0
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * level
	 * 
	 * @return the level
	 * @since 1.0.0
	 */

	public byte getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(byte level) {
		this.level = level;
	}

	public static GradeEnum valueOf(int level) {
		switch (level) {
		case 1:
			return GradeEnum.BLUE;
		case 2:
			return GradeEnum.YELLOW;
		case 3:
			return GradeEnum.ORANGE;
		case 4:
			return GradeEnum.RED;
		default:
			return GradeEnum.NORMAL;
		}
	}

}
