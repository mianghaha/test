package string;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test1 {
	
	public class CommandMapListResultBean extends CommonResultBean{

		private List<Query_command_finished> commandmaplist;

		public List<Query_command_finished> getCommandmaplist() {
			return commandmaplist;
		}


		public void setCommandmaplist(List<Query_command_finished> commandmaplist) {
			this.commandmaplist = commandmaplist;
		}

		public String toString() {
			String aString = commandmaplist == null ? null : commandmaplist.size() + "";
			return "CommandTextListResultBean [commandmaplist=" + commandmaplist == null ? "null" : (commandmaplist.size() + "") + "]";
		}

	}
	public class Query_command_finished{
		
	}
	
	public class CommonResultBean extends MessageResultBean
	{
		public Map<Object, Object> data;
		
		public CommonResultBean()
		{
			super();
			data = new HashMap<Object, Object>();
		}

		public CommonResultBean(Integer retcode, String error_msg)
		{
			this.retcode = retcode;
			this.error_msg = error_msg;
		}

		public Map<Object, Object> getData() {
			return data;
		}

		public void setData(Map<Object, Object> data) {
			this.data = data;
		}

		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("CommonResultBean [");
			if (retcode != null)
			{
				builder.append("retcode=");
				builder.append(retcode);
				builder.append(", ");
			}
			if (error_msg != null)
			{
				builder.append("error_msg=");
				builder.append(error_msg);
				builder.append(", ");
			}
			if (data != null)
			{
				builder.append("data=");
				builder.append(data);
			}
			builder.append("]");
			return builder.toString();
		}
	}
	
	public class MessageResultBean
	{
		public Integer retcode;
		public String error_msg;
		
		public MessageResultBean()
		{
			retcode = 123;
			error_msg = "123";
		}

		public MessageResultBean(Integer retcode, String error_msg)
		{
			super();
			this.retcode = retcode;
			this.error_msg = error_msg;
		}

		public Integer getRetcode()
		{
			return retcode;
		}

		public void setRetcode(Integer retcode)
		{
			this.retcode = retcode;
		}

		public String getError_msg()
		{
			return error_msg;
		}

		public void setError_msg(String error_msg)
		{
			this.error_msg = error_msg;
		}

		@Override
		public String toString() {
			return "MessageResultBean [retcode=" + retcode + ", error_msg=" + error_msg + "]";
		}
	}

	public static void main(String[] args){
		test1 test1 = new test1();
		CommandMapListResultBean bean = test1.new CommandMapListResultBean();
		System.out.println(bean);
	}
}
