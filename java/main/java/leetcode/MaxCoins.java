package leetcode;

/**
 * 
 * �� n �����򣬱��Ϊ0 �� n-1��ÿ�������϶�����һ�����֣���Щ���ִ������� nums �С�
 * ����Ҫ����������е�����ÿ�������һ������ i ʱ������Ի�� nums[left] * nums[i] * nums[right] ��Ӳ�ҡ�
 * ����� left �� right ����� i ���ڵ������������š�ע�⵱����������� i ������ left ������ right �ͱ�������ڵ����������ܻ��Ӳ�ҵ����������
 * ˵��:����Լ��� nums[-1] = nums[n] = 1����ע�����ǲ�����ʵ���ڵ����Բ����ܱ����ơ� 0 �� n �� 500, 0 �� nums[i] �� 100
 * ʾ��:
 * ����: [3,1,5,8]
 * ���: 167 
 * ����: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 *
 */
public class MaxCoins {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = {3,1,5,8};
		System.out.println(solution1(nums));
	}
	
	public static int solution1(int[] nums) {
		int len = nums.length;
		int[][] cache = new int[len + 2][len + 2];
		
		for(int i = 1; i <= len; i++) {
			int mid = nums[i - 1];
			if(i > 1) {
				mid = mid * nums[i - 2];
			}
			if(i < len) {
				mid = mid * nums[i];
			}
			cache[i][i] = mid;
		}
		
		for(int i = 2; i <= len; i++) {
			for(int j = 1; j <= len - i + 1; j++) {
				for(int k = j; k <= i + j - 1; k++) {
					int left = cache[j][k - 1];
					int right =  cache[k + 1][i + j - 1];
					int mid = nums[k - 1];
					if(j > 1) {
						mid = mid * nums[j - 2];
					}
					if(j + i - 1< len) {
						mid = mid * nums[j + i - 1];
					}
					cache[j][j + i - 1] = Math.max(cache[j][j + i - 1], left + mid + right);
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
