package streams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class CopyFile {

	public static void copyFile(String name) {		
		File file = new File(name + "_copy");

		try (BufferedReader reader = new BufferedReader(new FileReader(name));
				BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

			String sCurrentLine;
			while ((sCurrentLine = reader.readLine()) != null) {
				writer.write(sCurrentLine + '\n');
			}

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void main(String[] args) throws IOException {
		copyFile("example");
	}
}

