package csproxytest.jingqi;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestGetRoleInfo {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:10080/jingqi/get_role_info_list";
//			String str_privatekey = "8CEE3E6C88C4F5BC0CFED86DD3C1E573";
			
			String url = "http://47.90.28.111:10080/jingqi/get_role_info_list";
			String str_privatekey = "8oizkziuvuajh8k+kfzxjtHesevukzfR";

			String userId = "316893111";
			int serverCode  = 11005;
			String timeStamp = String.valueOf(System.currentTimeMillis());

            final StringBuilder source = new StringBuilder();
            source.append(userId).append(serverCode).append(str_privatekey).append(timeStamp);
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(source.toString().getBytes("UTF-8"))));
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();

			param.put("userId", userId);
			param.put("serverCode", serverCode);
			param.put("timeStamp", timeStamp);
			param.put("sign", sign);			
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
