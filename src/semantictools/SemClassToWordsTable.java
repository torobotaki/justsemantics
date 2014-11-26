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


	public HashSet<String> add(SemanticClass semclass){
		if (!semanticClasses.contains(semclass)) {
			semanticClasses.add(semclass);
		}
		return super.put(semclass.name, semclass.words); 
	}
	
	public void addAll(ArrayList<SemanticClass> semClassList) {
		for (SemanticClass semClass:semClassList){
			add(semClass);
		}
	}
	public HashSet<String> put(String name, HashSet<String> words){
		SemanticClass semclass =getSemanticClass(name);
		if (semclass==null) { 
		 semclass = new SemanticClass(name, words);
			return add(semclass); 
		}
		else {
			semclass.addAll(words);
			return super.put(semclass.name, semclass.words); 
		}
	}
	public HashSet<String> put(String name, String word){
		SemanticClass semclass =getSemanticClass(name);
		if (semclass==null) { 
		 semclass = new SemanticClass(name, new HashSet<String>());
		 	semclass.add(word);
			return add(semclass); 
		}
		else {
			semclass.add(word);
			return super.put(semclass.name, semclass.words); 
		}
	}

	public SemanticClass getSemanticClass(String name) {
		SemanticClass semclass = null;
		for (SemanticClass semClassInTheList: semanticClasses){
			if (semClassInTheList.name.equals(name)){
				semclass = semClassInTheList;
			}
		}
		return semclass;
	}

	public ArrayList<SemanticClass> getSemanticClasses(){
		return semanticClasses;
	}

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
