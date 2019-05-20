package leetcode;

public class LongestPalindrome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String string = "babad";
		String string = "aaaa";
//		String string = "ccc";
//		String string = "abcba";
		System.out.println(longestPalindrome(string));
	}
	
	public static String longestPalindrome(String s) {
        if(s == null || s.equals("") || s.length() == 1)return s;
        
        int index = 0;
        int max = 1;
        int length = s.length();
        //对回文分奇数偶数 0 奇数 1 偶数
        int[][] cache = new int[length][2];
        for(int i = 1; i < length; i++){
            int tmp = 0;
            char c = s.charAt(i);
            int count1 = cache[i - 1][0];
            if(count1 == 0 && i - 2 >= 0 && s.charAt(i - 2) == c){
                cache[i][0] = 3;
                tmp = 3;
            }else if(count1 > 0 && (count1 = i - count1 - 1) >= 0 && s.charAt(count1) == c){
                cache[i][0] = cache[i - 1][0] + 2;
                tmp = cache[i][0];
            }
            
            int count2 = cache[i - 1][1];
            if(count2 == 0 && s.charAt(i - 1) == c){
                cache[i][1] = 2;
                tmp = 2;
            }else if(count2 > 0 && (count2 = i - count2 - 1) >= 0 && s.charAt(count2) == c){
                cache[i][1] = cache[i - 1][1] + 2;
                tmp = cache[i][1];
            }
            
            if(tmp > 0 && tmp > max){
                max = tmp;
                index = i - tmp + 1;
            }
        }
        return s.substring(index, index + max);
    }

}
