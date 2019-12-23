package images;

import java.io.File;

public class transTobase64 {

	public static void main(String[] args){
		System.out.println(System.getProperty("user.dir"));
		File file = new File(System.getProperty("user.dir") + "/src/images/1.jpg");
		try {
			String picBase64 = FileUtil.encodeBase64File(file);
			System.out.println(picBase64);
			System.out.println(picBase64.length());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
