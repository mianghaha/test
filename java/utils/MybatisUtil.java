package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class MybatisUtil {

	private static Logger logger = Logger.getLogger(MybatisUtil.class);
	public static String resource; 
	public static SqlSessionFactory sessionFactory;
	
	public static void init(String resource, Properties properties) throws IOException {
		if(resource == null || resource.trim().equals("")) {
			logger.warn("init:resource is null.");
			return;
		}
		
		InputStream is = Resources.getResourceAsStream(resource);
		if(properties != null) {
			sessionFactory = new SqlSessionFactoryBuilder().build(is, properties);
		}else {
			sessionFactory = new SqlSessionFactoryBuilder().build(is);
		}
	}
	
	public static SqlSession getSession() {
		if(sessionFactory == null) {
			return null;
		}else {
			return sessionFactory.openSession();
		}
	}
}
