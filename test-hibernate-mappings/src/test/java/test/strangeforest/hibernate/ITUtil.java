package test.strangeforest.hibernate;

import java.io.*;

public interface ITUtil {

	public static void deleteFiles(String dir, final String pattern) {
		File dirFile = new File(dir);
		if (!dirFile.exists())
			return;
		for (File file : dirFile.listFiles(f -> f.getPath().matches(".*" + pattern))) {
			if (file.delete())
				System.out.println("DB file deleted: " + file.getAbsolutePath());
			else {
				file.deleteOnExit();
				System.out.println("DB file cannot be deleted now, will be deleted on exit: " + file.getAbsolutePath());
			}
		}
	}
}