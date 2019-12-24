package csproxytest.tencent;

import java.util.Map.Entry;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.SortedMap;
import java.util.TreeMap;

import utils.HttpClientUtil;
import utils.MD5Util;

public class TestQueryRole {

	public static void main(String[] args){
		try{
			String url = "http://localhost:9080/tencent/get_roles";
//			String url = "http://1.119.16.71:19080/tencent/get_roles";
			String appKey = "6568vG1FKLsnFyuT";

			String openid = "BCA973D34E100237D9256DD71AE15E2D";
			String appid = "1106130434";
			String area = "qq";
			String timestamp = System.currentTimeMillis() / 1000 + "";
			String partition = "9901";
			String roleid = "4204205";
			
            final StringBuilder pKeySrouce = new StringBuilder();
            pKeySrouce.append(openid).append(appKey).append(timestamp);
            final String pkey = MD5Util.getMD5Digest(pKeySrouce.toString());
			
			SortedMap<String, String> param =  new TreeMap<String, String>();
			param.put("openid", openid);
			param.put("appid", appid);
			param.put("area", area);
			param.put("timestamp", timestamp);
			param.put("pkey", pkey);
			param.put("partition", partition);
			param.put("roleid", roleid);
			
			final StringBuilder source = new StringBuilder();
			for(Entry<String, String> entry : param.entrySet()) {
				source.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			source.deleteCharAt(source.length() - 1);
            String paramSign = URLEncoder.encode(URLDecoder.decode(source.toString(), "UTF-8"), "UTF-8");
            paramSign = paramSign.replace("*", "%2A").replace("+", "%2B").replace("=", "%3D").replace("/","%2F");
            
            final StringBuilder signSource = new StringBuilder();
            signSource.append("GET&").append(URLEncoder.encode("/tencent/get_roles", "UTF-8")).append("&").append(paramSign);
            System.out.println("signSource=" + signSource);
            
            final String encryptKey = appKey + "&";
            String mySig = Base64.encode(HMAC.hmacSha1(signSource.toString(), encryptKey), "UTF-8");
            mySig.replace("+", " ");
			param.put("sig", mySig);
            
			String encode = "UTF-8";
			String result = HttpClientUtil.invokeGet(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
