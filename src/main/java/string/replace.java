package string;

public class replace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String a = "1|2";
//		a = a.replaceAll("\\|", "");
		
		String a = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		a = a.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		System.out.println(a);
	}

}
