package db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dbname = "atpdb";
		DBHelper db1 = new DBHelper(dbname);//创建DBHelper对象  
		String sql = "select * from account";//SQL语句  
		
        try {  
        	ResultSet ret = db1.excuteQuery(sql);//执行语句，得到结果集  
            while (ret.next()) {  
                String userid = ret.getString(1);  
                String name = ret.getString(2);  
                String password = ret.getString(3);  
                String nickname = ret.getString(4);  
                System.out.println(userid + "\t" + name + "\t" + password + "\t" + nickname );  
            }//显示数据  
            ret.close();  
            db1.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
	}

}
