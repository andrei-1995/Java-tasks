package streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class LsCatTailWc {

	public static void ls() {
		Path path = Paths.get("");
		String s = path.toAbsolutePath().toString();
		System.out.println(s);
		File[] files = new File(s).listFiles();
		showFiles(files);
	}

	private static void showFiles(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				showFiles(file.listFiles());
			} else {

				System.out.print("File: " + file.getName() + "  ");

				GroupPrincipal group;
				try {
					group = Files.readAttributes(file.toPath(), PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS)
							.group();
					System.out.print("Group: " + group.toString() + "  ");
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.print("Access: ");
				if (file.canRead())
					System.out.print("Readable ");
				if (file.canWrite())
					System.out.print("Writeable ");
				if (file.canExecute())
					System.out.print("Executable ");

				System.out.print("Size in bytes: " + file.length() + "   ");

				BasicFileAttributes attr;
				try {
					attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
					System.out.println("Date and time: " + attr.creationTime());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void cat(String name) {
		try (BufferedReader reader = new BufferedReader(new FileReader(name))) {

			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				System.out.println(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void tail(String name) {
		List<String> lines = new ArrayList<String>();

		try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		int size = lines.size();
		for (int i = size - 1; i >= (size - 10) && i >= 0; i--) {
			System.out.println(lines.get(i));
		}
	}

	public static void tail(String name, String opts) {
		if(opts.startsWith("-l") == false) return;
		int numberOfLines = Integer.parseInt(opts.substring(opts.indexOf('l')+1));
		List<String> lines = new ArrayList<String>();

		try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int size = lines.size();
		for (int i = size - 1; i >= (size - numberOfLines) && i >= 0; i--) {
			System.out.println(lines.get(i));
		}
	}
	
	public static void wc(String name){		
		try (Scanner sc = new Scanner(new FileReader(name))) {			
		    int lines = 0;
		    int words = 0;
		    int chars = 0;
		    
		    while(sc.hasNextLine())  {
		        lines++;
		        String line = sc.nextLine();
		        chars += line.length();
		        words += new StringTokenizer(line, " \n\t").countTokens();
		    }
		    
		    System.out.println("Number of lines: " + lines);
		    System.out.println("Number of words: " + words);
		    System.out.println("Number of characters: " + chars);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		wc("example");
	}
}
