package com.yzfar.www.base.util;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yzfar.www.base.model.FitterFun;

/**
 * 最小二乘法曲线拟合
 */
public final class LeastSquares {
	private LeastSquares() {
	}

	protected static Logger logger = LogManager.getLogger();

	/**
	 * @param xs
	 *            x坐标数据
	 * @param ys
	 *            y坐标数据
	 * @param degree
	 *            拟合多项式次数
	 */
	public static FitterFun fitter(double[] xs, double[] ys, int degree) {
		if (xs.length == 0 || xs.length != ys.length) {
			return null;
		}
		WeightedObservedPoints wop = new WeightedObservedPoints();
		for (int i = 0; i < xs.length; i++) {
			wop.add(xs[i], ys[i]);
		}
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
		try {
			double[] fit = fitter.fit(wop.toList());
			return new FitterFun(fit);
		} catch (Exception e) {
			logger.error("多项式[{}]拟合失败:{}", degree, e.getMessage());
			return null;
		}
	}

	/**
	 * 获取最佳次数冥
	 * 
	 * @param xs
	 *            x坐标数据
	 * @param ys
	 *            y坐标数据
	 * 
	 */
	public static int fitterDegree(double[] xs, double[] ys) {
		double currentLMS = 0;
		int degree = 0, sun = 2, currentSun = 0;
		while (true) {
			degree++;
			FitterFun fitter = LeastSquares.fitter(xs, ys, degree);
			if (fitter == null) {
				degree--;
				break;
			}
			Double lms = fitter.getSqrtLMS(xs, ys);
			if (degree == 1) {
				currentLMS = lms;
				continue;
			}
			if (lms >= currentLMS) {
				currentSun++;
			}
			if (currentSun == sun) {
				break;
			}
			currentLMS = lms;
		}
		return degree;
	}

}
