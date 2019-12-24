package leetcode;

public class ShortestSubarray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A = {2, -1, 2};
		int K = 3;
		System.out.println(solution1(A, K));
	}
	
	public static int solution1(int[] A, int K) {
        if(A == null || A.length == 0)return -1;
        
        int len = A.length;
        int[] cache = new int[len];
        for(int i = 0; i < len; i++) {
        	cache[i] = A[i];
        	if(A[i] >= K)return 1;
        }
        
        for(int i = 2; i <= len; i++) {
        	for(int j = 0; j <= len - i; j++) {
        		int tmp = cache[j] + A[j + i - 1];
        		if(tmp >= K) {
        			return i;
        		}else {
        			cache[j] = tmp;
        		}
        	}
        }
        return -1;
	}
}
