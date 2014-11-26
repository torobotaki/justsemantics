package semantictools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;


public class GloVeHelper extends WordEmbedding{
ArrayList<String> words = new ArrayList<>();

	
	public GloVeHelper(String vocabulary_filename, String vector_filename, Boolean binary) throws Exception {
		words = readWords(vocabulary_filename); 
		ArrayList<ArrayList<Double>> WW_vectors = readVectors(vector_filename, binary);
		vectors = getWvectors(WW_vectors, words);

		}
	
	public ArrayList<String> readWords (String filename) throws IOException {
		BufferedReader freader = new BufferedReader(new FileReader(filename));
		String line = null;
		while ((line = freader.readLine()) != null) {
			String word = line.split(" ")[0];
			words.add(word);
		}
		freader.close();
		vocab_size = words.size();
		return words;

	}




	public  ArrayList<ArrayList<Double>> readVectors (String filename, Boolean binary) throws IOException {  
			RandomAccessFile vector_file = new RandomAccessFile(filename, "r");
			vector_file.seek(vector_file.length());
			vector_size = new Integer((int) (vector_file.getFilePointer()/16/vocab_size -1));
			vector_file.seek(0);
			ArrayList<ArrayList<Double>> WW_vectors = new ArrayList<ArrayList<Double>>();
			
			
			for (int i = 0; i <2*vocab_size; i++) {
				ArrayList<Double> WW_single_vector = new ArrayList<>();
				byte[] binary_data = new byte[8];
				for (int j = 0; j <  vector_size; j++){
					vector_file.readFully(binary_data);
					
					double value = ByteBuffer.wrap( binary_data ).order( ByteOrder.LITTLE_ENDIAN ).getDouble();
					WW_single_vector.add(new Double(value));
				}
				WW_vectors.add(normalizeVector(WW_single_vector));
			}
			vector_file.close();
			return WW_vectors;

			

	}
	
	HashMap<String, ArrayList<Double>> getWvectors ( ArrayList<ArrayList<Double>> WW_vectors, ArrayList<String> words) throws Exception{
		HashMap<String, ArrayList<Double>> W1 = new HashMap<String, ArrayList<Double>>();
		if (words.size() != WW_vectors.size()/2) {
			throw (new Exception("vector size doesnt match"));
		}
		for (int i = 0; i < words.size(); i++){
			W1.put(words.get(i), WW_vectors.get(i));
		}
		
		return W1;
		
	}
			
	

}
