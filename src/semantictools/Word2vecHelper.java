package semantictools;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;



public class Word2vecHelper extends WordEmbedding {
	
	public Word2vecHelper(String filename, Boolean binary) throws Exception {
		vectors = readFile(filename, binary);
		}

	public  HashMap<String, ArrayList<Double>>  readFile (String filename, Boolean binary) throws Exception {  
		HashMap<String, ArrayList<Double>> vectors = new HashMap<String, ArrayList<Double>>();
		DataInputStream dataIS = new DataInputStream(new FileInputStream(new File(filename)));
		String line = "";
		while (true){
			char ch = (char) dataIS.read();
			if (ch != '\n'){
				line = line+ch;
			}
			else break;
		}
		String[] nums = line.split(" ");
		 vocab_size = Integer.parseInt(nums[0]) ;
		 vector_size = Integer.parseInt(nums[1]);
//		System.out.println(vocab_size+" "+layer1_size);
		if (binary){
			int binary_len = 4*vector_size;
			byte[] binary_data = new byte[binary_len];
			for (int line_no=0; line_no < vocab_size; line_no ++){
				ArrayList<Double> vector = new ArrayList<>();
				String word = "";
				while (true){
					char ch = (char) dataIS.read();
					if (ch == ' '){
						word = word+"";
						break;
					}
					if (ch != '\n'){
						word = word+ch;
					}
				}//word read
//				System.out.print(word+" ");
				int vectorbytes = dataIS.read(binary_data);
				for (int i= 0; i< vectorbytes; i +=4){
					byte[]  bytes = {binary_data[i],binary_data[i+1],binary_data[i+2],binary_data[i+3]};
					float f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
//					float f = ByteBuffer.wrap( bytes ).order( ByteOrder.BIG_ENDIAN ).getDouble();  
					vector.add(new Double(f));
//					System.out.print(String.format("%.6f", f)+" ");
				}
//				System.out.println("");
				vectors.put(word, normalizeVector(vector));
			}
		}
		else {
			dataIS.close();
			throw new Exception("only binary files are supported for the time being!");
		}
		dataIS.close();
		System.out.println("succesfully read word2vec vector file: "+filename);
		return vectors;
	}
	


	
	
	


}
