package streams_huffman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {

	private class Node {
		private char ch;
		private int freq;
		private Node left, right;

		public void setCh(char ch) {
			this.ch = ch;
		}

		public void setFreq(int freq) {
			this.freq = freq;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		Node() {
		}

		Node(char ch, int freq) {
			this.ch = ch;
			this.freq = freq;
		}
	}

	private class FreqComparator implements Comparator<Node> {
		public int compare(Node a, Node b) {
			int freqA = a.freq;
			int freqB = b.freq;
			return freqA - freqB;
		}
	}

	private PriorityQueue<Node> prQueue;
	private HashMap<Character, String> charToCode;
	private HashMap<String, Character> codeToChar;

	public static void main(String[] args) {
		Huffman hfm = new Huffman();
		hfm.compressByHuffman("example");
	}

	public void compressByHuffman(String textFile) {

		String text = fileToString(textFile);
		HashMap<Character, Integer> dict = makeDict(text);

		prQueue = new PriorityQueue<>(1000, new FreqComparator());

		int n = 0;
		for (Character c : dict.keySet()) {
			prQueue.add(new Node(c, dict.get(c)));
			n++;
		}

		Node root = huffmann(n);

		charToCode = new HashMap<Character, String>();
		codeToChar = new HashMap<String, Character>();
		encodeDecodeMaps(root, new String());

		String compressed = compress(text);
		writeToFile(compressed, "compressed.txt");

		String decompressed = decompress(compressed);
		writeToFile(decompressed, "decompressed.txt");

		if (text.equals(decompressed)) {
			System.out.println("The result from decoding as absolutely the same as the original input");
		}
	}

	private String fileToString(String textFile) {

		StringBuilder text = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {

			String line;
			while ((line = br.readLine()) != null) {
				text.append(line + '\n');
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return text.toString();
	}

	private HashMap<Character, Integer> makeDict(String text) {

		HashMap<Character, Integer> dict = new HashMap<>();

		for (int i = 0; i < text.length(); i++) {
			if (dict.containsKey(text.charAt(i))) {
				dict.put(text.charAt(i), dict.get(text.charAt(i)) + 1);
			} else {
				dict.put(text.charAt(i), 1);
			}
		}
		return dict;
	}

	private Node huffmann(int n) {
		Node x, y;
		for (int i = 1; i <= n - 1; i++) {
			Node z = new Node();
			z.left = x = prQueue.poll();
			z.right = y = prQueue.poll();
			z.freq = x.freq + y.freq;
			prQueue.add(z);
		}
		return prQueue.poll();
	}	

	private void encodeDecodeMaps(Node n, String s) {
		if (n == null)
			return;
		encodeDecodeMaps(n.left, s + "0");
		encodeDecodeMaps(n.right, s + "1");

		// Visit only nodes with chars
		if (n.ch != '\u0000') {
			charToCode.put(n.ch, s);
			codeToChar.put(s, n.ch);
		}

	}

	private String compress(String s) {
		StringBuilder c = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
			c.append(charToCode.get(s.charAt(i)));
		return c.toString();
	}

	private String decompress(String s) {
		StringBuilder temp = new StringBuilder();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			temp.append(s.charAt(i));
			Character c = codeToChar.get(temp.toString());
			if (c != null) {
				result.append(c);
				temp = new StringBuilder();
			}
		}
		return result.toString();
	}

	private void writeToFile(String s, String fileName) {
		File f = new File(fileName);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			bw.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
