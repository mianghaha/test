package leetcode;

public class LongestOnes {

	public static void main(String[] args) {
		int[] A = {1,
				1,
				1,
				0,
				0,
				0,
				1,
				1,
				1,
				1,
				0};
		int K = 2;
		System.out.println(solution2(A, K));
	}
	
	public int soulution1(int[] A, int K) {
        int start = 0;
        int end = 0;
        int count = 0;
        int firstZeroIndex = -1;
        int max = 0;
        
        for(int i = 0; i < A.length; i++){
            if(A[i] == 1){
                end++;
            }else{
                if(count < K){
                    if(count == 0){
                       firstZeroIndex = i;
                    }
                    count++;
                    end++;
                }else{
                    if(firstZeroIndex != -1){
                        if(K == 1){
                            firstZeroIndex = i;
                            start = i;
                        }else{       
                            int z = firstZeroIndex + 1;
                            start = firstZeroIndex + 1;
                            while(A[z] == 1){
                                z++;
                            }
                            firstZeroIndex = z;
                            end++;
                        }
                    }else{
                        start = i + 1;
                        end = i + 1;
                    }
                }
            }
            
            
            if(max < end - start){
                max = end -start;
            }
        }
        return max;
    }
	
	public static int solution2(int[] A, int K) {
        int len = A.length;
        int[][]cache = new int[len + 1][K + 2];
        int max = 0;
        
        for(int i = 0; i < len; i ++){
            for(int j = 0; j <= K; j++){
                if(A[i] == 1){
                    cache[i + 1][j + 1] = cache[i][j + 1] + 1;
                }else{
                	if(j != 0) {
                		cache[i + 1][j + 1] = cache[i][j] + 1;
                	}
                }
                
                if(cache[i + 1][j + 1] > max)max = cache[i + 1][j + 1];
            }
        }
        return max;
    }

}
