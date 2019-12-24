package quartz;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.SchedulerException;

import utils.DateUtil;

public class QuartzUtilTestMain {

	public static void main(String[] args) {
		try {
			
			QuartzUtil.init("D:\\workspace-eclipse\\mavenTest/src/main/java/quartz/quartz.properties");
			
			String taskName = "taskName";
			String group = "group";
			String triggername = "triggername";
			String triggergroup = "triggergroup";
//			Date send_date = DateUtil.ParseString("2018-03-09 17:54:00");
			Date send_date = new Date();
			
			long repeat_interval = 0L;
			int repeat_count = 0;
			Class<? extends Job> jobClass = TestJob.class;
			Map<String, Object> data1 = new HashMap<String, Object>(){
				{
					put("key",111111);
				}
			};
			
//			TaskManager.addTask(taskName, group, triggername, triggergroup, send_date, repeat_interval, repeat_count, jobClass, data);
//			String newtriggername = "newtriggername";
//			Date starttime = DateUtil.ParseString("2018-03-09 17:55:00");
//			TaskManager.updateTrigger(taskName, triggername, triggername, triggergroup, repeat_count, repeat_interval, starttime);
			QuartzUtil.addSimpleTask(taskName, group, jobClass, triggername, triggergroup, send_date, null, repeat_interval, null, data1);
			Thread.sleep(2000L);
//			QuartzUtil.pauseTask(taskName, group);
			System.out.println("pause--------------------------------------" + ",isExist=" + QuartzUtil.isExist(taskName, group));
			Thread.sleep(2000L);
			System.out.println("resume--------------------------------------" + ",isExist=" + QuartzUtil.isExist(taskName, group));
			QuartzUtil.resumeTask(taskName, group);
			Thread.sleep(1000L);
			System.out.println("remove1--------------------------------------"+ ",isExist=" + QuartzUtil.isExist(taskName, group));
			QuartzUtil.removeTask(taskName, group);
			System.out.println("resume2--------------------------------------" + ",isExist=" + QuartzUtil.isExist(taskName, group));
			
			Map<String, Object> data2 = new HashMap<String, Object>(){
				{
					put("key",222222);
				}
			};
			QuartzUtil.updateSimpleTask(taskName, group, jobClass, triggername, triggergroup, send_date, null, repeat_interval, null, data2);
//			QuartzUtil.destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
