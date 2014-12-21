package semantictools;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This type of object serves to keep the two lookup tables word2sem and sem2word which keep (respectively) a mapping (lookup) of words to their classes and a mapping (lookup) of semantic classes to their words
 * @author dvalsamou
 *
 */
public class SemanticTable {
	WordToSemClassTable word2sem = new WordToSemClassTable();
	SemClassToWordsTable sem2word = new SemClassToWordsTable();
	
	public SemanticTable() {
	}
	
	public SemanticTable(WordToSemClassTable w2s, SemClassToWordsTable s2w){
		word2sem = w2s;
		sem2word = s2w;
	}
	/**
	 * Adds a list of words to a semantic class. Updates both lookup tables.
	 * @param name the name of the class
	 * @param words the words (HashSet<String>)
	 */
	public void add(String name, HashSet<String> words) {
		sem2word.put(name, words);
		word2sem.populate(sem2word.getSemanticClass(name));
	}
	/**
	 * Adds a word to a semantic class, Updates both lookup tables
	 * @param name the name of the class
	 * @param word
	 */
	public void add(String name, String word){
		sem2word.add(name, word);
		word2sem.populate(sem2word.getSemanticClass(name));
	}
	/**
	 * Adds a new semantic class object to the two lookup tables
	 * @param semClass
	 */
	public void add(SemanticClass semClass){
		sem2word.add(semClass);
		word2sem.populate(semClass);
	}
	/**
	 * Adds a list of semantic class objects to the two lookup tables
	 * @param semClassList
	 */
	public void addAll(ArrayList<SemanticClass> semClassList){
		sem2word.addAll(semClassList);
		word2sem.populateAll(semClassList);
	}
	
	/**
	 * Gets the semantic class object that has a specific name
	 * @param name
	 * @return the SemanticClass object by that name
	 */
	public SemanticClass getSemanticClassByName(String name){
		return sem2word.getSemanticClass(name);
	}
	
	/**
	 * Gets the semantic class object which contains a specific word
	 * @param word
	 * @return the SemanticClass object that contains that word
	 */
	public SemanticClass getSemanticClassByWord(String word){
		return word2sem.getSemanticClassOf(word);
	}
	/**
	 * Prints a human readable list of the semantic classes (names) and their words
	 */
	public void printSemanticClasses(){
		System.out.println("Semantic Classes:");
		for (SemanticClass semClass: sem2word.getSemanticClasses()){
			semClass.print();
		}
	}

}
