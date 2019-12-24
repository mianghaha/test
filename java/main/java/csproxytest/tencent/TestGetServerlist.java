package csproxytest.tencent;

import java.util.Map.Entry;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.SortedMap;
import java.util.TreeMap;

import utils.HttpClientUtil;
import utils.MD5Util;

public class TestGetServerlist {

	public static void main(String[] args){
		try{
//			String url = "http://localhost:9080/tencent/get_zones";
//			String url = "http://1.119.16.71:19080/tencent/get_zones";
			String url = "http://csproxy-lyb.zulong.com/tencent/get_zones";
			String appKey = "6568vG1FKLsnFyuT";

			String appid = "1106130434";
			String area = "qq";
			String timestamp = System.currentTimeMillis() / 1000 + "";
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("appid", appid);
			param.put("area", area);
			param.put("timestamp", timestamp);
			
			final StringBuilder source = new StringBuilder();
			for(Entry<String, Object> entry : param.entrySet()) {
				source.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			source.deleteCharAt(source.length() - 1);
            String paramSign = URLEncoder.encode(URLDecoder.decode(source.toString(), "UTF-8"), "UTF-8");
            paramSign = paramSign.replace("*", "%2A").replace("+", "%2B").replace("=", "%3D").replace("/","%2F");
            
            final StringBuilder signSource = new StringBuilder();
            signSource.append("GET&").append(URLEncoder.encode("/tencent/get_zones", "UTF-8")).append("&").append(paramSign);
            
            final String encryptKey = appKey + "&";
            String mySig = Base64.encode(HMAC.hmacSha1(signSource.toString(), encryptKey), "UTF-8");
            mySig.replace("+", " ");
			param.put("sig", mySig);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
