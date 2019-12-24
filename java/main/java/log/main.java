package log;

import log.ZLogger.LEVEL;
import log.ZLogger.LogMsg;

public class main {


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZLogger logger = new ZLogger(main.class);
		
		LogMsg msg1 = logger.buildMsg().O("测试").I("最开始").P("param", "11111").F("main");
		LogMsg msg2 = msg1.clone().P("param2222", "22222");
		msg1.Flush(LEVEL.INFO);
		msg2.Flush(LEVEL.INFO);
	}

}
