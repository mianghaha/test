package atpinvest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ExportInvestPhoneNum {
	private static Set<String> investPhoneQuesIdSet = new HashSet<>();
	static {
		investPhoneQuesIdSet.add("119:626#");
		investPhoneQuesIdSet.add("143:756#");
		investPhoneQuesIdSet.add("146:758#");
	}

	public static void main(String[] args) throws Exception {
		File outOutFile = new File("./src/main/java/atpinvest/lyb_invest_phone.txt");
		if(outOutFile.exists()) {
			outOutFile.delete();
			outOutFile.createNewFile();
		}else {
			outOutFile.createNewFile();
		}
		FileReader inFile = new FileReader("./src/main/java/atpinvest/invest_lyb.sql");
		BufferedReader bf = new BufferedReader(inFile);
		String line;
		Map<String, String> answerMap = new HashMap<String, String>();
		while ((line = bf.readLine()) != null) {
			if(line.contains("userid"))continue;
			String[] contentArray = line.split("\t");
			String userId = contentArray[0];
			String content = contentArray[1];
			String[] answerArray = content.split(",");
			for(String answer : answerArray) {
				for(String targetQuestion : investPhoneQuesIdSet) {
					if(answer.contains(targetQuestion)) {
						answer = answer.replace(targetQuestion, "");
						if(!answer.trim().equals("")) {
							answerMap.put(userId, answer);
						}
					}
				}
			}
		}
		bf.close();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outOutFile));
		for(Entry<String, String> entry : answerMap.entrySet()) {
			String content = entry.getKey() + " " + entry.getValue();
			bw.append(content);
			bw.newLine();
		}
		bw.close();
	}
}
