package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

public class CopyTest {

	public static void main(String[] args) throws Exception{
		File source = new File("C:\\Users\\miang\\Desktop\\cspagent.jar");
		FileInputStream fi = new FileInputStream(source);
		
		String des = "C:\\Users\\miang\\Desktop\\cspagent1.jar";
		File file = new File(des);
		if(!file.exists()){
			file.createNewFile();
		}
		RandomAccessFile raf = new RandomAccessFile(des, "rw");
		byte[] bytearray = new byte[512];
		int needsize = 0;
		needsize = fi.read(bytearray, 0, 512);
		while(needsize > 0){
			raf.write(bytearray, 0, needsize);
			needsize = fi.read(bytearray, 0, 512);
		}
		raf.close();
		fi.close();
		
		while(true){
			Thread.sleep(10000);
		}
	}

}
