package mysql;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mysql.bean.Moment;
import mysql.dao.MomentDao;
import utils.MybatisUtil;

public class DupilicateKeyTest {
	private static Logger logger = LoggerFactory.getLogger(DupilicateKeyTest.class);

	public static void main(String[] args) throws IOException {
		// DB 初始化
		InputStream is = new FileInputStream("./src/main/java/mysql/jdbc.properties");
		Properties properties = new Properties();
		properties.load(is);
		MybatisUtil.init("mysql/mybatis-config.xml", properties);
		Date now = new Date();
		Moment moment = new Moment(1, 199, 100023L, 100, "测试", "123$zulong", "1|2",
				"3|4", "supplement", "String pic1", "String pic2", "String pic3", "String pic4", "String remindList",
				0, now, now, 0, null, 0, 0, 0, false, 0, 0, 0, 0, "String ext1",
				"String ext2", "String ext3", "String permissions", true);
		int count = 10000;
		long startTime = System.currentTimeMillis();
		insertOrUpdate(count, moment);
//		queryAndUpdate(count, moment);
		System.out.println("spendTime=" + (System.currentTimeMillis() - startTime)/1000);
	}
	
	public static void insertOrUpdate(int count, Moment moment) {
		SqlSession session = null;
		MomentDao momentDao = null;
		for(int i = 0; i < count; i ++) {
			try{
				session =  MybatisUtil.getSession();
				momentDao = session.getMapper(MomentDao.class);
				momentDao.insert(moment);
				session.commit();
			}catch (Exception e) {
				logger.error("exception", e);
				if(session != null) {
					session.rollback();
				}
			}finally {
				if(session != null) {
					session.close();
				}
			}
		}
	}
	
	public static void queryAndUpdate(int count, Moment moment) {
		SqlSession session = null;
		MomentDao momentDao = null;
		for(int i = 0; i < count; i ++) {
			try{
				session =  MybatisUtil.getSession();
				momentDao = session.getMapper(MomentDao.class);
				Moment tmp = momentDao.queryByMomentId(moment.getMomentId());
				if(tmp != null) {
					momentDao.updateStatus(moment.getMomentId(), 0);
				}
				session.commit();
			}catch (Exception e) {
				logger.error("exception", e);
				if(session != null) {
					session.rollback();
				}
			}finally {
				if(session != null) {
					session.close();
				}
			}
		}
	}
}
