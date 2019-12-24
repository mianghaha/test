package math;

public class weiyi {
	
	 private static final int COUNT_BITS = Integer.SIZE - 3;
	
	public static void main(String[] args){	
		int SHUTDOWN   =  0 << COUNT_BITS;
		System.out.println("SHUTDOWN=" + SHUTDOWN);
		String bin1 =  toBinary(-1);
		System.out.println("-1=" + bin1);
//		System.out.println("-1=" + bin1 + ",to10agin=" + Integer.parseInt(bin1, 2));
		int running = -1 << COUNT_BITS;
		System.out.println("RUNNING=" + running + ",bin=" + toBinary(running));
//		System.out.println("RUNNING=" + toBinary(-1 << COUNT_BITS)  + ",to10agin=" + Integer.parseInt(bin2, 2));
		
		System.out.println("atomic=" + ((-1 << COUNT_BITS) | 0));
		int capacity = (1 << COUNT_BITS) - 1;
		System.out.println("capacity=" + capacity + ",bin=" + toBinary(capacity) + ",~c=" + ~capacity);
	}
	
	static String toBinary(int num) {
		String str = "";
		
		int abs = Math.abs(num);
		while (abs != 0) {
			str = abs % 2 + str;
			abs = abs / 2;
		}
		
		//补齐31位
		if(str.length() < 31) {
			int count = 31 - str.length();
			for(int i = 0; i < count; i++){
				str = "0" + str;
			}
		}
		
		//补齐标志位
		str = "0" + str;
		//负数需要取补码
		if(num < 0) {
			char[] sup = new char[32];
			
			//首先取反
			for(int i = 0; i < 32; i++) {
				if(str.charAt(i) == '0') {
					sup[i] = '1';
				}else {
					sup[i] = '0';
				}
			}
			
			//进行+1
			if(sup[31] == '0') {
				sup[31] = '1';
			}else {
				int label = 1;
				for(int i = 31; i >= 0; i--) {
					if(sup[i] == 1 && label == 1) {
						sup[i] = '0';
					}else {
						break;
					}
				}
			}
			
			return new String(sup);
		}else {
			return str;
		}
	}
}
