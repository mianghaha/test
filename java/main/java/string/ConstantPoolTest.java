package string;

import java.util.ArrayList;
import java.util.List;

public class ConstantPoolTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 0;
		List<String> list = new ArrayList<String>();
		while(true){
			//通过intern方法向常量池中手动添加常量
			list.add(String.valueOf(i++).intern());
		}

	}

}
