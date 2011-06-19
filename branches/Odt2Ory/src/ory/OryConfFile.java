package ory;

import java.io.IOException;
//import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * class for creating a .conf (configuration) file
 * for an orayta book.
 * @author izar00@gmail.com
 *
 */
public class OryConfFile {
	private Filename filename;
	private StringBuffer text;
	private final int UID_MIN = 0;
	private final int UID_MAX = 40000;
	private static int uid ;
//	private boolean publicFile; //if this is a public file we want to use a uid of our own range.
	
	/**
	 * 
	 * @param file - the Filename object for the ory file for which this conf is meant.
	 */
	OryConfFile (Filename file) {
		
		String path = file.getFullPath();
		String baseName = file.getBaseName();
		String extension = "conf";
		
		filename = new Filename (path, baseName, extension);
		
		text = new StringBuffer();
		
//		setAutoUid();
	}
	
	/**
	 * creates a unique id entry
	 * with a number generated from the path
	 * of this conf file.
	 * NOTICE: you may get a negative uid.
	 */
	public void setAutoUid() {
		String str = (filename.getAbsolutePath()); // using date to decrease probability of books with the same uid.
		int num = str.hashCode();
		
		//make sure our number isn't in the range that is in use.
		if (num > UID_MIN && num < UID_MAX)
			num += UID_MAX;
		
		setUID(num);
	}

	void addEntry (String label, String value){
		String str = new String (label + "=" + value + "\n");
		text.append(str);
	}
	
	void addEntry (String label, int num){
		String value = Integer.toString(num);
		addEntry(label, value);
		
	}
	
	public void setBookName(String bookName){
		addEntry("DisplayName", bookName);
	}
	
	public void setBookSource(String source){
		addEntry("TextSource", source);
	}
	
	public void setUID(String uid){
		setUID( Integer.parseInt(uid));
		
	}

	public void setUID(int num) {
		
		if(OryConfFile.uid == 0)
			OryConfFile.uid = num;
		else {
			num = OryConfFile.uid;
		}
		OryConfFile.uid++; //increase uid by 1 for every input file.
		
		if (num >= UID_MIN && num <= UID_MAX){
			System.out.println("warning: pussible violation of uid bounderies\n" +
					"using uid: " + num);
		}
			
		addEntry("UniqueId", num);
		
	}

	public Filename getFilename() {
		return filename;
	}

	public void setFilename(Filename filename) {
		this.filename = filename;
	}

	public String getText() {
		return text.toString();
	}

	public void save() throws IOException {
		
//		File file = new File(filename.getFilename());
		String encoding = "utf-8";
//		System.out.println("the text is:");
//		System.out.println(text);
		FileUtils.writeStringToFile(filename, text.toString(), encoding);
		
	}
	

}
