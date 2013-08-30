package test.strangeforest.hibernate;

import java.io.*;

public abstract class ITUtil {

	public static void deleteFile(String name) {
		File file = new File(name);
		file.delete();
		file.deleteOnExit();
	}

	public static void deleteFiles(String dir, final String pattern) {
		File dirFile = new File(dir);
		if (!dirFile.exists())
			return;
		for (File file : dirFile.listFiles(new FileFilter() {
			@Override public boolean accept(File file) {
				return file.getPath().matches(".*" + pattern);
			}
		})) {
			file.delete();
			file.deleteOnExit();
		}
	}
}