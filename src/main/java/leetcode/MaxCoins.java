package leetcode;

/**
 * 
 * 有 n 个气球，编号为0 到 n-1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
 * 现在要求你戳破所有的气球。每当你戳破一个气球 i 时，你可以获得 nums[left] * nums[i] * nums[right] 个硬币。
 * 这里的 left 和 right 代表和 i 相邻的两个气球的序号。注意当你戳破了气球 i 后，气球 left 和气球 right 就变成了相邻的气球。求所能获得硬币的最大数量。
 * 说明:你可以假设 nums[-1] = nums[n] = 1，但注意它们不是真实存在的所以并不能被戳破。 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
 * 示例:
 * 输入: [3,1,5,8]
 * 输出: 167 
 * 解释: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 *
 */
public class MaxCoins {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {3,1,5,8};
		System.out.println(solution2(nums));
	}
	
	public static int solution1(int[] nums) {
		int len = nums.length;
		int[][] cache = new int[len + 2][len + 2];
		cache[0][0] = 1;
		cache[len + 1][len + 1] = 1;
		
		for(int i = 1; i <= len; i++) {
			int mid = nums[i - 1];
			if(i > 1) {
				mid = mid * nums[i - 2];
			}
			if(i < len - 1) {
				mid = mid * nums[i + 1];
			}
			cache[i][i] = mid;
		}
		
		for(int i = 1; i <= len; i++) {
			for(int j = i; j <= len; j++) {
				for(int k = i; k <= j; k++) {
					int left = cache[i][k - 1];
					int right =  cache[k + 1][j];
					int mid = nums[k - 1];
					if(i > 1) {
						mid = mid * nums[i - 2];
					}
					if(j < len) {
						mid = mid * nums[j];
					}
					cache[i][j] = Math.max(cache[i][j], left + mid + right);
				}
			}
		}
		return cache[1][len];
	}
	
	public static int solution2(int[] nums) {
		 int n = nums.length;
	        int[][] dp = new int[n+2][n+2];
	        int[] nums2 = new int[n+2];
	        nums2[0]=1;
	        nums2[n+1]=1;
	        for(int i=1;i<n+1;i++){
	            nums2[i] = nums[i-1];
	        }
	        //dp[i][j] = max(dp[i][k-1]+dp[k+1][j]+nums2[i-1]*nums2[j+1]*nums[k])
	        for(int i=1;i<n+1;i++){
	            dp[i][i] = nums2[i-1]*nums2[i]*nums2[i+1];
	        }
	        for(int j=1;j<n;j++){
	            for(int i=1;i<n+1-j;i++){
	                int max;
	                for(int k=i;k<=i+j;k++){
	                    int left = (k-1>=i)?dp[i][k-1]:0;
	                    int right = (k+1<=i+j)?dp[k+1][i+j]:0;
	                    dp[i][i+j] = Math.max(left+right+nums2[i-1]*nums2[j+i+1]*nums2[k],dp[i][i+j]);
	                }
	                
	            }
	            
	        }
	        return dp[1][n];
	}
}
