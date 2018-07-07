package com.yzfar.www.base.model;

/**
 * 拟合函数
 */
public class FitterFun {

	public FitterFun(double[] fit) {
		this.fit = fit;
	}

	private double[] fit;

	public double[] getFit() {
		return fit;
	}

	public double getValue(double x) {
		double resule = 0;
		for (int i = 0; i < fit.length; i++) {
			resule += Math.pow(x, i) * fit[i];
		}
		return resule;
	}

	/**
	 * 获取最小平方差
	 */
	public Double getSqrtLMS(double[] xs, double[] ys) {
		if (xs.length == 0 || xs.length != ys.length) {
			return null;
		}
		double result = 0;
		for (int i = 0; i < ys.length; i++) {
			Double yv = getValue(xs[i]);
			result += Math.pow(ys[i] - yv, 2);
		}
		return Math.sqrt(result / ys.length);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = fit.length - 1; i > -1; i--) {
			if (fit[i] > 0 && i != fit.length - 1) {
				sb.append("+");
			}
			sb.append(fit[i]).append(" * X").append("(" + i + ") ");
		}
		return sb.toString();
	}

}
