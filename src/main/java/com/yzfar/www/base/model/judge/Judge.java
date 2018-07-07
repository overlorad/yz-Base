package com.yzfar.www.base.model.judge;

import java.io.Serializable;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

public final class Judge implements Serializable {
	private static final long serialVersionUID = -5825736501799506845L;
	protected static Logger logger = LogManager.getLogger();

	private Judge() {
	}

	public static class JudgeContent implements Serializable {
		private static final long serialVersionUID = 1L;
		private byte level;// 等级
		private String desc;// 描述

		public JudgeContent(byte level, String desc) {
			this.level = level;
			this.desc = desc;
		}

		public byte getLevel() {
			return level;
		}

		public JudgeContent setLevel(byte level) {
			this.level = level;
			return this;
		}

		public String getDesc() {
			return desc;
		}

		public JudgeContent setDesc(String desc) {
			this.desc = desc;
			return this;
		}

	}

	public static JudgeContent judge(Object value, Map<Integer, Vector<Rule>> rules) {
		for (byte i = GradeEnum.RED.getLevel(); i > 0; i--) {
			Vector<Rule> r = rules.get(i);
			if (r != null) {
				StringBuffer ruleBasic = new StringBuffer();
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < r.size(); j++) {
					sb.append(r.get(j).getFunction(value));
					ruleBasic.append(r.get(j).toString());
				}
				try {
					Evaluator eva = new Evaluator();
					boolean bool = eva.getBooleanResult(sb.toString());
					if (bool) {
						return new JudgeContent(i, ruleBasic.toString());
					}
				} catch (EvaluationException e) {
					logger.info("表达式{}出错！", sb.toString());
					continue;
				}
			}
		}
		return null;
	}

}
