package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {  
    public static String url = "jdbc:mysql://10.236.254.202:3307/";
    public static final String name = "com.mysql.jdbc.Driver";  
    public static String user = "acspassport";  
    public static String password = "123s56..";  
    
    
    public Connection conn = null;  
    public PreparedStatement pst;
    
    public DBHelper(String dbName) {  
        try {
        	url = url + dbName;
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    public DBHelper(String url, String user, String password) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }  
    
    public ResultSet excuteQuery(String sql) throws SQLException{
    	pst = conn.prepareStatement(sql);//准备执行语句 
    	return pst.executeQuery();
    }
  
    public void close() {  
        try { 
        	this.pst.close();
            this.conn.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}  
