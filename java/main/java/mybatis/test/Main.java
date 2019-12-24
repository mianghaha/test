package mybatis.test;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import utils.JsonUtil;

public class Main {

	public static void main(String[] args){
//		System.out.println(System.getProperty("user.dir"));
//		System.out.println(Main.class.getClassLoader().getResource("").getPath());
		
//		File root = new File("src/main/java/mybatis/test/");
//		File[] files = root.listFiles();
//		for(File file : files){
//			System.out.println(file.getName());
//		}
		

		
//		String resource = "C:/Users/miang/workspace/mavenTest/src/main/java/mybatis/test/mybatis-config.xml";
		String resource = "mybatis/mybatis-config.xml";

		try {
			InputStream is = Resources.getResourceAsStream(resource);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
			
			SqlSession session = null;
			try{
				session = factory.openSession();
				MomentMapper momentMapper = session.getMapper(MomentMapper.class);
				Moment moment = momentMapper.getMoment(316901348262150144L);
				List<Moment> momentList = momentMapper.getMomentByRoleId(100023L);
//				session.commit();
				System.out.println(JsonUtil.TransToJson(momentList));
			}catch(Exception e1){
				e1.printStackTrace();
				session.rollback();
			}finally{
				if(session != null){
					session.close();
				}
			}
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}
