package precreaterole;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import utils.JsonUtil;

public class suiji2 {
	
	public static void main(String[] args){
		try {
	         String encoding="UTF-8";
	         File file = new File("D://CRT下载//result.txt");
	         if(file.isFile() && file.exists()){ 
	        	 //判断文件是否存在
	             InputStreamReader read = new InputStreamReader(
	             new FileInputStream(file),encoding);//考虑到编码格式
	             BufferedReader bufferedReader = new BufferedReader(read);
	             
	             //输出文件
	             File outFile = new File("D://CRT下载//xiugaiguojia.sql");
	             if(!outFile.exists()){
	            	 outFile.createNewFile();
	             }
	             BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
	             
	             Random random = new Random();
	             
	             String lineTxt = null;
	             int i = 0;
	             while((lineTxt = bufferedReader.readLine()) != null){
	            	 Map<String,Object> map = JsonUtil.TransToObject(lineTxt,HashMap.class);
	            	 for(Map.Entry<String,Object> entry : map.entrySet()){
	            		 String sql = "update pre_create_role set nation = " + entry.getValue()
		            			 + " where userid = '" + entry.getKey().trim() + "';";
	            		 bw.write(sql);
		            	 bw.newLine(); 
	            	 }
	             }
	             read.close();
	             bw.close();
		 }else{
		     System.out.println("找不到指定的文件");
		 }
		 } catch (Exception e) {
		     System.out.println("读取文件内容出错");
		     e.printStackTrace();
		 }
	}
}
