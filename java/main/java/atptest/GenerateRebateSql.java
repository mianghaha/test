package atptest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateRebateSql {

	public static void main(String[] args) {
		try{
			// TODO Auto-generated method stub
			String filename = "周年回流账号_2";
			File inFile = new File("C://Users/miang/workspace/TEST/src/atptest/" + filename + ".txt");
			if(!inFile.exists()){
				System.out.println("in file not exist");
				return;
			}
			
			List<String> contentList = new ArrayList<String>();
			InputStreamReader read = new InputStreamReader(new FileInputStream(inFile), "UTF-8");//考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while((lineTxt = bufferedReader.readLine()) != null){
				contentList.add(lineTxt);
			}
			read.close();
			System.out.println("contentList.size=" + contentList.size());

			File outFile = new File("C://Users/miang/workspace/TEST/src/atptest/" + filename + ".sql");
			if(!outFile.exists()){
				outFile.createNewFile();
			}
             
			int gameid = 208;
			int serverid = 1;
			
			//content格式 openId|roleId|roleName|serverId|photoId|avatar|level|lastLoginTime|extInfo
			 BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			 bw.write("use atpdb;");
			 bw.newLine();
			 
			 StringBuilder sb = new StringBuilder();
			 Set<String> set = new HashSet<String>();
			 int count = 0;
			 int limit = 10000;
			 int success = 0;
			 int error = 0;
			 for(int i = 0; i < contentList.size(); i ++){
				 if(count == 0){
					 bw.write("INSERT INTO rebate_user_new (serialid,userid,actid,award_item,awrad_currency) values ");
				 }
				 
				 String content = contentList.get(i);
				 String[] temp = content.split("\\#");
				 if(temp.length != 3 && temp.length != 4){
					 System.out.println("error content,index=" + i + ",content=" + content);
					 count++;
					 error++;
					 continue;
				 }
				 
				 String currency = "";
				 if(temp.length == 4){
					 currency = temp[3];
				 }
				 
				 String openid = temp[0];
				 int index = openid.indexOf("@");
				 if(index != -1){
					 openid = openid.substring(0, index);
				 }
				 
				 if(set.contains(openid)){
					 System.out.println("duplicate user,index=" + i + ",content=" + content);
					 error++;
					 continue;
				 }
				 
				 int serialid = 0;
				 String actid = temp[1];
				 if(actid.equals("1008")){
					 serialid = 2;
				 }else if(actid.equals("1009")){
					 serialid = 1;
				 }else{
					 System.out.println("error actid,index=" + i + ",content=" + content);
					 error++;
					 continue;
				 }
				 
				 
				 sb.delete(0, sb.length());
			     sb.append("(");
			     sb.append(serialid).append(",");
			     sb.append("'").append(openid).append("'").append(",");
			     sb.append("'").append(temp[1]).append("'").append(",");
			     sb.append("'").append(temp[2]).append("'").append(",");
			     sb.append("'").append(currency).append("'");
			     sb.append(")");
			     
			     if(i == contentList.size() - 1 || count == limit){
			    	 sb.append(";");
			     }else{
			    	 sb.append(",");
			     }
			     
			     set.add(openid);
			     bw.write(sb.toString());
				 bw.newLine();
				 count++;
				 success++;
				 
				 if(count > limit){
					 count = 0;
				 }
			 }
			 
			 System.out.println("success=" + success);
			 System.out.println("error=" + error);
			 
			 bw.flush();
             bw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
