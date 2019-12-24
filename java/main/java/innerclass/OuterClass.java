package innerclass;

public class OuterClass {
	
	private int i = 1;
	
	public class InnerClass{
		private int k = 2;
		
		public void test() {
			OuterClass outerClass = OuterClass.this;
			System.out.println(outerClass.getClass().getName() + ",hashcode=" + outerClass.hashCode() + ",i=" + i + ",k=" + k);
		}
	}

}
