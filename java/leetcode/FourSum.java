package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import utils.JsonUtil;


/**
 * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d 
 * 使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
 * 注意：
 * 答案中不可以包含重复的四元组。
 * 示例：
 * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
 * 满足要求的四元组集合为：
 * [[-1,  0, 0, 1],[-2, -1, 1, 2],[-2,  0, 0, 2]]
 * @author miang
 *
 */
public class FourSum {

	public static void main(String[] args) throws Exception{
//		int nums[] = {1,0,-1,0,-2,2};
//		int nums[] = {0,0,0,0};
//		int nums[] = {-1,0,1,2,-1,-4};
//		int target = -1;
		int nums[] = {-1,-5,-5,-3,2,5,0,4};
		int target = -7;
		System.out.println(JsonUtil.TransToJson(solution1(nums, target)));
	}
	
	public static List<List<Integer>> solution1(int[] nums, int target) {
		if(nums == null || nums.length <= 3)return new ArrayList<>();
		
		int len = nums.length;
		Arrays.sort(nums);
		List<List<Integer>> res = new LinkedList<>();
		Integer cache1 = null;
		Integer cache2 = null;
		
		for(int i = 0; i < len - 2; i++) {
			if(i - 1 >= 0 && nums[i] == nums[i - 1])continue;
            if (i < len - 3 && nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                 break;
            }
            if (i < len - 3 && nums[i] + nums[len - 1] + nums[len - 2] + nums[len - 3] < target) {
                continue;
            }
			for(int j = i + 1; j < len - 2; j++) {
				if(cache1 != null && j - 1 > i && nums[j] == nums[j - 1])continue;
				int x = j + 1;
				int y = len - 1;
				cache1 = null;
 				cache2 = null;
				while(x < y) {
					if(cache1 != null && nums[x] == cache1) {
						x++;
						continue;
					}
					
					if(cache2 != null &&  nums[y] == cache2) {
						y--;
						continue;
					}
					
					int sum = nums[i] + nums[j] + nums[x] + nums[y];
					if(sum == target) {
						List<Integer> list = new LinkedList<>();
						list.add(nums[i]);
						list.add(nums[j]);
						list.add(nums[x]);
						list.add(nums[y]);
						res.add(list);
						cache1 = nums[x];
						cache2 = nums[y];
						x++;
						y--;
					}else if(sum < target) {
						x++;
					}else {
						y--;
					}
				}
			}
		}
		return res;
	}
}
