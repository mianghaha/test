package gda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try{
			// TODO Auto-generated method stub
			String filename = "result_10";
			File inFile = new File("C://Users/miang/workspace/TEST/src/gda/" + filename + ".txt");
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

			File outFile = new File("C://Users/miang/workspace/TEST/src/gda/" + filename + ".sql");
			if(!outFile.exists()){
				outFile.createNewFile();
			}
             
			int gameid = 208;
			int serverid = 1;
			
			//content格式 openId|roleId|roleName|serverId|photoId|avatar|level|lastLoginTime|extInfo
			 BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			 bw.write("use gdadb;");
			 bw.newLine();
			 
			 int count = 0;
			 int limit = 10000;
			 int success = 0;
			 int error = 0;
			 for(int i = 0; i < contentList.size(); i ++){
				 if(count == 0){
					 bw.write("INSERT INTO profile (game_id,open_id,role_id,role_name,server_id,photo_id,avatar,level,last_login_time,ext_info) values ");
				 }
				 
				 String content = contentList.get(i);
				 String[] temp = content.split("\\|");
				 if(temp.length != 8){
					 System.out.println("error content,index=" + i + ",content=" + content);
					 count++;
					 error++;
					 continue;
				 }
				 
				 String openid = temp[0];
				 int index = openid.indexOf("@");
				 if(index != -1){
					 openid = openid.substring(0, index);
				 }
				 
			     StringBuilder sb = new StringBuilder();
			     sb.append("(");
			     sb.append(gameid).append(",");
			     sb.append("'").append(openid).append("'").append(",");
			     sb.append(temp[1]).append(",");
			     sb.append("'").append(temp[2]).append("'").append(",");
			     sb.append(temp[3]).append(",");
			     sb.append("'").append(temp[4]).append("'").append(",");
			     sb.append("'").append(temp[5]).append("'").append(",");
			     sb.append(temp[6]).append(",");
			     sb.append(temp[7]).append(",");
			     sb.append("''");
			     sb.append(")");
			     
			     if(i == contentList.size() - 1 || count == limit){
			    	 sb.append(";");
			     }else{
			    	 sb.append(",");
			     }
			     
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
