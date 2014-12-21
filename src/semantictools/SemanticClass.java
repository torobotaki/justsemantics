package semantictools;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Object that holds a Semantic Class. If the class doesn't have a name, an arbitrary name will be produced. A Semantic Class can have a name, words, a (cluster) centroid and a size.
 * @author dvalsamou
 *
 */
public class SemanticClass {
	String name = "";
	HashSet<String> words = new HashSet<String>();
	String centroid = "";
	Integer size = new Integer(0);

	public void setClusterStats(String centroidWord, Integer sizeOfCluster){
		centroid = centroidWord;
		size = sizeOfCluster;
	}
	public SemanticClass(String name, HashSet<String> words){
		this.name = name;
		this.words = words;

	}
	public SemanticClass(HashSet<String> words){
		this(generateString(), words);

	}
	public SemanticClass(){
		this(new HashSet<String>());
	}

	public SemanticClass(String name) {
		this(name, new HashSet<String>());
	}
	public HashSet<String> getWords(){
		return words;
	}

	public Boolean contains(String word){
		return words.contains(word);
	}

	public boolean add(String word){
		return words.add(word);
	}

	public boolean addAll(List<String> wordList){
		return words.addAll(wordList);
	}
	
	public boolean addAll(Set<String> wordList){
		return words.addAll(wordList);
	}
	private static String generateString(  ) {
		Random rng = new Random();
		int letlength = 2;
		int numlength = 3;
		String letters ="abcdefghijklmnopqrstuvwxyz";
		String numbers ="1234567890";
		char[] let = new char[letlength];
		for (int i = 0; i < letlength; i++){
			let[i] = letters.charAt(rng.nextInt(letters.length()));
		}
		char[] num = new char[numlength];
		for (int i = 0; i < numlength; i++){
			num[i] = numbers.charAt(rng.nextInt(numbers.length()));
		}
		return ((new String(let))+(new String(num)));
	}

	public void print(){
		System.out.println(name+" :");
		System.out.println("\tword closest to centroid: "+centroid);
		System.out.println("\tsize: "+size);
		System.out.print("\twords: [");
		TreeSet<String> sortedWords = new TreeSet<String>(words);
		Boolean first = true;
		for (String word: sortedWords) {
			if (first) {
				System.out.print(" "+word);
				first = false;
			}
			else {
			System.out.print(", "+word);
			}
		}
		System.out.println(" ].");
	}

}
