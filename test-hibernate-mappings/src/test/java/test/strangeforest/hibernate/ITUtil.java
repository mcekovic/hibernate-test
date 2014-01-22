package test.strangeforest.hibernate;

import java.io.*;

public abstract class ITUtil {

	public static void deleteFiles(String dir, final String pattern) {
		File dirFile = new File(dir);
		if (!dirFile.exists())
			return;
		for (File file : dirFile.listFiles(new FileFilter() {
			@Override public boolean accept(File file) {
				return file.getPath().matches(".*" + pattern);
			}
		})) {
			if (file.delete())
				System.out.println("DB file deleted: " + file.getAbsolutePath());
			else {
				file.deleteOnExit();
				System.out.println("DB file cannot be deleted now, will be deleted on exit: " + file.getAbsolutePath());
			}
		}
	}
}