package quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import utils.DateUtil;

public class TestJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(DateUtil.transToString(new Date()) + ",key1=" + context.getMergedJobDataMap().get("key"));
	}
}
