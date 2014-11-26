package semantictools;

import java.util.ArrayList;
import java.util.HashSet;

public class SemanticTable {
	WordToSemClassTable word2sem = new WordToSemClassTable();
	SemClassToWordsTable sem2word = new SemClassToWordsTable();
	
	public SemanticTable() {
	}
	
	public SemanticTable(WordToSemClassTable w2s, SemClassToWordsTable s2w){
		word2sem = w2s;
		sem2word = s2w;
	}
	
	public WordToSemClassTable getWordToSemClassTable(){
		return word2sem;
	}
	
	public SemClassToWordsTable getSemClassToWordsTable(){
		return sem2word;
	}
	
	public ArrayList<SemanticClass> getSemanticClasses(){
		return sem2word.getSemanticClasses();
	}
	
	public ArrayList<String> getAllWords(){
		return word2sem.getWords();
	}
	

	public void add(String name, HashSet<String> words) {
		sem2word.put(name, words);
		word2sem.populate(sem2word.getSemanticClass(name));
	}
	
	public void add(String name, String word){
		sem2word.add(name, word);
		word2sem.populate(sem2word.getSemanticClass(name));
	}
	
	public void add(SemanticClass semClass){
		sem2word.add(semClass);
		word2sem.populate(semClass);
	}
	
	public void addAll(ArrayList<SemanticClass> semClassList){
		sem2word.addAll(semClassList);
		word2sem.populateAll(semClassList);
	}
	
	public SemanticClass getSemanticClassByName(String name){
		return sem2word.getSemanticClass(name);
	}
	
	public HashSet<String> getWordsForThisClass(String name){
		return sem2word.getSemanticClass(name).words;
	}
	
	public SemanticClass getSemanticClassByWord(String word){
		return word2sem.getSemanticClassOf(word);
	}
	public String getSemanticClassNameByWord(String word){
		return word2sem.getSemanticClassOf(word).name;
	}
	
	public Boolean inTheSameClass(String word1, String word2){
		return word2sem.inTheSameClass(word1, word2);
	}
	
	public void printSemanticClasses(){
		System.out.println("Semantic Classes:");
		for (SemanticClass semClass: sem2word.getSemanticClasses()){
			semClass.print();
		}
	}

}
