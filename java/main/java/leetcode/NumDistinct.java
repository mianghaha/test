package leetcode;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;

public class NumDistinct {

    public static int solutin1(String s, String t) {
    	return recursive(s, 0, t, 0, 0);
    }
    
    public static int recursive(String s, int indexS, String t, int indexT, int count) {
    	if(indexT == t.length()) {
    		return count + 1;
    	}
    	
    	char c1 = t.charAt(indexT); 
    	while(indexS < s.length()) {
    		char c2 = s.charAt(indexS);
    		if(c2 == c1) {
    			count = recursive(s, indexS + 1, t, indexT + 1, count);
    			count = recursive(s, indexS + 1, t, indexT, count);
    			return count;
    		}
    		indexS++;
    	}
    	return count;
    }
    
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
    	//3
 //   	System.out.println(solutin2("rabbbit", "rabbit"));
    	
    	//5
    	System.out.println(solutin2("babgbag", "bag"));
    }
    
    public static int solutin2(String s, String t) throws JsonGenerationException, JsonMappingException, IOException {
    	if(s == null || s.length() == 0 || t == null || t.length() == 0)return 0;
    	
    	int lenS = s.length();
    	int lenT = t.length();
    	int[][] dp = new int[lenT][lenS];
    	
    	int size = 0;
    	for(int j = 0; j < lenS; j++) {
    		if(s.charAt(j) == t.charAt(0)) {
    			dp[0][j] = ++size;
    		}else {
    			dp[0][j] = size;
    		}
    	}
    	
    	for(int i = 1; i < t.length(); i++) {
    		for(int j = i; j < s.length(); j++) {
    			dp[i][j] = dp[i][j - 1];
    			if(t.charAt(i) == s.charAt(j)) {
    				dp[i][j] += dp[i - 1][j - 1];
    			}
    		}
    	}
    	System.out.println(JsonUtil.TransToJson(dp));
    	return dp[lenT - 1][lenS - 1];
    }
}
