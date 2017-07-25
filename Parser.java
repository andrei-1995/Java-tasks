package streams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

	private BufferedReader reader = null;

	Parser(String name) {
		try {
			reader = new BufferedReader(new FileReader(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getValue(String key) {
		String currentLine;
		try {
			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.indexOf(key) != -1) {
					int indexOfEq = currentLine.indexOf("=");
					String valueWithSpaces = currentLine.substring(indexOfEq + 1);
					return valueWithSpaces.replaceAll("\\s+", "");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void finalize() {
		try {
			if (reader != null)
				reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Parser parser = new Parser("example");
		System.out.println(parser.getValue("abc"));
	}

}
