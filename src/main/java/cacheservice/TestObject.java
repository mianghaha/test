package cacheservice;

public class TestObject {
	
	public TestObject() {
		
	}

	public TestObject(int a, int b, String c) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	private int a;
	private int b;
	private String c;
	
	public int getA() {
		return a;
	}
	public int getB() {
		return b;
	}
	public String getC() {
		return c;
	}
	public void setA(int a) {
		this.a = a;
	}
	public void setB(int b) {
		this.b = b;
	}
	public void setC(String c) {
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof TestObject)) {
			return false;
		}
		
		TestObject t = (TestObject)o;
		if(this.a != t.a || this.b != t.b || (this.c == null && t.c != null) || (this.c != null && t.c == null) || !this.c.equals(t.c)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Test [a=");
		builder.append(a);
		builder.append(", b=");
		builder.append(b);
		builder.append(", c=");
		builder.append(c);
		builder.append("]");
		return builder.toString();
	}
}
