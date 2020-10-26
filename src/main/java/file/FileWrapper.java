package file;

import java.io.File;

public abstract class FileWrapper {

	private long lastModifyTime;
	String filePath;
	
	abstract void callback();
	
	public boolean hasChanged(boolean isUpdateLastModifyTime) {
		File file = new File(filePath);
		boolean hasChanged = file.lastModified() == lastModifyTime;
		if(isUpdateLastModifyTime && hasChanged) {
			updateLastModifyTime(file);
		}
		return hasChanged;
	}
	
	public void updateLastModifyTime(File file) {
		lastModifyTime = file.lastModified();
	}
}
