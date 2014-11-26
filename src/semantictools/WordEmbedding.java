package semantictools;

import java.util.ArrayList;
import java.util.HashMap;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class WordEmbedding {
	HashMap<String, ArrayList<Double>>  vectors = new HashMap<String, ArrayList<Double>>();
	Integer vocab_size =0;
	Integer vector_size=0;


	public WordEmbedding() {
		// TODO Auto-generated constructor stub
	}




	public  Double dotProduct(String word1, String word2) {
		Double score = 0.0;
		final ArrayList<Double> w1v = vectors.get(word1);
		final ArrayList<Double> w2v = vectors.get(word2);
		if (w1v == null || w2v == null) return -100.0;
		for (int i = 0; i< w1v.size(); i++){
			score += w1v.get(i) * w2v.get(i);
		}
		return score;
	}
	
	public  Double dotProduct(ArrayList<Double> w1v, ArrayList<Double> w2v) {
		Double score = 0.0;
		if (w1v == null || w2v == null) return -100.0;
		if (w1v.size()!= w2v.size())
			try {
				throw new Exception("Vectors have different sizes!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		for (int i = 0; i< w1v.size(); i++){
			score += w1v.get(i) * w2v.get(i);
		}
		return score;
	}
	

	public ArrayList<Double> getVector (String word){
		return vectors.get(word);
	}

	public Double euclideanDistance(String word1, String word2) {
		ArrayList<Double> w1v = vectors.get(word1);
		ArrayList<Double> w2v = vectors.get(word2);
		if (w1v == null || w2v == null) return -100.0;

		double score = 0.0;
		for (int i = 0, n = w1v.size(); i < n; i++) {
			score += Math.pow(w1v.get(i) - w2v.get(i), 2);
		}
		return Math.sqrt(score);
	}



	public ArrayList<Double> normalizeVector( ArrayList<Double> vector) {
		ArrayList<Double> normalizedVector = new ArrayList<Double>();
		Double length = new Double(0);
		for (int i = 0; i< vector.size(); i++){
			length += vector.get(i)* vector.get(i);
		}
		length = (double) Math.sqrt((double) length);
		for (int i = 0; i< vector.size(); i++){
			normalizedVector.add(vector.get(i)/length);
		}
		return normalizedVector;
	}
	
	public SemanticTable kMeansClustering (int numOfClusters) throws Exception{
		SemanticTable semTable = new SemanticTable();
		
//		Instances instances = new Instances();
		
		SimpleKMeans kmeans = new SimpleKMeans();
		kmeans.setSeed(10);
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(numOfClusters);
		kmeans.buildClusterer(produceInstances());
		// This array returns the cluster number (starting with 0) for each instance
		// The array has as many elements as the number of instances
		int[] intassignments = kmeans.getAssignments();
		Integer[] assignments = new Integer[intassignments.length];
		for (int j=0; j< intassignments.length; j++){
			assignments[j] = Integer.valueOf(intassignments[j]);
		}
//		Set<Integer> semClassesNamesInt = new TreeSet<Integer>();
//		semClassesNamesInt.addAll(Arrays.asList(assignments));
//		HashMap<Integer, SemanticClass> semanticClassesMap = new HashMap<>();
//		SemClassToWordsTable semanticClassesMap = semTable.getSemClassToWordsTable();
//		for (Integer classNo:semClassesNamesInt){
//			String name = ""+classNo;
//			SemanticClass semClass = new SemanticClass(name);
//			semanticClassesMap.put(name, semClass);
//		}
		ArrayList<String> vocab = new ArrayList<>();  
		vocab.addAll(vectors.keySet());
		
		int i=0;
		for(int clusterNum : assignments) {
			String word = vocab.get(i);
			String name = ""+clusterNum;
			semTable.add(name, word);
//			semanticClassesMap.get(clusterNum).add(word);
		    System.out.printf("Instance %d ("+word+") -> Cluster %d\n", i+1, clusterNum);
		    i++;
		}
		ArrayList<String> centroids = instancesToWords(kmeans.getClusterCentroids());
		
		for (String word:centroids){
			SemanticClass semClass = semTable.getSemanticClassByWord(word);
			semClass.centroid = word;
		}
		int[] sizes = kmeans.getClusterSizes();
		
		for (int k =0; k< sizes.length; k++) {
			SemanticClass semClass = semTable.getSemanticClassByName(""+k);
			semClass.size = new Integer(sizes[k]);
		}
		

		return semTable;
		
	}
	

	
	public Instances produceInstances(){
		ArrayList<Attribute> fvWekaAttributes = new ArrayList<Attribute>();
		for (int i = 0; i < vector_size ; i++){
			Attribute attr = new Attribute("col"+i);
			fvWekaAttributes.add(attr);
		}
		Instances instances = new Instances("kmeansInstances", fvWekaAttributes, vocab_size);
		for (String word:vectors.keySet()) {
			Instance iExample = new DenseInstance(fvWekaAttributes.size());
			for (int i = 0; i < vector_size; i++){
				iExample.setValue(fvWekaAttributes.get(i), vectors.get(word).get(i));
			}
			instances.add(iExample);
		}
 		return instances;
	}
	
	public ArrayList<Double> instanceToVector(Instance inst){
		ArrayList<Double> vector = new ArrayList<>();
		for (int i = 0; i < inst.numAttributes(); i++){
			vector.add(inst.value(i));
		}
		return vector;
	}
	
	public String vectorToClosestWord(ArrayList<Double> vector){
		String word = null;
		Double min = Double.POSITIVE_INFINITY;
		for (String w:vectors.keySet()){
			ArrayList<Double> w_vector = vectors.get(w);
			Double product = dotProduct(w_vector, vector);
			if ( product < min){
				min = product;
				word = w;
			}
		}
		return word;
	}
	
	public ArrayList<String> instancesToWords(Instances insts){
		ArrayList<String> words = new ArrayList<>();
		for (Instance inst:insts){
			ArrayList<Double> vector = instanceToVector(inst);
			String word = vectorToClosestWord(vector);
			if (word!= null) words.add(word);
		}
		return words;
		
	}
//	public Boolean sameVectors(ArrayList<Double> vector1, ArrayList<Double> vector2){
////		Collection<Double> commonList = CollectionUtils.retainAll(vector1, vector2);
////		Boolean same = commonList.size() == vector1.size();
//		String text1 = vectorToString(vector1);
//		String text2 = vectorToString(vector2);
//		
// 		return text1.equals(text2);
//	}
//	
//	private String vectorToString(ArrayList<Double> vector){
//		String text = "";
//		for (Double num:vector) {
//			String numtext=num.toString();
//			numtext.subSequence(0, 6);
//			text+=numtext;
//		}
//		return text;
//	}
}
