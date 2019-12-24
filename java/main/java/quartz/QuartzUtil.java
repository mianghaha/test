package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 任务管理类: 对任务的管理;
 */
public class QuartzUtil {

    private static Scheduler scheduler; // 任务调度器;
    private static Logger logger = LoggerFactory.getLogger(QuartzUtil.class);
    private final static String JOB_GROUP_DEFAULT = "jobGroup";
    private final static String TRIGGER_GROUP_DEFAULT = "triggerGroup";
    
    public static void init(Scheduler scheduler) {
    	QuartzUtil.scheduler = scheduler;
    }
    
    /**
     * 根据文件初始化调度器
     * @param file
     */
    public static void init(String file) {
    	try {
        	SchedulerFactory sf = new StdSchedulerFactory(file);
        	scheduler = sf.getScheduler();
        	scheduler.start();
    	}catch(Exception e) {
            logger.error("init:exception.", e);
    	}
    }
    
    /**
     * 查询外部调度器任务是否存在
     * @param scheduler
     * @param jobId
     * @param group
     * @return
     */
    public static boolean isExist(Scheduler scheduler, String jobId, String jobGroup) {
        try {
        	if(jobGroup == null) {
        		jobGroup = JOB_GROUP_DEFAULT;
        	}
            JobKey key = new JobKey(jobId, jobGroup);
            return scheduler.checkExists(key);
        } catch (SchedulerException e) {
            logger.error("isExist:exception,jobId=" + jobId + ",jobGroup=" + jobGroup, e);
        }
        return false;
    }
    
    /**
     * 查询默认调度器任务是否存在
     * @param jobId
     * @param group
     * @return
     */
    public static boolean isExist(String jobId, String group) {
    	return isExist(scheduler, jobId, group);
    }

    /**
     * 根据参数创建job
     * @param scheduler
     * @param jobId
     * @param jobGroup
     * @param jobClass
     * @param triggerName
     * @param triggerGroup
     * @param startTime
     * @param endTime
     * @param repeatInterval
     * @param repeatCount
     * @param data
     * @return
     */
    public static boolean addSimpleTask(Scheduler scheduler, String jobId, String jobGroup, Class<? extends Job> jobClass, String triggerName, String triggerGroup, Date startTime, Date endTime, Long repeatInterval, Integer repeatCount, Map<String, Object> data) {
    	StringBuilder param = new StringBuilder();
    	param.append("jobId=").append(jobId).append(",")
    	.append("jobGroup=").append(jobGroup).append(",")
    	.append("triggerName=").append(triggerName).append(",")
    	.append("triggerGroup=").append(triggerGroup).append(",")
    	.append("startTime=").append(startTime).append(",")
    	.append("endTime=").append(endTime).append(",")
    	.append("repeatInterval=").append(repeatInterval).append(",")
    	.append("repeatCount=").append(repeatCount).append(",")
    	.append("jobClass=").append(jobClass.getName()).append(",")
    	.append("data.size=").append(data == null ? "null" : data.size()).append(",");
    	
        try {
        	//获取执行任务实例
        	JobDetail job = getJobDetail(jobId, jobGroup, jobClass, data);
            //设置具体执行规则
            SimpleTrigger trigger = getSimpleTrigger(triggerName, triggerGroup, repeatCount, repeatInterval, startTime, endTime);
            //加入至队列
            scheduler.scheduleJob(job, trigger);
            logger.info("addTask:success.param=" + param.toString());
        } catch (SchedulerException e) {
            logger.error("addTask:exception.param=" + param.toString(), e);
            return false;
        }
        return true;
    }
    
    public static boolean addSimpleTask(String jobId, String jobGroup, Class<? extends Job> jobClass, String triggerName, String triggerGroup, Date startTime, Date endTime, Long repeatInterval, Integer repeatCount, Map<String, Object> data) {
    	return addSimpleTask(scheduler, jobId, jobGroup, jobClass, triggerName, triggerGroup, startTime, endTime, repeatInterval, repeatCount, data);
    }
    
    public static boolean addSimpleTask(Scheduler scheduler, String jobId, String jobGroup, Class<? extends Job> jobClass, Date startTime, Date endTime, Long repeatInterval, Integer repeatCount, Map<String, Object> data) {
    	return addSimpleTask(scheduler, jobId, jobGroup, jobClass, jobId, jobGroup, startTime, endTime, repeatInterval, repeatCount, data);
    }
    
    public static boolean addSimpleTask(String jobId, String jobGroup, Class<? extends Job> jobClass, Date startTime, Date endTime, Long repeatInterval, Integer repeatCount, Map<String, Object> data) {
    	return addSimpleTask(scheduler, jobId, jobGroup, jobClass, jobId, jobGroup, startTime, endTime, repeatInterval, repeatCount, data);
    }
    
    public static boolean updateSimpleTask(Scheduler scheduler, String jobId, String jobGroup, Class<? extends Job> jobClass, String triggerName, String triggerGroup, Date startTime, Date endTime, Long repeatInterval, Integer repeatCount, Map<String, Object> data) {
    	StringBuilder param = new StringBuilder();
    	param.append("jobId=").append(jobId).append(",")
    	.append("jobGroup=").append(jobGroup).append(",")
    	.append("triggerName=").append(triggerName).append(",")
    	.append("triggerGroup=").append(triggerGroup).append(",")
    	.append("startTime=").append(startTime).append(",")
    	.append("endTime=").append(endTime).append(",")
    	.append("repeatInterval=").append(repeatInterval).append(",")
    	.append("repeatCount=").append(repeatCount).append(",")
    	.append("jobClass=").append(jobClass.getName()).append(",")
    	.append("data.size=").append(data == null ? "null" : data.size()).append(",");
    	
        try {
        	JobKey key = new JobKey(jobId, jobGroup);
        	JobDetail job = scheduler.getJobDetail(key);
        	//任务不存在，重新添加任务
        	if(job == null) {
        		addSimpleTask(scheduler, jobId, jobGroup, jobClass, triggerName, triggerGroup, startTime, endTime, repeatInterval, repeatCount, data);
        	//任务存在，更新任务信息
        	}else {
        		job.getJobDataMap().putAll(data);
        		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
        		SimpleTrigger trigger = getSimpleTrigger(triggerName, triggerGroup, repeatCount, repeatInterval, startTime, endTime);
        		scheduler.rescheduleJob(triggerKey, trigger);
        	}
            logger.info("updateSimpleTask:success.param=" + param.toString());
        } catch (SchedulerException e) {
            logger.error("updateSimpleTask:exception.param=" + param.toString(), e);
            return false;
        }
        return true;
    }
    
    public static boolean updateSimpleTask(String jobId, String jobGroup, Class<? extends Job> jobClass, String triggerName, String triggerGroup, Date startTime, Date endTime, Long repeatInterval, Integer repeatCount, Map<String, Object> data) {
    	return updateSimpleTask(scheduler, jobId, jobGroup, jobClass, triggerName, triggerGroup, startTime, endTime, repeatInterval, repeatCount, data);
    }
    
    /**
     * 根据cron规则创建任务
     * @param scheduler
     * @param jobId
     * @param jobGroup
     * @param jobClass
     * @param triggerName
     * @param triggerGroup
     * @param cronSchedule
     * @param data
     * @return
     */
    public static boolean addCronTask(Scheduler scheduler, String jobId, String jobGroup, Class<? extends Job> jobClass, String triggerName, String triggerGroup, String cronSchedule, Map<String, Object> data) {
    	StringBuilder param = new StringBuilder();
    	param.append("jobId=").append(jobId).append(",")
    	.append("jobGroup=").append(jobGroup).append(",")
    	.append("triggerName=").append(triggerName).append(",")
    	.append("triggerGroup=").append(triggerGroup).append(",")
    	.append("cronSchedule=").append(cronSchedule).append(",")
    	.append("data.size=").append(data == null ? "null" : data.size()).append(",");
    	
        try {
        	//获取执行任务实例
        	JobDetail job = getJobDetail(jobId, jobGroup, jobClass, data);
            //设置具体执行规则
        	CronTrigger trigger = getCrontrigger(triggerName, triggerGroup, cronSchedule);
            //加入至队列
            scheduler.scheduleJob(job, trigger);
            logger.info("addTask:success.param=" + param.toString());
        } catch (SchedulerException e) {
            logger.error("addTask:exception.param=" + param.toString(), e);
            return false;
        }
        return true;
    }
    
    public static boolean addCronTask(String jobId, String jobGroup, Class<? extends Job> jobClass, String triggerName, String triggerGroup, String cronSchedule, Map<String, Object> data) {
    	return addCronTask(scheduler, jobId, jobGroup, jobClass, triggerName, triggerGroup, cronSchedule, data);
    }
    
    /**
     * 获取执行任务实例
     * @param jobName
     * @param jobGroup
     * @param jobClass
     * @param data
     * @return
     */
    private static JobDetail getJobDetail(String jobName, String jobGroup, Class<? extends Job> jobClass, Map<String, Object> data) {
    	if(jobGroup == null) {
    		jobGroup = JOB_GROUP_DEFAULT;
    	}
        //建立job
        JobDetail job = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();
        //传入参数
        job.getJobDataMap().putAll(data);
        return job;
    }

    /**
     * 获取简单触发器实例
     * @param repeatCount
     * @param repeatInterval
     * @param startTime
     * @param endTime
     * @param data
     * @return
     */
    private static SimpleTrigger getSimpleTrigger(String triggerName, String triggerGroup, Integer repeatCount, Long repeatInterval, Date startTime, Date endTime) {
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();
        trigger.setName(triggerName);
        if(triggerGroup == null) {
        	triggerGroup = TRIGGER_GROUP_DEFAULT;
        }
        trigger.setGroup(triggerGroup);
        trigger.setStartTime(startTime);
        //设置结束时间
        if(endTime != null) {
        	trigger.setEndTime(endTime);
        }   
        trigger.setMisfireInstruction(SimpleTriggerImpl.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT);
        //设置执行次数
        if(repeatCount != null && repeatCount >= 1) {
        	trigger.setRepeatCount(repeatCount - 1);
        }else {
        	trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        }
        //设置间隔时间,最短的间隔时间为100ms
        if (repeatInterval != null && repeatInterval > 0) {
        	trigger.setRepeatInterval(repeatInterval);
        }else {
        	trigger.setRepeatInterval(100L);
        }
        return trigger;
    }
    
    /**
     * 获取cron类触发器实例
     * @param triggerName
     * @param triggerGroup
     * @param cronSchedule
     * @return
     */
    private static CronTrigger getCrontrigger(String triggerName, String triggerGroup, String cronSchedule) {
        if(triggerGroup == null) {
        	triggerGroup = TRIGGER_GROUP_DEFAULT;
        }
    	return TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup)
    			.withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .build();
    }

    /**
     * 暂停外部调度器任务
     * @param scheduler
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static boolean pauseTask(Scheduler scheduler, String jobId, String jobGroup) {
    	StringBuilder param = new StringBuilder();
    	param.append("jobId=").append(jobId).append(",")
    	.append("jobGroup=").append(jobGroup).append(",");
        try {
        	if(jobGroup == null) {
        		jobGroup = JOB_GROUP_DEFAULT;
        	}
            JobKey key = new JobKey(jobId, jobGroup);
            scheduler.pauseJob(key);
            logger.info("pauseTask:success." + param.toString());
        } catch (SchedulerException e) {
            logger.error("pauseTask:exception." + param.toString(), e);
        }
        return true;
    }
    
    /**
     * 暂停默认调度器任务
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static boolean pauseTask(String jobId, String jobGroup) {
    	return pauseTask(scheduler, jobId, jobGroup);
    }

    /**
     * 恢复外部调度器任务
     * @param scheduler
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static boolean resumeTask(Scheduler scheduler, String jobId, String jobGroup) {
    	StringBuilder param = new StringBuilder();
    	param.append("jobId=").append(jobId).append(",")
    	.append("jobGroup=").append(jobGroup).append(",");
        try {
        	if(jobGroup == null) {
        		jobGroup = JOB_GROUP_DEFAULT;
        	}
        	JobKey key = new JobKey(jobId, jobGroup);
            scheduler.resumeJob(key);
            logger.info("resumeTask:success." + param.toString());
        } catch (SchedulerException e) {
            logger.error("resumeTask:exception." + param.toString(), e);
        }
        return true;
    }
    
    /**
     * 恢复默认调度器任务
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static boolean resumeTask(String jobId, String jobGroup) {
    	return resumeTask(scheduler, jobId, jobGroup);
    }

    /**
     * 删除外部调度器任务
     * @param scheduler
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static boolean removeTask(Scheduler scheduler, String jobId, String jobGroup) {
    	StringBuilder param = new StringBuilder();
    	param.append("jobId=").append(jobId).append(",")
    	.append("jobGroup=").append(jobGroup).append(",");
        try {
        	if(jobGroup == null) {
        		jobGroup = JOB_GROUP_DEFAULT;
        	}
        	JobKey key = new JobKey(jobId, jobGroup);
            scheduler.deleteJob(key);
            logger.info("removeTask:success." + param.toString());
        } catch (SchedulerException e) {
            logger.error("removeTask:exception."  + param.toString(), e);
        }

        return true;
    }
    
    /**
     * 删除默认调度器任务
     * @param jobId
     * @param jobGroup
     * @return
     */
    public static boolean removeTask(String jobId, String jobGroup) {
    	return removeTask(scheduler, jobId, jobGroup);
    }

    /**
     * 销毁定时任务调度器
     *
     * @throws SchedulerException
     */
    public static void destroy() throws SchedulerException {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}
