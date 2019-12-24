package leetcode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;

public class MergeKLists {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		int[] nums1 = {-8,-7,-6,-3,-2,-2,0,3};
		ListNode n1 = Util.generateListNode(nums1);
		int[] nums2 = {-10,-6,-4,-4,-4,-2,-1,4};
		ListNode n2 = Util.generateListNode(nums2);
		int[] nums3 = {-10,-9,-8,-8,-6};
		ListNode n3 = Util.generateListNode(nums2);
		int[] nums4 = {-10,0,4};
		ListNode n4 = Util.generateListNode(nums2);
		
		ListNode[] lists = {n1,n2,n3,n4};
		ListNode res = solution2(lists);
		List<Integer> listRes = Util.transformListNode(res);
		System.out.println(JsonUtil.TransToJson(listRes));
		
		Map<String, Integer> map = new HashMap<>();
		map.getOrDefault("key", 1);
	}
	
	public static ListNode solution1(ListNode[] lists) {
	    if(lists == null || lists.length == 0)return null;
	    ListNode head = null;
	    for(int i = 0; i < lists.length; i++){
	        head = mergeTwoList(head, lists[i]);
	    }
	    return head;
	}
	    
	public static ListNode mergeTwoList(ListNode node1, ListNode node2){
        if(node1 == null)return node2;
        if(node2 == null)return node1;
        
        ListNode head = null;
        
        if(node1.val > node2.val){
            head = node2;
            node2 = node2.next;
        }else{
            head = node1;
            node1 = node1.next;
        }
        ListNode tail = head;
        
        while(node1 != null && node2 != null){
            if(node1.val > node2.val){
                tail.next = node2;
                node2 = node2.next;
            }else{
                tail.next = node1;
                node1 = node1.next;
            }
            tail = tail.next;
        }
        
        if(node1 != null){
            tail.next = node1;
        }else{
            tail.next = node2;
        }
        return head;
	}

	//堆排序方式
	public static ListNode solution2(ListNode[] lists) {
	    if(lists == null || lists.length == 0)return null;
	    
	    int len = lists.length;
	    //保证前k个元素非null
	    for(int i = 0; i < len; i++) {
	    	if(lists[i] == null) {
	    		swap(lists, i--, --len);
	    	}
	    }
	    
	    firstCreateMinimalHeap(lists, len);
	    ListNode head = lists[0];
	    ListNode tail = lists[0];
	    lists[0] = tail.next;
	    if(tail.next == null) {
	    	swap(lists, 0, --len);
	    }
	    
	    
	    while(len > 1) {
    		if(lists[0] == null) {
    			swap(lists, 0, --len);
    		}else {
    			reCreateMinimalHeap(lists, len);
    			tail.next = lists[0];
    			tail = lists[0];
    			lists[0] = lists[0].next;
    		}
	    }
	    
	    if(lists[0] != null) {
	    	tail.next = lists[0];
	    }
	    return head;
	}
	
	public static void firstCreateMinimalHeap(ListNode[] lists, int len) {
		int last = len / 2 - 1;
		
		for(int i = last; i >= 0; i--) {
			int parent = i;
			while(parent != -1) {
				parent = compareAndSwap(lists, parent, len);
			}
		}		
	}
	
	public static void reCreateMinimalHeap(ListNode[] lists, int len) {
		int parent = 0;
		while(parent != -1) {
			parent = compareAndSwap(lists, parent, len);
		}
	}
	
	public static int compareAndSwap(ListNode[] lists, int parent, int len) {
		int last = len / 2 - 1;
		if(parent > last)return -1;
		
		int left = parent * 2 + 1;
		int right = left + 1;
		//无右子树
		if(right > len - 1) {
			if(lists[left].val < lists[parent].val) {
				swap(lists, left, parent);
				return left;
			}
		}else {
			if(lists[left].val < lists[parent].val && lists[left].val < lists[right].val) {
				swap(lists, left, parent);
				return left;
			}else if(lists[right].val < lists[parent].val) {
				swap(lists, right, parent);
				return right;
			}
		}
		return -1;
	}
	
	public static void swap(ListNode[] lists, int i, int j) {
		ListNode tmp = lists[i];
		lists[i] = lists[j];
		lists[j] = tmp;
	}
}
