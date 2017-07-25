package streams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Encoding {
	public static void encoding(String name, String srcEncoding) {
		File src = new File(name);
		File dest = new File("UTF8");
		transform(src, srcEncoding, dest, "UTF-8");
	}
	
	private static void transform(File source, String srcEncoding, File target, String tgtEncoding) {
	    try (
	      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source), srcEncoding));
	      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), tgtEncoding)); ) {
	          char[] buffer = new char[16384];
	          int read;
	          while ((read = br.read(buffer)) != -1)	        	  
	              bw.write(buffer, 0, read);
	    }catch(IOException e){
	    	e.printStackTrace();
	    }
	}
	
	public static void main(String[] args){
		encoding("subs.srt", "iso-8859-1");
	}
}
