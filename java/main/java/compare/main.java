package compare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JspActInfo a = new JspActInfo(1,1,"11111");
		JspActInfo b = new JspActInfo(2,3,"22222");
		
		ArrayList<JspActInfo> list = new ArrayList<JspActInfo>();
		list.add(a);
		list.add(b);
		
		Collections.sort(list);
		
		for(JspActInfo j : list){
			System.out.println(j.getUrl());
		}
	}

}
