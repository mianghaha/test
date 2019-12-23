package genetic;

import java.util.HashMap;
import java.util.Map;

public class Imp extends BaseClass{
	
	Map<String, String> a = new HashMap<String, String>(){
		{
			put("1","1");
		}
	};

	@Override
	public <T> T getCacheObject(String key) {
		// TODO Auto-generated method stub
		return (T) a;
	}

}
