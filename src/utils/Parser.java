package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A class which gets a webpage link and a list of file types and generates links to the files with the desired
 * file types in that webpage.
 * @author martin
 */
public class Parser {
	private String webpage;
	private List<String> fileTypes;
	
	/**
	 * A constructor which takes the webpage and an array of file types. If the array is empty, the default file type is ".jpg".
	 * @param webpage A String with the webpage link
	 * @param fileTypes An array of Strings with the desired filetypes.
	 */
	public Parser(String webpage, List<String> fileTypes) {
		this.webpage = webpage;
		System.out.println("Filetypes size: " + fileTypes.size());
		if (fileTypes.size() == 1) {
			this.fileTypes = new ArrayList<String>();
			this.fileTypes.add(".jpg");
		}
		else {
			this.fileTypes = fileTypes;
		}
	}
	
	/**
	 * A constructor which takes the webpage link. The file type is set to ".jpg" (the default one).
	 * @param webpage A String with the webpage link.
	 */
	public Parser(String webpage) {
		this.webpage = webpage;
		fileTypes = new ArrayList<String>();
		fileTypes.add(".jpg");
	}
	
	/**
	 * Generate all links with files with the specified types from the page.
	 * @return A list with strings (links to files).
	 */
	public List<String> generateLinks() {
		Document doc;
		List<String> links = new ArrayList<String>();
		try {
			doc = Jsoup.connect(webpage).get();
			if (fileTypes.contains(".jpg") || fileTypes.contains(".png") || fileTypes.contains(".gif") || fileTypes.contains(".jpeg")) {
				Elements elem = doc.getElementsByTag("img");
				//System.out.println("Blah");
				for (Element l : elem) {
					String imglink = l.attr("src");
					//System.out.println(l.absUrl("src"));
					for (String type : fileTypes) {
						if (imglink.toUpperCase().contains(type.toUpperCase())) {
							links.add(l.absUrl("src"));
						}
					}
				}
			}
			
			Elements elem = doc.getElementsByTag("a");
			for (Element l : elem) {
				String attr = l.attr("href");
				for (String type : fileTypes) {
					if (attr.toUpperCase().contains(type.toUpperCase())) {
						links.add(l.absUrl("href"));
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Couldn't generate links.");
			e.printStackTrace();
		}
		
		return links;
	}
}