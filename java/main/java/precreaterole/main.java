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

import utils.JsonUtil;

public class main {
	
	public static void main(String[] args){
		try {
	         String encoding="UTF-8";
	         File file = new File("D://CRT下载//special_shuanglang.txt");
	         Map<String,String> map = new HashMap<String,String>();
	         if(file.isFile() && file.exists()){ //判断文件是否存在
	             InputStreamReader read = new InputStreamReader(
	             new FileInputStream(file),encoding);//考虑到编码格式
	             BufferedReader bufferedReader = new BufferedReader(read);
	             String lineTxt = null;
	             int i = 0;
	             while((lineTxt = bufferedReader.readLine()) != null){
	            	 String[] array = lineTxt.split("	");
	            	 map.put(array[0],array[1]);
	             }
	             read.close();
	             
	             String json = JsonUtil.TransToJson(map);
	             File outFile = new File("D://CRT下载//shuanglang_json.txt");
	             if(!outFile.exists()){
	            	 outFile.createNewFile();
	             }
	             
	             BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
	             bw.write(json);
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
