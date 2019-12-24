package quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 任务管理类;对任务的管理;
 *
 */
public class TaskManager
{

	public static Scheduler scheduler; // 任务调度器;
	private static Logger logger = LoggerFactory.getLogger(TaskManager.class);

	
	/**
	 * 
	 * @throws SchedulerException
	 */
	public static void init() throws SchedulerException
	{
		String fileName = "./quartz.properties";
		init(fileName, null);
	}
	
	public static void reload(){
		try {
			logger.info("taskmanager reload start");
			destroy();
			init();			
		} catch (SchedulerException e1) {
			logger.warn("taskmanager reload thrown exception");
		}
	}

	/**
	 * 初始化
	 * 
	 * @throws SchedulerException
	 */
	public static void init(String fileName, List<TaskBean> list) throws SchedulerException
	{
		logger.info("------- Initializing scheduler-----------------");
		SchedulerFactory sf = new StdSchedulerFactory(fileName);
		scheduler = sf.getScheduler();
		logger.info("------- Initialization Complete -----------");
		logger.info("------- Scheduling Jobs -------------------");
		
		if (list != null && list.size() > 0)
		{
			for (TaskBean bean : list)
			{
				// 定义job和trigger
				JobDetail job;
				try
				{
					job = JobBuilder.newJob((Class<? extends Job>) Class.forName(bean.getClassName())).withIdentity(bean.getTaskName(), "group").build();
					job.getJobDataMap().putAll(bean.getParam());
				} catch (ClassNotFoundException e)
				{

					logger.warn("Can't find " + bean + " class name.");
					continue;
				}

				 CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(bean.getTaskName() + "_trigger", "group").withSchedule(CronScheduleBuilder.cronSchedule(bean.getTrigger())).build();
				// Tell quartz to schedule the job using our trigger
				try
				{
					scheduler.scheduleJob(job, trigger);
					logger.info(bean + " start success");

				} catch (SchedulerException e)
				{
					logger.warn(bean + " can't be scheduled because ", e);
				}
			}
		}
		// 启动scheduler
		scheduler.start();
	}
	
	/**
	 * 查询任务是否存在
	 * @param jobname
	 * @param group
	 * @return
	 * @throws SchedulerException 
	 */
	public static boolean isExist(String taskName, String group){
		JobKey key = new JobKey(taskName, group);
		try {
			return scheduler.checkExists(key);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isExist(JobKey key){
		try {
			return scheduler.checkExists(key);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 添加固定次数任务
	 * @param taskName
	 * @param group
	 * @param triggername
	 * @param triggergroup
	 * @param send_date
	 * @param repeat_interval
	 * @param repeatCount
	 * @param className
	 * @param data
	 * @return
	 */
	public static boolean addTask(String taskName, String group, String triggername, String triggergroup, Date send_date,
			Long repeat_interval, int repeat_count, String className, Map<String, Object> data){
		
		try{
			Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(className);
			addTask(taskName, group, triggername, triggergroup, send_date, repeat_interval, repeat_count, jobClass, data);
		} catch (ClassNotFoundException e) {
			logger.warn("Can't find \"" + className + "\" class name.");
			return false;
		} 
		return true;
	}
	
	/**
	 * 添加固定次数任务
	 * @param taskName
	 * @param group
	 * @param triggername
	 * @param triggergroup
	 * @param send_date
	 * @param repeat_interval
	 * @param repeatCount
	 * @param className
	 * @param data
	 * @return
	 */
	public static boolean addTask(String taskName, String group, String triggername, String triggergroup, Date send_date,
			Long repeat_interval, int repeat_count, Class<? extends Job> jobClass, Map<String, Object> data){
		
		try{
			//建立job
			JobDetail job = JobBuilder.newJob(jobClass).withIdentity(taskName, group).build();
			//传入参数
			job.getJobDataMap().putAll(data);
		
			SimpleTriggerImpl trigger = new SimpleTriggerImpl();
			trigger.setName(triggername);
			trigger.setGroup(triggergroup);
			trigger.setStartTime(send_date);
			trigger.setMisfireInstruction(SimpleTriggerImpl.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT);
			trigger.setRepeatCount(repeat_count - 1);
			if(repeat_interval <= 0) {
				trigger.setRepeatInterval(1000L);
			}else {
				trigger.setRepeatInterval(repeat_interval);
			}

			//加入至队列
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			logger.error(jobClass.getName() + " can't be scheduled because ", e);
			return false;
		}
		return true;
	}
	
	/**
	 * 添加循环任务
	 * @param taskName
	 * @param group
	 * @param triggername
	 * @param triggergroup
	 * @param send_date
	 * @param repeat_interval
	 * @param repeatCount
	 * @param className
	 * @param data
	 * @return
	 */
	public static boolean addTask(String taskName, String group, String triggername, String triggergroup, Date send_date,
			Date end_date, Long repeat_interval, String className, Map<String, Object> data){
		
		try{
			//建立job
			JobDetail job = JobBuilder.newJob((Class<? extends Job>) Class.forName(className)).withIdentity(taskName, group).build();
			//传入参数
			job.getJobDataMap().putAll(data);
			
			SimpleTriggerImpl trigger = new SimpleTriggerImpl();
			trigger.setName(triggername);
			trigger.setGroup(triggergroup);
			trigger.setStartTime(send_date);
			trigger.setEndTime(end_date);
			if(repeat_interval <= 0) {
				trigger.setRepeatInterval(1000L);
			}else {
				trigger.setRepeatInterval(repeat_interval);
			}
			trigger.setMisfireInstruction(SimpleTriggerImpl.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
			trigger.setRepeatCount(SimpleTriggerImpl.REPEAT_INDEFINITELY);
			
			//加入至队列
			scheduler.scheduleJob(job, trigger);
			logger.info(className + " start success");
		} catch (ClassNotFoundException e) {
			logger.warn("Can't find \"" + className + "\" class name.");
			return false;
		} catch (SchedulerException e) {
			logger.error(className + " can't be scheduled because ", e);
			return false;
		}
		return true;
	}
	
	/**
	 * 更新任务（重建任务）
	 * @param taskName
	 * @param group
	 * @param triggername
	 * @param triggergroup
	 * @param className
	 * @param data
	 * @return
	 */
	public static boolean updateTrigger(String jobname, String oldtriggername, String newtriggername, String triggergroup, 
			int repeatCount, Long repeatInterval, Date starttime){
		TriggerKey oldkey = new TriggerKey(oldtriggername, triggergroup);
		TriggerKey newkey = new TriggerKey(newtriggername, triggergroup);
		SimpleTriggerImpl newtrigger = new SimpleTriggerImpl();
		try {
			newtrigger.setStartTime(starttime);
			newtrigger.setRepeatCount(repeatCount - 1);
			if(repeatInterval <= 0) {
				newtrigger.setRepeatInterval(1000L);
			}else {
				newtrigger.setRepeatInterval(repeatInterval);
			}
			if(scheduler.getTrigger(oldkey) == null){
				newtrigger.setKey(oldkey);
				scheduler.rescheduleJob(newkey, newtrigger);
			}else{
				newtrigger.setKey(newkey);
				scheduler.rescheduleJob(oldkey, newtrigger);
			}
			logger.info("updateTrigger:JOB updateing trigger success.Jobname=" + jobname + ",oldtriggername=" + oldtriggername
					+ ",newtriggername=" + newtriggername + ",triggergroup=" + triggergroup + ",repeatCount=" + repeatCount
					+ ",repeatInterval=" + repeatInterval + ",starttime=" + starttime);
		} catch (SchedulerException e) {
			logger.error("updateTrigger:JOB updateing trigger failed.Jobname" + jobname + jobname + ",oldtriggername=" + oldtriggername
					+ ",newtriggername=" + newtriggername + ",triggergroup=" + triggergroup + ",repeatCount=" + repeatCount
					+ ",repeatInterval=" + repeatInterval + ",starttime=" + starttime, e);
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 暂停任务
	 * 
	 * @param taskName    任务名称
	 */
	public static boolean pauseTask(String taskName, String group)
	{
		JobKey key = new JobKey(taskName, group);
		try {
			scheduler.pauseJob(key);
			logger.info("Task:" + taskName + " has been paused");
		} catch (SchedulerException e) {
			logger.error("pauseTask:exception,taskName=" + taskName + ",group=" + group, e);
		}
		return true;
	}
	
	/**
	 * 恢复任务
	 * 
	 * @param taskName    任务名称
	 */
	public static boolean resumeTask(String taskName, String group)
	{
		JobKey key = new JobKey(taskName, group);
		try {
			scheduler.resumeJob(key);
			logger.info("Task:" + taskName + " has been resumed");
		} catch (SchedulerException e) {
			logger.error("resumeTask:exception,taskName=" + taskName + ",group=" + group, e);
		}
		return true;
	}

	/**
	 * 删除任务
	 * 
	 * @param taskName    任务名称
	 */
	public static boolean removeTask(String taskName, String group)
	{
		JobKey key = new JobKey(taskName, group);
		try {
			scheduler.deleteJob(key);
			logger.info("Task:" + taskName + " has been deleted");
		} catch (SchedulerException e) {
			logger.error("removeTask:exception,taskName=" + taskName + ",group=" + group, e);
		}
		return true;
	}
	
	/**
	 * 将字符串转化为String数组
	 * @param date
	 * @return
	 */
	public static String[] transDateToArray(String date){
		String[] dates = new String[6];
		
		String[] splitdate = date.split(" ");
		String[] ymd = splitdate[0].split("-");
		String[] hms = splitdate[1].split(":");
		for(int i = 0; i < 3; i++){
			dates[i] = ymd[i];
		}
		for(int i = 3; i < 6; i++){
			dates[i] = hms[i - 3];
		}
		return dates;
	}	
	
	/**
	 * 日期转为Cron表达式 传入日期格式应为：YYYY-MM-DD HH:MM:SS
	 * @return
	 */
	public static String transtoCron(String date){
		
		if(!validateDate(date)){
			return null;
		}
		
		String[] dates = transDateToArray(date);
		
		int result = validateDate(dates);
		if(result < 0){
			return null;
		}else if(result == 1){
			return null;
		}

		StringBuffer sb = new StringBuffer();
		sb.append(dates[5]);
		sb.append(" ").append(dates[4]);
		sb.append(" ").append(dates[3]); 
		sb.append(" ").append(dates[2]); 
		sb.append(" ").append(dates[1]);
		sb.append(" ").append("?");		
		sb.append(" ").append(dates[0]);
		
		return sb.toString();
	}
	
	public static String transtoDate(String cron){
		if(cron.equals(""))return cron;
	
		String[] dates = cron.split(" ");
		StringBuffer sb = new StringBuffer();
		sb.append(dates[6]);
		sb.append("-").append(dates[4]);
		sb.append("-").append(dates[3]); 
		sb.append(" ").append(dates[2]); 
		sb.append(":").append(dates[1]);	
		sb.append(":").append(dates[0]);		
		return sb.toString();
	}
	
	/**
	 * 验证日期是否有非法字符
	 * @return
	 */
	public static boolean validateDate(String date){

		String reg = "[^0-9-\\*:\\s]";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(date);
		if(m.find()){
			return false;
		}
		
		String[] dates = transDateToArray(date);
		if(validateDate(dates) != 0){
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 验证日期是否合法 以数组的形式传入 
	 * 数组数据的排列顺序为：0：YYYY 1：MM 2:DD 3：HH 4：MM 5：SS
	 * 若某个字段不需要则用*代替，其他字符均为非法
	 * @return -1:不合法 0:合法且时间为将来时 1:合法且时间为过去时 2:间隔时间序列，即带有*
	 */
	public static int validateDate(String[] dates){
		
		if(dates.length != 6){
			return -1;
		}
		String reg = "[^0-9]";
		Pattern p = Pattern.compile(reg);
		Matcher m;
		
		//年
		int year = 0;
		if(!"*".equals(dates[0])){
			m = p.matcher(dates[0]);
			if(m.find()){
				return -1;				
			}
			
			year = Integer.valueOf(dates[0]);
			if(year < 0){
				return -1;
			}
		}
		
		//月
		int mon = 0;
		if(!"*".equals(dates[1])){
			m = p.matcher(dates[1]);
			if(m.find()){
				return -1;				
			}
			
			mon = Integer.valueOf(dates[1]);
			if(mon > 12 || mon < 0){
				return -1;
			}
		}
		
		//日
		int day = 0;
		if(!"*".equals(dates[2])){
			m = p.matcher(dates[2]);
			if(m.find()){
				return -1;				
			}
			
			day = Integer.valueOf(dates[2]);
			if(day > 31 || day < 0){
				return -1;
			}
			if(day > 28){
				if(mon == 0){
					return -1;
				}else if(mon == 2){
					//闰年
					if(year == 0 || year % 400 == 0 || (year % 4 ==0 && year % 100 != 0)){
						if(day > 29){
							return -1;
						}
					}
				}else if(mon == 4|| mon == 6|| mon == 9|| mon == 11){
					if(day > 30){
						return -1;
					}
				}
			}
		}
		
		//时
		int hour = 0;
		if(!"*".equals(dates[3])){
			m = p.matcher(dates[3]);
			if(m.find()){
				return -1;				
			}
			
			hour = Integer.valueOf(dates[3]);
			if(hour > 23 || hour < 0){
				return -1;
			}
		}
		
		//分
		int min = 0;
		if(!"*".equals(dates[4])){
			m = p.matcher(dates[4]);
			if(m.find()){
				return -1;				
			}
			
			min = Integer.valueOf(dates[4]);
			if(min > 60 || min < 0){
				return -1;
			}
		}
		
		//秒
		int sec = 0;
		if(!"*".equals(dates[5])){
			m = p.matcher(dates[5]);
			if(m.find()){
				return -1;				
			}
			
			sec = Integer.valueOf(dates[5]);
			if(sec > 60 || sec < 0){
				return -1;
			}
		}
		
		if(year != 0 && mon != 0 && day != 0){
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, mon - 1, day, hour, min, sec);
			
			if(calendar.compareTo(Calendar.getInstance()) < 0){
				return 1;
			}
		}else{
			return 2;
		}
		
		return 0;
	}
	
	
	public static String getNowTime(){
		Calendar calendar = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.SECOND)).append(" ");
		sb.append(calendar.get(Calendar.MINUTE)).append(" ");
		sb.append(calendar.get(Calendar.HOUR_OF_DAY)).append(" ");
		sb.append(calendar.get(Calendar.DATE)).append(" ");
		sb.append(calendar.get(Calendar.MONTH) + 1).append(" ");
		sb.append("?").append(" ");		
		sb.append(calendar.get(Calendar.YEAR));
		return sb.toString();
	}
	
	/**
	 * 判断时间表达式是否过时
	 * @param date
	 * @return
	 */
	public static boolean isLastTime(String date){
		
		String[] dates = transDateToArray(date);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(dates[0]), 
				Integer.valueOf(dates[1]) - 1,
				Integer.valueOf(dates[2]),
				Integer.valueOf(dates[3]),
				Integer.valueOf(dates[4]),
				Integer.valueOf(dates[5]));
		
		if(calendar.compareTo(Calendar.getInstance()) > 0){
			return false;
		}
		
		return true;
		
	}
	

	/**
	 * 销毁
	 * 
	 * @throws SchedulerException
	 */
	public static void destroy() throws SchedulerException
	{
		if (scheduler != null)
		{
			scheduler.shutdown();
		}
	}


}
