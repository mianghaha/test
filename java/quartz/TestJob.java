package quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import utils.DateUtil;

public class TestJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(DateUtil.transToString(new Date()) + ",gameId=" + context.getMergedJobDataMap().get("gameId"));
		
		String jobName = (String) context.getMergedJobDataMap().get("repeatJobName");
		if(jobName == null) {
			JobKey key = context.getJobDetail().getKey();
			jobName = key.getName() + "_repeat";
			String jobGroup = key.getGroup() + "_repeat";;
			String triggerName = jobName + "_trigger";
			String triggerGroup = jobGroup + "_trigger";
			context.getMergedJobDataMap().put("repeatJobName", jobName);
			TaskManager.addTask(jobName, jobGroup, triggerName, triggerGroup, new Date(), 5000L, 3, this.getClass(),
					context.getMergedJobDataMap());
		}

	}
}
