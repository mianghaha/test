package binoperate;

public class Main {
	
    static final int MAXIMUM_CAPACITY = 1 << 30;

	public static void main(String[] args) {
		int n = 5;
		// TODO Auto-generated method stub
		System.out.println(tableSizeFor(n));
		System.out.println(n >>> 1);
		
	}

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
