package file;

import java.util.HashMap;
import java.util.Map;

public class FileManager implements Runnable{
	
	private Map<String, FileWrapper> filePool = new HashMap<>();

	@Override
	public void run() {
		for(FileWrapper wrapper : filePool.values()) {
			if(wrapper.hasChanged(true)) {
				wrapper.callback();
			}
		}
	}
}
