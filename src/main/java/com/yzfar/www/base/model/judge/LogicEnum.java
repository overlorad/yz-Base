package com.yzfar.www.base.model.judge;

import java.io.Serializable;

public enum LogicEnum implements Serializable {

	HEAD("", ""), AND("and", "&&"), OR("or", "||");

	private String name;

	private String exp;

	private LogicEnum(String name, String exp) {
		this.name = name;
		this.exp = exp;
	}

	public String getExp() {
		return exp;
	}

	public String getName() {
		return name;
	}

	public static LogicEnum getLogicEnum(String name) {
		if (name == null) {
			return LogicEnum.HEAD;
		}
		if (name.equals("")) {
			return LogicEnum.HEAD;
		} else if (name.equals("and")) {
			return LogicEnum.AND;
		} else if (name.equals("or")) {
			return LogicEnum.OR;
		}
		return LogicEnum.HEAD;
	}

	public static LogicEnum getLogicEnumByExp(String exp) {
		if (exp.equals("")) {
			return LogicEnum.HEAD;
		} else if (exp.equals("&&")) {
			return LogicEnum.AND;
		} else if (exp.equals("||")) {
			return LogicEnum.OR;
		}
		return LogicEnum.HEAD;
	}

}