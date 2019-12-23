package leetcode;

import java.util.Stack;

public class LongestValidParentheses {
	
	public static void main(String[] args) {
		String string = ")(())";
		System.out.println(solution1(string));
	}

	public static int solution1(String s) {
		if(s == null || s.length() == 0)return 0;
        
        int len = s.length();
        Stack<Integer> stack = new Stack<Integer>();
        int leftCount = 0;
        int maxCount = 0;
        int start = 0;
        
        for(int i = 0; i < len; i++){
            char c = s.charAt(i);
            if(c == '('){
                leftCount++;
                stack.push(i);
            }else{
                if(leftCount == 0){
                    start = i + 1;
                    stack.clear();
                }else{
                    int count = 0;
                    if(leftCount == 1){
                        count = i - start + 1;
                    }else{
                        stack.pop();
                        int last = stack.pop();
                        stack.push(last);
                        count = i - last;
                    }  
                    if(count > maxCount){
                        maxCount = count;
                    }
                    leftCount--;
                }
            }
        }
        return maxCount;
	}
}
