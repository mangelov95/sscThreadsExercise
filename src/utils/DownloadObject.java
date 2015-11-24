package utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

/**
 * A class which takes the link to the file and allows the file to be downloaded to a specified download folder.
 * Some lines of code taken from Shan's example code.
 * @author martin
 *
 */
public class DownloadObject {
	private String targetFolder;
	private String fileLink;
	
	/**
	 * A constructor which takes a link to the file and the folder where this file should be saved.
	 * @param targetFolder A string with the full path to the folder.
	 * @param fileLink A string with the link to the file.
	 */
	public DownloadObject(String targetFolder, String fileLink) {
		this.targetFolder = targetFolder;
		this.fileLink = fileLink;
	}
	
	/**
	 * If we want a default Downloads folder, we can set it in this constructor and only require the link to the file.
	 * @param fileLink A string with the link to the file.
	 */
	public DownloadObject(String fileLink) {
		this.targetFolder = "";
		this.fileLink = fileLink;
	}
	
	/**
	 * Retrieve the link to the file. Good for debuging.
	 * @return
	 */
	public String getFileLink() {
		return fileLink;
	}
	
	public String getFileName() {
		return fileLink.substring(fileLink.lastIndexOf('/')+1, fileLink.length() );
	}

	/**
	 * Method which downloads the specified file to the specified directory.
	 */
	public void download(String threadName) { 
		String fileName = this.getFileName();
		System.out.println("File " + fileName + " started downloading using " + threadName);
		URL source;
		try {
			source = new URL(fileLink);
			File file = new File(targetFolder + fileName);
			FileUtils.copyURLToFile(source, file);
		} catch (IOException e) {
			System.out.println("Couldn't download the file");
			e.printStackTrace();
		}
		
		System.out.println("File " + fileName + " successfuly downloaded using " + threadName);
	}
}
