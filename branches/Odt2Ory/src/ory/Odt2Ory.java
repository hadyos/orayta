package ory;

import java.io.File;
import java.io.FileNotFoundException;

/*
 * Odt2Ory01.java
 *
 * Created on September 12, 2007, 4:24 PM
 *
 * this program is meant to convert text from an .odt file
 * to a text format that is used by Oryata.
 * 
 *  it is based on a program i found here:
 *  http://blogs.oracle.com/prasanna/entry/openoffice_parser_extracting_text_from
 *  
 *  IMPORTENT: the program currently handles footnotes vary badly, so dont use it on
 *  files with footnotes.
 *  also it will work only with full linux paths. 
 * 
 */

/* TODO :
 * write output to file. DONE.
 * compile to a single jar. DONE.
 * replace Filename so the program can work with windows. DONE.
 * create a gui. DONE.
 * get license. DONE.
 * loop option for many files.
 * create conf files. DONE.
 * Footnote class. DONE.
 * run tests.
 * upload. DONE.
 * print usage.
 */


/**
 * this program is a helper for 'orayta' project.
 * it is designed to except an open office file (.odt) 
 * and transform to the format orayta understands.
 * @author izar00@gmail.com
 */



public class Odt2Ory {
	Filename inputFilename, oryFilename;
	String fileType;
	

	Odt2Ory()    {
	}
	
	/**
	 * this is where all the work is done.
	 * @param input - an odt file path.
	 * @throws Exception
	 */
	public void process(String input)  throws Exception {


		if (input == null) {
			message("no file selected");
			return;
		}

		inputFilename = new Filename(input); 
		
		//recursively process files.
		if (inputFilename.isDirectory()){
			final File[] childs = inputFilename.listFiles();
			for (File child: childs){
				process(child.getAbsolutePath());
			}
			return;
		}
		
		launchInfo(input);
		
		
		
		
		if (! inputFilename.canRead()) {
			errorMessage("cant read file:\n" + inputFilename + "\nהקובץ לא נמצא" );
			return;
		}
		
		String fileType = inputFilename.getExtension();
		if (! fileType.equals("odt")){
			errorMessage("file type: " + fileType + " not supported\n" +
					"סוג הקובץ: " + fileType + " לא נתמך.");
			return;
		}

		try {
			OryFile oryFile = new OryFile(inputFilename);
			oryFile.save();

			oryFilename = new Filename(oryFile.getFilename());


			//    	System.out.println("Extracted Text:");
			//    	System.out.println(oryFile.getFileText());


			OryConfFile oryConf = new OryConfFile (oryFilename);
			oryConf.setBookName(oryFile.getBookTitle());
			
			if (Main.parameters.getUid().isEmpty()){
				oryConf.setAutoUid();
			}
			else {
				oryConf.setUID(Main.parameters.getUid());
			}

			oryConf.save();

		} catch (FileNotFoundException e) {
			
			errorMessage("File not found" + "\n" + "הקובץ לא נמצא" + "\n", e);
			return;

		} catch (Exception e) {
			errorMessage("an error occured" + "\n" + "אירעה שגיאה" + "\n", e);
			return;
		}

		success();

	}

	

	void message (String str){
		System.out.println();
		System.out.println(str);
	}

	void errorMessage (String str, Exception e){
		System.err.println(str);
		System.err.println(e);
	}
	
	void errorMessage (String str) {
		System.err.println(str);
	}

	void success() {
		message("oporation completed successfully" + "\n" + "הפעולה הסתיימה בהצלחה" + "\n" +
				"input: " + inputFilename.getPath() + "\n" + "output: " + oryFilename.getPath() + "\n");

	}

	void launchInfo(String inputFilename){
		log("runing odt2ory ver 0.2");
		log("processing file: " + inputFilename );
	}

	void log(String string) {
		System.out.println(string);
	}

}