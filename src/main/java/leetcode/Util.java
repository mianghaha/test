package leetcode;

import java.util.LinkedList;
import java.util.List;

public class Util {

	public static ListNode generateListNode(int[] nums) {
		
		ListNode head = new ListNode(nums[0]);
		ListNode tail = head;
		for(int i = 1; i < nums.length; i++) {
			ListNode node = new ListNode(nums[i]);
			tail.next = node;
			tail = node;
		}
		return head;
	}
	
	public static List<Integer> transformListNode(ListNode node) {
		List<Integer> list = new LinkedList<>();
		while(node != null) {
			list.add(node.val);
			node = node.next;
		}
		return list;
	}
}
