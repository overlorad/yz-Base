package com.yzfar.www.base.support.scheduler;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.DailyTimeIntervalTriggerImpl;

import com.yzfar.www.base.model.JobModel;
import com.yzfar.www.base.model.JobModel.PollingType;
import com.yzfar.www.base.util.ListUtil;
import com.yzfar.www.base.util.SchedulerUtil;

/**
 * @author: cp
 * @date: 2018年5月28日 下午1:17:28
 */
public class SchedulerUtilImpl implements SchedulerUtil {
	public static final String FLAG_JOBMODELS = "FLAG_JOBMODELS";
	protected Logger logger = LogManager.getLogger();
	private SchedulerFactory schedFact = null;
	private Map<String, ModelToJobDetail> jobMap = null;

	public SchedulerUtilImpl() {
		schedFact = new StdSchedulerFactory();
		jobMap = ListUtil.newConcurrentHashMap(2 << 5, 2);
	}

	public SchedulerUtilImpl(String quartzPath) {
		try {
			schedFact = new StdSchedulerFactory(quartzPath);
			jobMap = ListUtil.newConcurrentHashMap(2 << 5, 2);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	class ModelToJobDetail {

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((jobModel == null) ? 0 : jobModel.hashCode());
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
			ModelToJobDetail other = (ModelToJobDetail) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (jobModel == null) {
				if (other.jobModel != null)
					return false;
			} else if (!jobModel.equals(other.jobModel))
				return false;
			return true;
		}

		JobModel jobModel;
		JobDetailImpl jobDetail;

		ModelToJobDetail(JobModel jobModel, JobDetailImpl jobDetail) {
			this.jobModel = jobModel;
			this.jobDetail = jobDetail;
		}

		private SchedulerUtilImpl getOuterType() {
			return SchedulerUtilImpl.this;
		}
	}

	@Override
	public boolean addJob(JobModel jobModel) {
		if (jobModel == null) {
			logger.error("轮询任务添加失败:空对象");
			return false;
		}
		PollingType pollingType = jobModel.getPollingType();
		switch (pollingType) {
		case PERIOD:
			Scheduler sched;
			try {
				sched = schedFact.getScheduler();
				JobDetailImpl jobDetail = new JobDetailImpl();
				String jobKey = jobModel.getId();
				JobKey jk_day = new JobKey(jobKey);
				jobDetail.setKey(jk_day);
				jobDetail.setGroup(jobKey + "-GROUP");
				jobDetail.setJobClass(JobBase.class);
				JobDataMap jobDataMap = jobDetail.getJobDataMap();
				jobDataMap.put(FLAG_JOBMODELS, jobModel);
				DailyTimeIntervalTriggerImpl trigger = new DailyTimeIntervalTriggerImpl();
				trigger.setStartTime(jobModel.getStartTime());
				trigger.setRepeatIntervalUnit(jobModel.getTntervalType());
				trigger.setRepeatInterval(jobModel.getPeriod());
				trigger.setName(jobKey);
				Date endTime = jobModel.getEndTime();
				if (endTime != null) {
					trigger.setEndTime(endTime);
				}
				sched.scheduleJob(jobDetail, trigger);
				sched.start();
				jobMap.put(jobKey, new ModelToJobDetail(jobModel, jobDetail));
				logger.info("添加轮询任务【{}】", jobModel);
				return true;
			} catch (Exception e) {
				logger.error("轮询任务添加失败---{}", e);
				return false;
			}
		case TIMING:
			try {
				Scheduler scheduler = schedFact.getScheduler();
				JobDetailImpl jobDetail = new JobDetailImpl();
				JobDataMap jobDataMap = jobDetail.getJobDataMap();
				jobDataMap.put(FLAG_JOBMODELS, jobModel);
				String jobKey = jobModel.getId();
				JobKey job_key = new JobKey(jobKey);
				jobDetail.setKey(job_key);
				jobDetail.setJobClass(JobBase.class);
				CronTrigger trigger = null;
				trigger = TriggerBuilder.newTrigger().withIdentity(jobKey, jobKey + "-GROUP")
						.withSchedule(CronScheduleBuilder.cronSchedule(jobModel.getExpression())).build();
				scheduler.scheduleJob(jobDetail, trigger);
				scheduler.start();
				jobMap.put(jobKey, new ModelToJobDetail(jobModel, jobDetail));
				logger.info("添加定时任务【{}】", jobModel);
				return true;
			} catch (SchedulerException e) {
				logger.error("初始化[{}]定时任务[{}]失败", jobModel, jobModel.getExpression());
				return false;
			}
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean addJobs(Collection<JobModel> jobModels) {
		if (jobModels == null) {
			logger.error("任务集合为空");
			return false;
		}
		for (JobModel jobModel : jobModels) {
			addJob(jobModel);
		}
		return true;
	}

	@Override
	public void deleteJob(String id) {
		ModelToJobDetail modelToJobDetail = jobMap.get(id);
		if (modelToJobDetail == null) {
			return;
		}
		try {
			schedFact.getScheduler().deleteJob(modelToJobDetail.jobDetail.getKey());
			jobMap.remove(id);
			logger.info("轮询任务【{}】删除---", modelToJobDetail.jobModel);
		} catch (SchedulerException e) {
			logger.error("轮询任务【{}】删除失败---{}", modelToJobDetail.jobModel, e);
		}
	}

	@Override
	public JobModel getJob(String id) {
		ModelToJobDetail modelToJobDetail = jobMap.get(id);
		if (modelToJobDetail == null) {
			return null;
		}
		return modelToJobDetail.jobModel;
	}

	@Override
	public boolean upData(String id, IntervalUnit tntervalType, int period) {
		ModelToJobDetail modelToJobDetail = jobMap.get(id);
		if (modelToJobDetail == null) {
			return false;
		}
		deleteJob(id);
		JobModel jobModel = modelToJobDetail.jobModel;
		jobModel.setTntervalType(tntervalType);
		jobModel.setPeriod(period);
		boolean addJob = addJob(jobModel);
		logger.info("更新的轮询任务为【{}】", jobModel);
		return addJob;
	}

	@Override
	public boolean pauseJob(String id) {
		ModelToJobDetail modelToJobDetail = jobMap.get(id);
		if (modelToJobDetail == null) {
			logger.error("暂停({})的轮询任务失败:空对象", id);
			return false;
		}
		JobModel jobModel = modelToJobDetail.jobModel;
		Scheduler sched;
		try {
			sched = schedFact.getScheduler();
			sched.pauseJob(modelToJobDetail.jobDetail.getKey());
			logger.info("暂停【{}】的轮询任务成功", jobModel);
			return true;
		} catch (SchedulerException e) {
			logger.error("暂停【{}】的轮询任务失败:{}", jobModel, e);
			return false;
		}
	}

	@Override
	public boolean reStartJob(String id) {
		ModelToJobDetail modelToJobDetail = jobMap.get(id);
		if (modelToJobDetail == null) {
			logger.error("重新启动({})的轮询任务失败:空对象", id);
			return false;
		}
		Scheduler sched;
		JobModel jobModel = modelToJobDetail.jobModel;
		try {
			sched = schedFact.getScheduler();
			sched.resumeJob(modelToJobDetail.jobDetail.getKey());
			logger.info("重新启动({})的轮询任务成功:{}", jobModel);
			return false;
		} catch (SchedulerException e) {
			logger.error("重新启动({})的轮询任务失败:{}", jobModel, e);
			return false;
		}
	}

	@Override
	public void clear() {
		try {
			Scheduler sched = schedFact.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
				logger.info("清空所有轮询任务");
				jobMap.clear();
			}
		} catch (SchedulerException e) {
			logger.error("清空所有轮询任务错误:{}", e);
		}
	}

}
