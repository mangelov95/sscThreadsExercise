import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.DownloadObject;
import utils.DownloadRunnable;
import utils.Parser;
import utils.Status;

/**
 * A class with a main method which runs my program and downloads files from a specified webpage.
 * @author martin
 *
 */
public class NoGUI {
	public static void main(String[] args) {
		String folderPath = "C:\\Users\\martin\\Desktop\\imgs\\";
		folderPath.replace("\\", "/");
		String folderInput;
		List<String> fileTypes = new ArrayList<String>();
		String link;
		String fileType = "";
		int numberOfThreads;
		Scanner input = new Scanner(System.in);
		System.out.println("Please input a link: ");
		link = input.nextLine();
		while (link.equals("")) {
			System.out.println("Please input a valid link: ");
			link = input.nextLine();
		}
		System.out.println("Please input a folder: ");
		folderInput = input.nextLine();
		while (folderInput.equals("")) {
			System.out.println("Please input a valid folder: ");
			folderInput = input.nextLine();
			if (folderInput.charAt(folderInput.length() - 1) != '/') {
				folderInput = folderInput + "/";
			}
		}
		Path path = Paths.get(folderInput);
		if (!Files.exists(path)) {
			System.out.println("Folder doesn't exist. Please input a new folder: ");
			folderInput = input.nextLine();
			if (folderInput.charAt(folderInput.length() - 1) != '/') {
				folderInput = folderInput + "/";
			}
		}
		
		while (!fileType.contains("end")) {
			System.out.println("Please write a file type and press Enter or type 'end' to continue");
			fileType = input.nextLine();
			if (!fileType.startsWith(".")) {
				fileType = "." + fileType;
			}
			fileTypes.add(fileType);
		}
		Parser p = new Parser(link, fileTypes);
		List<String> fileLinks = p.generateLinks();
		System.out.println("Please input the number of threads: ");
		numberOfThreads = input.nextInt();
		while (numberOfThreads > fileLinks.size() || numberOfThreads == 0) { // If the inputted number
														// of threads is bigger
														// than the number of
														// files or the number of threads is 0
			System.out.println("These are too many threads for the number of files or the number of threads is 0, please input another number: ");
			numberOfThreads = input.nextInt();
		}
		List<DownloadObject> objs = new ArrayList<DownloadObject>();
		for (String fileLink : fileLinks) {
			DownloadObject obj = new DownloadObject(folderInput, fileLink);
			objs.add(obj);
		}
				
		ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
		DownloadRunnable runnable = null;
		for (DownloadObject obj : objs) {
			System.out.println("File " + obj.getFileName() + " is being added to the queue.");
			runnable = new DownloadRunnable(obj);
			pool.execute(runnable);
		}
		pool.shutdown();

	}
}
