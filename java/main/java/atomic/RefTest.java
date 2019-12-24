package atomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RefTest {

	public static void main(String[] args) {
		List<String> list = new ArrayList();
		list.add("2");
		list.add("initial value referenced");

		AtomicReference<List<String>> atomicStringReference = new AtomicReference<List<String>>(list);
		boolean exchanged = atomicStringReference.compareAndSet(list, list);
		System.out.println("exchanged: " + exchanged + ",list=" + atomicStringReference.get());

		list.add("new value referenced");
		exchanged = atomicStringReference.compareAndSet(list, list);
		System.out.println("exchanged: " + exchanged + ",list=" + atomicStringReference.get());
	}
}
