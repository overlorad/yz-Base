package com.yzfar.www.base.util;

import java.util.Map;
import java.util.Vector;

import com.yzfar.www.base.model.judge.Judge;
import com.yzfar.www.base.model.judge.Judge.JudgeContent;
import com.yzfar.www.base.model.judge.Rule;

/**
 * 规则计算
 */
public final class JudgeUtil {
	private JudgeUtil() {
	}

	/**
	 * 计算故障等级
	 */
	public static JudgeContent calculate(Object value, Map<Integer, Vector<Rule>> rules) {
		return Judge.judge(value, rules);
	}
}
