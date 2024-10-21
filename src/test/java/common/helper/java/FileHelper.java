package common.helper.java;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A Collection of helper methods for files.
 * </p>
 */
public class FileHelper {

	/**
	 * Check file exists
	 * @param filePath - location of file
	 */
	public static boolean exists(String filePath) {
		if (filePath.startsWith("classpath:"))
			filePath = filePath.replace("classpath:", System.getProperty("user.dir") + "/src/test/java");
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * Get all file names from a folder with specified extension
	 * @param folderPath - location of folder
	 * @param extension - file extension
	 */
	public static List<String> getAllFileNames(String folderPath, String extension, boolean removeExtension) {
		File folder = new File(folderPath);
		List<String> fileNames = new ArrayList<>();

		if (folder.exists()) {
			for (var file : folder.listFiles()) {
				if (file.getName().endsWith("." + extension)) {
					if (removeExtension) fileNames.add(FilenameUtils.removeExtension(file.getName()));
					else fileNames.add(file.getName());
				}
			}
		}

		return fileNames;
	}

	/**
	 * Get all folder names from a folder
	 * @param folderPath - location of parent folder
	 */
	public static List<String> getAllFolderNames(String folderPath) {
		File folder = new File(folderPath);
		List<String> folderNames = new ArrayList<>();

		if (folder.exists()) {
			for (var file : folder.listFiles()) {
				if (file.isDirectory()) {
					folderNames.add(file.getName());
				}
			}
		}

		return folderNames;
	}
}