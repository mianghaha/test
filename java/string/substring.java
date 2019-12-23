package string;

public class substring {
	
	public static void main(String[] args){
		String a = "123,";
		a = a.substring(0,a.length()-1);
		System.out.println(a);
		
		
		String b = "http://zlyjtest-1253664568.cossh.myqcloud/testvideo.mp4.f20.mp4";
		b =  b.substring(b.length() - 4, b.length());
		System.out.println(b);
		
		String portraitName = "b.jpg";
		int index = portraitName.indexOf(".");
//		portraitName = portraitName.substring(0, portraitName.lastIndexOf("."));
//		portraitName = portraitName.replaceAll("\\.","");
		portraitName = portraitName.substring(0,index);
		System.out.println(index + portraitName);
		
		
	}

}
