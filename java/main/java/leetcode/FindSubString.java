package leetcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;

public class FindSubString {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
//		String string = "barfoothefoobarman";
//		String[] strings = {"foo","bar"};
		
//		String string = "a";
//		String[] strings = {"a"};
		
//		String string = "barfoofoobarthefoobarman";
//		String[] strings = {"bar","foo","the"};

//		String string = "foobarfoobar";
//		String[] strings = {"foo","bar"};
		
//		String string = "aaabdbdabbdccadbdcbbacdbddadcaacaadabdacadacaaadcbacdaddda";
//		String[] strings = {"cbac","dadd"};
		
//		String string = "wordgoodgoodgoodbestword";
//		String[] strings = {"word","good","best","good"};
		
//		String string = "barfoothefoobarman";
//		String[] strings = {"foo","bar"};
		
//		String string = "wordgoodgoodgoodbestword";
//		String[] strings = {"word","good","best","word"};
		
		//13
		String string = "lingmindraboofooowingdingbarrwingmonkeypoundcake";
		String[] strings = {"fooo","barr","wing","ding","wing"};
		
		System.out.println(JsonUtil.TransToJson(solution(string, strings)));
	}
	
	public static List<Integer> solution(String s, String[] words) {
        List<Integer> list = new ArrayList<>();
        if(s == null || s.length() == 0 || words == null || words.length == 0){
            return list;
        }
        
        HashMap<String, Integer> wordsMap = new HashMap<>();
        for(int i = 0; i < words.length; i++){
            wordsMap.put(words[i], wordsMap.getOrDefault(words[i], 0) + 1);
        }
        
        int len = words[0].length();
        int start = 0;
        int sLength = s.length();
        Queue<String> cache = new LinkedList<>();
        for(int i = 0; i < sLength - len; i++){
            String sub = s.substring(i, i + len);
            if(wordsMap.containsKey(sub)){
                int count = wordsMap.get(sub);
                if(count == 1){
                	wordsMap.remove(sub);
                }else{
                	wordsMap.put(sub, count - 1);
                }
                cache.add(sub);
                if(wordsMap.isEmpty()) {
                	list.add(start);
                }
            }else{
                if(start != i){
                	boolean isBreak = false;
                	while(!cache.isEmpty()) {
                		String first = cache.poll();
                    	start = start + len;
                		if(first.equals(sub)) {
                			cache.add(first);
                            if(wordsMap.isEmpty()) {
                            	list.add(start);
                            }
                            isBreak = true;
                            break;
                		}else {
                			wordsMap.put(first, wordsMap.getOrDefault(first, 0) + 1);
                		}
                	}
                	if(!isBreak) {
                    	start++;
                	}
                }else {
                	start++;
                }
            }
        }
    	return list;
    }
}
