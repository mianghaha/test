package sort;

public class MinimumHeap {

	public static void main(String[] args) {
		
	}
	
	public static void sort(int[] nums) {
		int len = nums.length;
		int last = len / 2 - 1;
		
		//第一次建立最大堆
		for(int i = last - 1; i >= 0; i--) {
			int parent = i;
			while(parent != -1) {
				parent = compareAndSwap(nums, parent, len);
			}
		}
		//已经建立完最小堆，将最小的放到最后
		swap(nums, 0, --len);
		
		//从根结点开始重新建立最小堆
		for(int i = len - 1; i > 0; i++) {
			int parent = 0;
			while(parent != -1) {
				parent = compareAndSwap(nums, parent, len);
			}
		}
	}
	
	public static int compareAndSwap(int[] nums, int parent, int len) {
		int left = 2 * parent + 1;
		int right = left + 1;
		if(left > len -1 || right > len - 1)return -1;
		
		if(right > len - 1) {
			if(nums[left] < nums[parent]) {
				swap(nums, left, parent);
				return -1;
			}
		}else {
			if(nums[parent] > nums[left] && nums[right] > nums[left]) {
				swap(nums, parent, left);
				return left;
			}else if(nums[parent] > nums[right] && nums[left] > nums[right]) {
				swap(nums, parent, right);
				return right;
			}
		}
		return -1;
	}
	
	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
}
