package leetcode;

public class FindMedianSortedArrays {

	public static void main(String[] args) {
		int[] nums1 = {100001};
		int[] nums2 = {100000};
		System.out.println(solution(nums1, nums2));
	}
	
	public static double solution(int[] nums1, int[] nums2) {
		int l1 = nums1.length;
        int l2 = nums2.length;
        int index1 = 0;
        int index2 = 0;
        boolean isJiShu = (l1 + l2) % 2 == 1;
        int m = (l1 + l2)/2;
        int[] cache = new int[m + 1];
        
        for(int i = 0; i <= m; i++) {
        	if(l2 == 0 || index2 >= l2 || (index1  < l1 && nums1[index1] <= nums2[index2])) {
        		cache[i] = nums1[index1];
        		index1++;
        	}else {
        		cache[i] = nums2[index2];
        		index2++;
        	}
        }
        
        if(isJiShu) {
        	return cache[m];
        }else {
        	return ((double)cache[m] + (double)cache[m - 1])/2;
        }
    }
}
