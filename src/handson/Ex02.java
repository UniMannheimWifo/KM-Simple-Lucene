package handson;
/**
 * Code for exercise 02 of the Knowledge Management lecture 2012
 * (University of Mannheim)
 * 
 * @author Johannes Knopp
 */

//TODO 4.2: import missing datastructure library here
import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Load file and call test. 
 */
public class Ex02 {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String ex02_path = "data/";
		
		File macbethfile = new File(ex02_path + "the_tragedy_of_macbeth.txt");
		HashSet<String> stopwords = read_stopwords(ex02_path + "english"); //TODO ex 4.4: uncomment
		BOW mcb = new BOW(macbethfile, stopwords);
		test(mcb);
	}


	/**
	 * Reads stopwords from file
	 * 
	 * @param filename Name of the file containing one stopword per line
	 * @return HashSet of stopwords.
	 * @throws IOException 
	 */
	public static HashSet<String> read_stopwords(String filename) throws IOException{
		HashSet<String> stopwords = new HashSet<String>();
		FileInputStream fstream = new FileInputStream(new File(filename));
		Scanner scanner = new Scanner(fstream);
		try {
			while (scanner.hasNext()){
				String word = scanner.next().trim(); //current word without whitespace
				stopwords.add(word);
			}
		}
		finally{
			scanner.close();
			fstream.close();
		}

		return stopwords;
	}
	

	private static void test(BOW mcb) {
		String[] search = new String[5];
		search[0] = "macbeth";
		search[1] = "Macbeth";
		search[2] = "MACBETH";
		search[3] = "MACBETH.";
		search[4] = "the";
		
		for (int i=0; i < search.length; i++) {
			System.out.println("'" + search[i] + "': " + mcb.get_count(search[i]));
		}
	}
}


/**
 * 
 * Represent a document as a Bag of Words
 *
 */
class BOW {
	
	private HashSet<String> stopwords = new HashSet<String>();
	private HashMap<String, Integer> wordcounts = new HashMap<String, Integer>();
	

	/**
	 * @param doc a text file
	 */
	public BOW(File doc) {
		try{
			this.process(doc);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}
	

	/**
	 * @param doc a text file
	 * @param stopwords
	 */
	public BOW(File doc, HashSet<String> stopwords) {
		try{
			this.stopwords = stopwords;
			this.process(doc);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	//TODO ex 4.2
	public HashMap<String, Integer> get_wordcounts() {
		return this.wordcounts;
	}


	/**
	 * Count word occurrences in the text file and store them
	 * 
	 * @param document a text file
	 * @throws IOException
	 */
	public void process(File document) throws IOException {
		FileInputStream fstream = new FileInputStream(document);
		Scanner scanner = new Scanner(fstream);
		try {
			while (scanner.hasNext()){
				String word = scanner.next().trim(); //current word without whitespace
				
				word = lower(word); //doesn't change anything right now
				word = remove_punctuation(word); //doesn't change anything right now
				
				if (stopwords.contains(word)) {
					continue;
				}

				if (!wordcounts.containsKey(word)) {
					//first occurrence
					wordcounts.put(word, 1);
				} else {
					//increase counter
					wordcounts.put(word, wordcounts.get(word)+1);
				}
			}
		}
		finally{
			scanner.close();
			fstream.close();
		}
	}


	/**
	 * @param word
	 * @return Number of times word is found in the document
	 */
	public int get_count(String word) {
		if (wordcounts.containsKey(word)) {
			return wordcounts.get(word);
		}
		return 0;
	}


	/**
	 * @param word
	 * @return word without any non-alphanumeric characters
	 */
	private String remove_punctuation(String word) {
		//TODO ex 4.3
		return word.replaceAll("\\p{Punct}", ""); //dummy return to make the program compile
	}


	/**
	 * @param word
	 * @return lower case version of the input word
	 */
	private String lower(String word) {
		//TODO ex 4.3
		return word.toLowerCase(); //dummy return to make the program compile
	}
}
