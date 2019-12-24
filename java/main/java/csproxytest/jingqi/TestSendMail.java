package csproxytest.jingqi;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestSendMail {

	public static void main(String[] args){
		try{
			String url = "http://localhost:10080/jingqi/give_awards";
//			String url = "http://218.32.221.70:8080/jingqi/give_awards";

			String str_privatekey = "8CEE3E6C88C4F5BC0CFED86DD3C1E573";
			String userId = "1587461";
			long roleId = 0;
			int serverCode  = 8888;
			String elementType = "1";
			String elementId = "1";
			String count = "1";
			String timeStamp = String.valueOf(System.currentTimeMillis());

            final StringBuilder source = new StringBuilder();
            source.append(serverCode).append(userId).append(elementType).append(elementId).append(count).append(str_privatekey)
                    .append(timeStamp);
			String sign = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(source.toString().getBytes("UTF-8"))));
			String title = "测试邮件主题";
			String content = "测试邮件内容";
			
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();

			param.put("userId", userId);
			param.put("roleId", roleId);
			param.put("serverCode", serverCode);
			param.put("elementType", elementType);
			param.put("elementId", elementId);
			param.put("count", count);
			param.put("timeStamp", timeStamp);
			param.put("title", title);
			param.put("content", content);
			param.put("sign", sign);			
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
