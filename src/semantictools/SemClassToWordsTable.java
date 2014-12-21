package semantictools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/**
 * A lookup table that gives a class -> words mapping. You can get directly the words as it is a HashMap, but it also keeps a list of the SemanticClass objects. 
 * @author dvalsamou
 *
 */
public class SemClassToWordsTable extends HashMap<String, HashSet<String>>{
	ArrayList<SemanticClass> semanticClasses = new ArrayList<SemanticClass>();

	private static final long serialVersionUID = 1L;

	public SemClassToWordsTable() {
	}

	/**
	 * Mimics a HashMap put, by adding this semantic class(parameter) to the map. It adds an entry containing the SemanticClass(key) and all of its words (value)
	 * @param semclass
	 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
	 */
	public HashSet<String> add(SemanticClass semclass){
		if (!semanticClasses.contains(semclass)) {
			semanticClasses.add(semclass);
		}
		return super.put(semclass.name, semclass.words); 
	}

	/**
	 * Mimics a HashMap put, by adding each semantic class of the set (parameter) to the map. For each SemanticClass it adds an entry containing the SemanticClass(key) and all of its words (value)
	 * @param semclass
	 */
	public void addAll(ArrayList<SemanticClass> semClassList) {
		for (SemanticClass semClass:semClassList){
			add(semClass);
		}
	}

	/**
	 * Get the SemanticClass object for a semantic class  name
	 * @param name name of the class
	 * @return the SemanticClass object
	 */
	public SemanticClass getSemanticClass(String name) {
		SemanticClass semclass = null;
		for (SemanticClass semClassInTheList: semanticClasses){
			if (semClassInTheList.name.equals(name)){
				semclass = semClassInTheList;
			}
		}
		return semclass;
	}

	/**
	 * A simple getter for the list of semantic classes
	 * @return the semanticClasses (ArrayList of SemanticClass objects)
	 */
	public ArrayList<SemanticClass> getSemanticClasses(){
		return semanticClasses;
	}

	/**
	 * Add a mapping between a semantic class and a word. If there exists a semantic class with this name add the word to its words, if it is a new class, create it and add this word
	 * @param name the name of the class
	 * @param word the word
	 * @return the SemanticClass object 
	 */
	public SemanticClass add(String name, String word) {
		if (!containsKey(name)) {
			put(name, new HashSet<String>());
		}
		get(name).add(word);

		SemanticClass semClass = getSemanticClass(name);

		if (semClass == null){
			semClass = new SemanticClass(name);
			semanticClasses.add(semClass);
		}
		semClass.add(word);
		return semClass;
	}




}
