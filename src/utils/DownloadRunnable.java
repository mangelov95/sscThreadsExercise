package utils;
import utils.DownloadObject;

/**
 * An implementation of a Runnable object which gets a desired file and downloads it when the run method is executed. 
 * @author martin
 *
 */
public class DownloadRunnable implements Runnable {
	private DownloadObject obj;
	
	/**
	 * Initialise a new DownloadRunnable object.
	 * @param obj A DownloadObject with the file which needs to be downloaded.
	 */
	public DownloadRunnable(DownloadObject obj) {
		this.obj = obj;
	}
	/**
	 * Prints out which thread exactly is working and downloads the object.
	 */
	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		obj.download(threadName);
		System.out.println();
	}
	
}