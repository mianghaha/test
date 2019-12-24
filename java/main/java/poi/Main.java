package poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File file = new File("C://Users/miang/workspace/TEST/src/poi/poi-test.xls");
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileOutputStream os = new FileOutputStream(file);
			
			String[] rows = {"rows1","rows2"};
			List<List<String>> colsList = new ArrayList<>();
			colsList.add(Arrays.asList(new String[]{"test-col-1","test-col-2"}));
			colsList.add(Arrays.asList(new String[]{"test-col-3","test-col-4"}));
			PoiUtil.generateExcel(rows, colsList, os);
			os.flush();
			os.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
