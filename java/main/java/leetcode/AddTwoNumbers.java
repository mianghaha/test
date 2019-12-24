package leetcode;

public class AddTwoNumbers {

	public static class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}
	
	public static ListNode solution(ListNode l1, ListNode l2) {
        if(l1 == null)return l2;
        if(l2 == null)return l1;
        
        ListNode head = null;
        ListNode tail = null;
        ListNode l1Next = l1;
        ListNode l2Next = l2;
        boolean isAdd = false;
        while(l1Next != null || l2Next != null || isAdd){
            int sum = 0;
            if(isAdd){
                sum++;
                isAdd = false;
            }
            if(l1Next != null){
                sum = sum + l1Next.val;
                l1Next = l1Next.next;
            }
            if(l2Next != null){
                sum = sum + l2Next.val;
                l2Next = l2Next.next;
            }
            if(sum >= 10){
                isAdd = true;
                sum = sum % 10;
            }
            ListNode newNode = new ListNode(sum);
            if(head == null){
                head = newNode;
                tail = newNode;
            }else{
                tail.next = newNode;
                tail = tail.next;
            }
        }
        return head;
    }
	
	public static void main(String[] args) {
		int[] array1 = {2,4,3};
		ListNode l1 = initListNode(array1);
		printListNode(l1);
		int[] array2 = {5,6,4};
		ListNode l2 = initListNode(array2);
		solution(l1, l2);
	}
	
	public static ListNode initListNode(int[] array) {
		ListNode head = new ListNode(array[0]);
		ListNode tail = head;
		for(int i = 1; i < array.length; i++) {
			ListNode newNode = new ListNode(array[i]);
			tail.next = newNode;
			tail = tail.next;
		}
		return head;
	}
	
	public static void printListNode(ListNode node) {
		while(node != null) {
			System.out.print(" -> " + node.val);
			node = node.next;
		}
	}
}
