package com.yzfar.www.base.model.judge;

import java.io.Serializable;

public class Rule implements Serializable {

	private static final long serialVersionUID = 1L;

	private LogicEnum logic; // 判断逻辑

	private RuleEnum rule;// 规则

	private String ruleValue;// 取值

	private String measurement = "";// 单位

	/**
	 * @param rule
	 * @param ruleValue
	 */
	public Rule(LogicEnum logic, RuleEnum rule, String ruleValue, String measurement) {
		this.logic = logic;
		this.rule = rule;
		this.ruleValue = ruleValue;
		this.measurement = measurement;
	}

	/**
	 * logic
	 *
	 * @return the logic
	 * @since 1.0.0
	 */
	public LogicEnum getLogic() {
		return logic;
	}

	/**
	 * @param logic
	 *            the logic to set
	 */
	public void setLogic(LogicEnum logic) {
		this.logic = logic;
	}

	/**
	 * @return the rule
	 */
	public RuleEnum getRule() {
		return rule;
	}

	/**
	 * @param rule
	 *            the rule to set
	 */
	public void setRule(RuleEnum rule) {
		this.rule = rule;
	}


	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	/**
	 * measurement
	 *
	 * @return the measurement
	 */
	public String getMeasurement() {
		return measurement;
	}

	/**
	 * @param measurement
	 *            the measurement to set
	 */
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	/**
	 * 
	 * @return Object
	 * @exception @since
	 *                1.0.0
	 */
	public Object convertToBaseValue() {
		return "";
	}

	public String getFunction(Object value) {
		StringBuffer sb = new StringBuffer();
		if (rule.equals(RuleEnum.CONTAIN)) {
			sb.append(this.logic.getExp()).append(" (");
			sb.append("0 <= (").append("indexOf('").append(value).append("', '").append(this.getRuleValue())
					.append("', -1)").append(")").append(") ");
		} else if (rule.equals(RuleEnum.NO_CONTAIN)) {
			sb.append(this.logic.getExp()).append(" (");
			sb.append("0 > (").append("indexOf('").append(value).append("', '").append(this.getRuleValue())
					.append("', -1)").append(")").append(") ");
		} else {
			sb.append(this.logic.getExp()).append(" (").append(value).append(" ").append(this.getRule().getExp())
					.append(" ");
			sb.append(String.valueOf(this.getRuleValue())).append(") ");
		}
		return sb.toString();
	}

	/**
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(logic.getName()).append(rule.getName()).append(ruleValue.toString())
				.append(measurement != null ? measurement : "");
		return sb.toString();
	}

}
