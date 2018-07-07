package com.yzfar.www.base.model.judge;

import java.io.Serializable;

public enum RuleEnum implements Serializable {

	DEFAULT("", ""),

	BIGGER("bigger", ">"),

	LESS("less", "<"),

	EQUAL("equal", "=="),

	NO_BIGGER("no_bigger", "<="),

	NO_LESS("no_less", ">="),

	NO_EQUAL("no_equal", "!="),

	CONTAIN("contain", "contains"),

	NO_CONTAIN("no_contain", "!contains"),

	ERROR_EQUAL("error_equal", "<>");
	private String name;

	private String exp;

	private RuleEnum(String name, String exp) {
		this.name = name;
		this.exp = exp;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("(").append(exp).append(")");
		return sb.toString();
	}

	/**
	 */
	public String getName() {
		return name;
	}

	public String getExp() {
		return exp;
	}

	public static RuleEnum getRuleEnum(String name) {
		if (name.equals(BIGGER.getName())) {
			return RuleEnum.BIGGER;
		} else if (name.equals(LESS.getName())) {
			return RuleEnum.LESS;
		} else if (name.equals(EQUAL.getName())) {
			return RuleEnum.EQUAL;
		} else if (name.equals(NO_BIGGER.getName())) {
			return RuleEnum.NO_BIGGER;
		} else if (name.equals(NO_LESS.getName())) {
			return RuleEnum.NO_LESS;
		} else if (name.equals(NO_EQUAL.getName())) {
			return RuleEnum.NO_EQUAL;
		} else if (name.equals(CONTAIN.getName())) {
			return RuleEnum.CONTAIN;
		} else if (name.equals(NO_CONTAIN.getName())) {
			return RuleEnum.NO_CONTAIN;
		} else if (name.equals(ERROR_EQUAL.getName())) {
			return RuleEnum.ERROR_EQUAL;
		}
		return RuleEnum.DEFAULT;
	}

	public static RuleEnum getRuleEnumByExp(String exp) {
		if (exp.equals(BIGGER.getExp())) {
			return RuleEnum.BIGGER;
		} else if (exp.equals(LESS.getExp())) {
			return RuleEnum.LESS;
		} else if (exp.equals(EQUAL.getExp())) {
			return RuleEnum.EQUAL;
		} else if (exp.equals(NO_BIGGER.getExp())) {
			return RuleEnum.NO_BIGGER;
		} else if (exp.equals(NO_LESS.getExp())) {
			return RuleEnum.NO_LESS;
		} else if (exp.equals(NO_EQUAL.getExp())) {
			return RuleEnum.NO_EQUAL;
		} else if (exp.equals(CONTAIN.getExp())) {
			return RuleEnum.CONTAIN;
		} else if (exp.equals(NO_CONTAIN.getExp())) {
			return RuleEnum.NO_CONTAIN;
		} else if (exp.equals(ERROR_EQUAL.getExp())) {
			return RuleEnum.ERROR_EQUAL;
		}
		return RuleEnum.DEFAULT;
	}
}
