package quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务bean
 * @author dufeng
 *
 */
public class TaskBean {
	
	public TaskBean(){
		param = new HashMap<String, String>();
	}
	
	private String taskName;//任务名称
	private String trigger; //定时配置
	private String className; //任务的类名称
	private Map<String, String> param; //参数
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	}
	
	@Override
	public String toString(){
		return "TaskBean:taskName=" + taskName + ",trigger=" + trigger + ",className=" + className;
	}

}
