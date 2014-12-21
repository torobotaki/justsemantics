package semantictools;

import java.util.ArrayList;
import java.util.HashMap;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * An abstract class which implements the idea of word embeddings, in the form of a {@link HashMap HashMap} of String (word) to ArrayList<Double> (vectors). 
 * It contains all the common things between different format types, such as methods to calculate distances and to transform to and from words, vectors, and Weka Instances.
 * @author dvalsamou
 *
 */
public abstract class WordEmbedding {
	HashMap<String, ArrayList<Double>>  vectors = new HashMap<String, ArrayList<Double>>();
	Integer vocab_size =0;
	Integer vector_size=0;


	public WordEmbedding() {
	}



	/**
	 * Calculates the similarity of two words by calculating the dot product of their vectors
	 * @param word1
	 * @param word2
	 * @return similarity as a Double
	 */
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
	/**
	 * Calculates the distance of two words by calculating the euclidean distance between their vectors
	 * @param word1
	 * @param word2
	 * @return distance as a Double
	 */
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
	/**
	 * Produce a SemanticTable object by calculating the clustering using Weka's KMeans. 
	 * @param numOfClusters the k for kMeans
	 * @return the semantic table, ie the set of classes/clusters calculated, as a SemanticTable object
	 * @throws Exception
	 */
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
		ArrayList<String> vocab = new ArrayList<>();  
		vocab.addAll(vectors.keySet());

		int i=0;
		for(int clusterNum : assignments) {
			String word = vocab.get(i);
			String name = ""+clusterNum;
			semTable.add(name, word);
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


	/**
	 * Transform the vectors into Weka Instances. 
	 * @return the Instances set. The attribute names are colX, where X is 0 to vector_size -1 and the values are "double" numbers.
	 */
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

	/**
	 * Transform a Weka Instance to the corresponding double vector
	 * @param inst
	 * @return an ArrayList<Double> representation of the vector
	 */
	public ArrayList<Double> instanceToVector(Instance inst){
		ArrayList<Double> vector = new ArrayList<>();
		for (int i = 0; i < inst.numAttributes(); i++){
			vector.add(inst.value(i));
		}
		return vector;
	}

	/**
	 * Returns the closest word to a given vector
	 * @param vector as an ArrayList<Double>
	 * @return word as a String
	 */
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
	/**
	 * Takes a set of instances (Weka's Instances) and returns the original words. This is a short cut/approximation as it actually gives the "closest" words, but it should be good enough for now.
	 * @param insts
	 * @return an ArrayList<String> of the words closest to these instances, ie the original words.
	 */
	public ArrayList<String> instancesToWords(Instances insts){
		ArrayList<String> words = new ArrayList<>();
		for (Instance inst:insts){
			ArrayList<Double> vector = instanceToVector(inst);
			String word = vectorToClosestWord(vector);
			if (word!= null) words.add(word);
		}
		return words;

	}
}
