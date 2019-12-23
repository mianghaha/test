package log;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by WangJQ on 2017/5/12.
 *
 *	NLOG(ERR, "testlog") // 声明日志级别和关键字
 *		.P("userid", userid) // 输出Octets
 *		.P("roleid", 123LL) // 整数
 *		.P("fv", 23.4f) // 浮点数
 *		.P("items", l) // 自定义结构列表
 *		.P("ted", 1.22222, 2) // 指定小数点后的位数
 *		.X("hex", 33445) // 16进制整数
 *		.O("p1=v1:p2=v2:p3=v3"); // 原样输出
 *
 * 以上代码将输出:
 * 2016-07-28 16:22:07|host|process|testlog:userid=aaa@pp:roleid=123:fv=23.4:items=1,2;2,3:ted=1.22:hex=0x82a5:p1=v1:p2=v2:p3=v3
 */
public class ZLogger
{
    private Logger logger;
    private static ConcurrentHashMap<Class<?>, ZLogger> logs = new ConcurrentHashMap<Class<?>, ZLogger>();
    
    public ZLogger (Class<?> T) {
        this.logger = LoggerFactory.getLogger(T);
    }
    
    public enum LEVEL {
        DEBUG, INFO, WARN, ERROR
    }
    
    public enum Separator{
    	COMMA(","),
    	EQUAL("="),
    	COLON(":"),
    	LINE("|"),
    	;
    	
    	private String str;
    	
    	private Separator(String str){
    		this.str = str;
    	}
    	
    	public String getStr(){
    		return str;
    	}
    }
    
    public static ZLogger getLogger(Class<?> cls) {
    	ZLogger logger = logs.get(cls);
    	if(logger == null) {
    		logger = new  ZLogger(cls);
    		logs.put(cls, logger);
    	}
    	return logger;
    }
    
    public LogMsg buildMsg(){
    	return new LogMsg();
    }
    
    public class LogMsg{
    	private StringBuilder logStream = new StringBuilder();
    	private String funcName;
        private	Exception exception;
        private LEVEL level;
    	
    	public LogMsg(){
    		level = LEVEL.INFO;
    	}
    	
    	public LogMsg(StringBuilder logStream, String funcName, Exception exception, LEVEL level) {
    		this.logStream = logStream;
    		this.funcName = funcName;
    		this.exception = exception;
    		this.level = level;
    	}
    	
    	public LogMsg Flush() {
    		return Flush(level);
    	}
    	
    	public LogMsg Flush(LEVEL level) {
	        if(funcName != null && funcName.length() > 0){
	        	logStream.insert(0, Separator.COLON.getStr()).insert(0, funcName);
	        }
	        
	        if(logStream.length() != 0){
	        	logStream.delete(logStream.length() - 1, logStream.length());
	        }
	        
	        Print(level);
	        logStream.delete(0, logStream.length());
	        funcName = null;
	        return this;
    	}

	    public void Print(LEVEL level) {
	        switch(level) {
	            case DEBUG:
	            	if(exception == null){
	            		logger.debug(logStream.toString());
	            	}else{
	            		logger.debug(logStream.toString(), exception);
	            	}
	                break;
	            case INFO:
	            	if(exception == null){
	            		logger.info(logStream.toString());
	            	}else{
	            		logger.info(logStream.toString(), exception);
	            	}
	                break;
	            case WARN:
	            	if(exception == null){
	            		logger.warn(logStream.toString());
	            	}else{
	            		logger.warn(logStream.toString(), exception);
	            	}
	                break;
	            default:
	            	if(exception == null){
		                logger.error(logStream.toString());
	            	}else{
	            		logger.error(logStream.toString(), exception);
	            	}
	                break;
	        }
	    }

	    /**
	     * 增加参数类日志
	     * @param attr
	     * @param object
	     * @return
	     */
	    public LogMsg P(String attr, Object object) {
	        logStream.append(attr).append(Separator.EQUAL.getStr()).append(object == null ? "null" : object.toString())
	        	.append(Separator.COMMA.getStr());
	        return this;
	    }
	    
	    public LogMsg P(String attr, Object object, Separator separator) {
	        logStream.append(attr).append(Separator.EQUAL.getStr()).append(object == null ? "null" : object.toString())
	        .append(separator.getStr());
	        return this;
	    }

	    /**
	     * 将字符串插入到最后
	     * @param s
	     * @return
	     */
	    public LogMsg O(String s) {
	        logStream.append(s).append(Separator.COMMA.getStr());
	        return this;
	    }
	    
	    public LogMsg O(String s, Separator separator) {
	        logStream.append(s).append(separator.getStr());
	        return this;
	    }
	    
	    /**
	     * 将字符串插入到最前
	     * @param s
	     * @return
	     */
	    public LogMsg I(String s) {
	        logStream.insert(0, Separator.COMMA.getStr()).insert(0, s);
	        return this;
	    }
	    
	    public LogMsg I(String s, Separator separator) {
	        logStream.insert(0, separator.getStr()).insert(0, s);
	        return this;
	    }
	    
	    /**
	     * 设置需要打印的异常
	     * @param exception
	     * @return
	     */
	    public LogMsg E(Exception exception) {
	        this.exception = exception;
	        return this;
	    }
	    
	    /**
	     * 设置方法名
	     * @param s
	     * @return
	     */
	    public LogMsg F(String s) {
	    	this.funcName = s;
	        return this;
	    }
	    
	    /**
	     * 设置打印级别
	     * @param level
	     * @return
	     */
	    public LogMsg setLevel(LEVEL level) {
	    	this.level = level;
	        return this;
	    }
	    
	    public LogMsg clone() {
	    	StringBuilder log = new StringBuilder(this.logStream.toString());
	    	return new LogMsg(log, this.funcName, this.exception, this.level);
	    }
    }
}
