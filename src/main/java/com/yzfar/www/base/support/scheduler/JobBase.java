package com.yzfar.www.base.support.scheduler;

import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yzfar.www.base.model.JobCallBack;
import com.yzfar.www.base.model.JobModel;
import com.yzfar.www.base.util.SpringUtil;

/**
 * @author: cp
 * @date: 2018年5月28日 下午1:36:52
 */
public class JobBase implements Job {
	protected static Logger logger = LogManager.getLogger();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		JobModel jobModel = (JobModel) jobDataMap.get(SchedulerUtilImpl.FLAG_JOBMODELS);
		Class<?> executeClazz = jobModel.getExecuteClazz();
		Object executeBean = SpringUtil.getBean(executeClazz);
		String methodName = jobModel.getExecuteMethod();
		Class<?>[] methodParam = jobModel.getMethodParam();
		try {
			Method method = executeClazz.getMethod(methodName, methodParam);
			Object result = method.invoke(executeBean, jobModel.getExecuteArgs());
			Class<? extends JobCallBack> jobCallBack = jobModel.getCallBackClazz();
			if (jobCallBack != null) {
				JobCallBack jobCallBackImpl = SpringUtil.getBean(jobCallBack);
				if (jobCallBackImpl == null) {
					return;
				}
				jobCallBackImpl.doCallBack(result);
			}
		} catch (Exception e) {
			logger.error("轮询任务执行失败[{}]", e.getMessage());
		}
	}

}
