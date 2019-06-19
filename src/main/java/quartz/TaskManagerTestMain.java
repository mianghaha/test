package quartz;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagerTestMain {

	public static void main(String[] args) throws Exception {
		File file = new File("./");	
		System.out.println(file.getAbsolutePath());
		List<TaskBean> list = new ArrayList<TaskBean>();
		TaskBean taskBean = new TaskBean();
		taskBean.setClassName("quartz.TestJob");
		taskBean.setTaskName("task");
		taskBean.setTrigger("0 45 15 * * ?");
		Map<String, String> param = new HashMap<String, String>();
		param.put("gameId", "1");
		taskBean.setParam(param);
		list.add(taskBean);
		TaskManager.init("./src/main/java/quartz/quartz.properties", list);
	}
}
