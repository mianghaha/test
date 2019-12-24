package kiwi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import utils.DateUtil;

public class ProductSql {

	public static void main(String[] args) {
		
		
		int productId = 1;
		int gameId = 102;
		String[] virtualCodeArray = {"ASDASDASDASD", "fasdasdasd", "BBBBBBBBBBB","5555555","66666666666"};
		List<String> virtualCodeList = Arrays.asList(virtualCodeArray);
		
		// TODO Auto-generated method stub
		String now = DateUtil.transToString(new Date());
		String leftModel = "(" + productId + ",'";
		String rightModel = "','" + now + "'," + gameId + ")";
		
		int limitCount = 2;
		int amount = (int)Math.ceil((double)virtualCodeList.size() / limitCount);
		List<String> sqlValList = new ArrayList<String>();
		//拼接语句
		for(int k = 0; k < amount; k++) {
			StringBuilder sb = new StringBuilder();
			int num = virtualCodeList.size() - limitCount * k;
			if(num > limitCount) {
				num = limitCount;
			}
    		for(int i = 0; i < num; i++) {
    			int index = k * limitCount + i;
    			String virtualCode = virtualCodeList.get(index);
    			sb.append(leftModel).append(virtualCode).append(rightModel).append(",");
    		}
    		//消除最后一句的逗号
    		sb.deleteCharAt(sb.length() - 1);
    		sqlValList.add(sb.toString());
    		System.out.println(sb.toString());
		}
	}

}
