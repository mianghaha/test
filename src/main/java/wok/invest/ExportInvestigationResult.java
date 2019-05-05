package wok.invest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.JsonUtil;

public class ExportInvestigationResult {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			// TODO Auto-generated method stub
			File inFile = new File("C://Users/miang/workspace/TEST/src/invest/invest_result_6_24.txt");
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
			

			String date = "2017-06-24";
			File outFile = new File("C://Users/miang/workspace/TEST/src/invest/燃烧之门调查 " + date + ".txt");
			if(!outFile.exists()){
				outFile.createNewFile();
			}
			
			int count = 0;
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			 for(int i = 0; i < contentList.size(); i ++){
				 String content = contentList.get(i);
				 String[] temp1 = content.split("\\{");
				 String[] temp2 = temp1[1].split("\\}");
				 String[] temp3 = temp1[0].split(",");
				 String userid = temp3[2];
				 
				 String answear = temp2[0];
				 answear = "{" + answear + "}";
				 Map<String, String> map = JsonUtil.TransToObject(answear, HashMap.class);
				 
				 String cellphoneAnswer = map.get("101");
				 String[] array = cellphoneAnswer.split("#");
				 if(array.length == 2){
					 String cellphone = array[1];
					 if(cellphone.length() == 11){
						 count++;
						 String nameAnswer = map.get("100");
						 String[] nameArray = nameAnswer.split("#");
						 String name = null;
						 if(nameArray.length > 1){
							 name  = nameArray[1];
						 }else{
							 continue;
						 }
						 
						 String answer97 = map.get("97");
						 String[] answer97Array = answer97.split("#");
						 String answer97Id = answer97Array[0];
						 String strAnswer97 = "对应错误";
						 if(answer97Id.equals("502")){
							 strAnswer97 = "15-19";
						 }else if(answer97Id.equals("503")){
							 strAnswer97 = "20-24";
						 }else if(answer97Id.equals("504")){
							 strAnswer97 = "25-29";
						 }else if(answer97Id.equals("505")){
							 strAnswer97 = "30-34";
						 }else if(answer97Id.equals("506")){
							 strAnswer97 = "35-39";
						 }else if(answer97Id.equals("516")){
							 strAnswer97 = "40及以上";
						 }
						 
						 String answer98 = map.get("98").trim();
						 String[] answer98Array = answer98.split("#");
						 String answer98Id = answer98Array[0];
						 String strAnswer98 = "对应错误";
						 if(answer98Id.equals("508")){
							 strAnswer98 = "北京";
						 }else if(answer98Id.equals("509")){
							 strAnswer98 = "上海";
						 }else if(answer98Id.equals("510")){
							 strAnswer98 = "深圳";
						 }else if(answer98Id.equals("511")){
							 strAnswer98 = "广州";
						 }else if(answer98Id.equals("512")){
							 strAnswer98 = "其他";
						 }
						 
						 String str = userid + ",name=" + name + ",cellphone=" + cellphone + ",请问您的年龄处于哪个阶段=" + strAnswer97
								 + ",请问您的所在地是=" + strAnswer98;
						 bw.write(str);
						 bw.newLine();
						 System.out.println(str + ",count=" + count);
					 }
				 }
			 }
			 
			 bw.flush();
			 bw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
