package csproxytest.xlegend;

import java.util.SortedMap;
import java.util.TreeMap;

import utils.Byte2CharUtil;
import utils.HttpClientUtil;
import utils.MD5Util;

public class TestGiveAward {

	public static void main(String[] args){
		try{
			String url = "http://127.0.0.1:9097/xlegend/give_awards";
			
			String uid = "123456789";	
			String sid = "19990";
			String cid  = "19283123";
			String title = "title";
			String body = "body";
			String items = "1:cash_000:1000,1:item_7214:1";
			
			String privatekey = "8CEE3E6C88C4F5BC0CFED86DD3C1E573";
			String str_sign =  sid + cid + items + privatekey;
			String md5Str = new String(Byte2CharUtil.encodeHex(MD5Util.getMD5(str_sign.getBytes("UTF-8"))));
			
			SortedMap<String, Object> param =  new TreeMap<String, Object>();
			param.put("uid", uid);
			param.put("sid", sid);
			param.put("cid", cid);
			param.put("title", title);
			param.put("body", body);
			param.put("items", items);
			param.put("md5str", md5Str);
			
			String encode = "UTF-8";
			String result = HttpClientUtil.invokePost(url, param, encode, 10000, 10000);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
