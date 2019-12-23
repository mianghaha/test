package string;

public class StringBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("1,");
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
	}

}
