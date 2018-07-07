package com.yzfar.www.base.util;

import java.util.Collection;

import org.quartz.DateBuilder.IntervalUnit;

import com.yzfar.www.base.model.JobModel;

/**
 * @author: cp
 * @date: 2018年5月28日 下午1:07:28
 */
public interface SchedulerUtil {
	/**
	 * 添加任务
	 */
	boolean addJob(JobModel jobModel);

	/**
	 * 添加任务
	 */
	boolean addJobs(Collection<JobModel> jobModels);

	/**
	 * 删除任务
	 */
	void deleteJob(String id);

	/**
	 * 获取任务
	 */
	JobModel getJob(String id);

	/**
	 * 修改任务时间
	 */
	boolean upData(String id, IntervalUnit tntervalType, int period);

	/**
	 * 暂停任务
	 */
	boolean pauseJob(String id);

	/**
	 * 开始某个暂停的任务
	 */
	boolean reStartJob(String id);

	/**
	 * 清除所有轮询任务
	 */
	void clear();
}
