package csproxytest.yifanda;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestSendMail {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:80/evatar/give_awards";
			String url = "http://218.32.221.70:8080/evatar/give_awards";

			String str_privatekey = "8CEE3E6C88C4F5BC0CFED86DD3C1E573";
			String userId = "1587461";
			long roleId = 0;
			String serialNo = "test1"; 
			String gameCode = "208";
			int serverCode  = 8888;			
			String packageId = "1";
			String packetnum = "1";

			String str_sign = userId + roleId + serverCode + gameCode + serialNo + packageId + packetnum
					+ str_privatekey;
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
			String subject = "测试邮件主题";
			String content = "测试邮件内容";
			
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();

			param.put("userId", userId);
			param.put("roleId", roleId);
			param.put("serialNo", serialNo);
			param.put("gameCode", gameCode);
			param.put("serverCode", serverCode);
			param.put("packageId", packageId);
			param.put("packetnum", packetnum);
			param.put("md5Str", sign);
			param.put("subject", subject);
			param.put("content", content);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
