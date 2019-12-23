package json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;

import com.alibaba.fastjson.JSON;

import utils.HttpClientUtil;

public class biaodian {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://203.69.109.27:8080/efun/get_role_info_list?userId=1006495679&serverCode=5808&gameCode=twqqh&sign=D2CE4F5E2CA43405F0EC9C7AF1A62484";
		String aString = HttpClientUtil.invokeHttpsGet(url, 10000, 10000);
		
//		String aString = "{\"list\":[{\"level\":196,\"onCard\":\"\",\"roleid\":\"139925168\",\"name\":\"%%%%%\",\"subgame\":\"5808\"}],\"code\":\"0000\"}";
		System.out.println(aString);
		Map<String, Object> map = new HashMap<>();
//		try {
//			map = JsonUtil.TransToObject(aString, HashMap.class);
//			System.out.println(map);
//			System.out.println(JsonUtil.TransToJson(map));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		map = JSON.parseObject(aString);
		System.out.println(map);
		
		
		
	}

}
