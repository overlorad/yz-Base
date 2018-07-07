package com.yzfar.www.base.model;

import java.util.Date;

import org.quartz.DateBuilder.IntervalUnit;

/**
 * 类说明:任务调度模型
 * 
 * @author 作者cp E-mail: 2055853135@qq.com
 * @version 创建时间：2017年12月19日 下午1:37:27
 */
public class JobModel {

	/**
	 * 调度类型
	 */
	public enum PollingType {
		PERIOD("周期"), TIMING("定时");
		private PollingType(String name) {
			this.name = name;
		}

		private String name;

		public String toString() {
			return name;
		}
	}

	/**
	 * 标识
	 */
	private String id;
	/**
	 * 开始时间
	 */
	private Date startTime = new Date();
	/**
	 * 调度类型
	 */
	private PollingType pollingType = PollingType.PERIOD;

	/**
	 * 调度时间单位类型
	 */
	private IntervalUnit tntervalType = IntervalUnit.MINUTE;
	/**
	 * 调度周期pollingType==PollingType.PERIOD可用
	 */
	private int period;

	/**
	 * 调度表达式pollingType==PollingType.TIMING可用
	 */
	private String expression;

	/**
	 * 方法调用的类
	 */
	private Class<?> executeClazz;

	/**
	 * 方法
	 */
	private String executeMethod;

	/**
	 * 形参
	 */
	private Class<?>[] methodParam;

	/**
	 * 执行实参
	 */
	private Object[] executeArgs;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 回调类
	 */
	private Class<? extends JobCallBack> callBackClazz;

	public String getId() {
		return id;
	}

	public JobModel setId(String id) {
		this.id = id;
		return this;
	}

	public Date getStartTime() {
		return startTime;
	}

	public JobModel setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public PollingType getPollingType() {
		return pollingType;
	}

	public JobModel setPollingType(PollingType pollingType) {
		this.pollingType = pollingType;
		return this;
	}

	public int getPeriod() {
		if (period < 1) {
			period = 1;
		}
		return period;
	}

	public JobModel setPeriod(int period) {
		this.period = period;
		return this;
	}

	public String getExpression() {
		return expression;
	}

	public JobModel setExpression(String expression) {
		this.expression = expression;
		return this;
	}

	public String getExecuteMethod() {
		return executeMethod;
	}

	public JobModel setExecuteMethod(String executeMethod) {
		this.executeMethod = executeMethod;
		return this;
	}

	public IntervalUnit getTntervalType() {
		return tntervalType;
	}

	public JobModel setTntervalType(IntervalUnit tntervalType) {
		this.tntervalType = tntervalType;
		return this;
	}

	public Object[] getExecuteArgs() {
		return executeArgs;
	}

	public JobModel setExecuteArgs(Object[] executeArgs) {
		this.executeArgs = executeArgs;
		return this;
	}

	public Class<? extends JobCallBack> getCallBackClazz() {
		return callBackClazz;
	}

	public JobModel setCallBackClazz(Class<? extends JobCallBack> callBackClazz) {
		this.callBackClazz = callBackClazz;
		return this;
	}

	public Class<?>[] getMethodParam() {
		return methodParam;
	}

	public JobModel setMethodParam(Class<?>[] methodParam) {
		this.methodParam = methodParam;
		return this;
	}

	public Class<?> getExecuteClazz() {
		return executeClazz;
	}

	public JobModel setExecuteClazz(Class<?> executeClazz) {
		this.executeClazz = executeClazz;
		return this;
	}

	public Date getEndTime() {
		return endTime;
	}

	public JobModel setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobModel other = (JobModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getPollingType()).append("任务[").append(this.getId()).append("]");
		return sb.toString();
	}
}
