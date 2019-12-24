package list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sync {

	public static void main(String args) {
		List a = Collections.synchronizedList(new ArrayList<Integer>());
	}
}
