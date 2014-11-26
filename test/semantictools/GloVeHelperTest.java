package semantictools;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GloVeHelperTest {

	@Test
	public void testDotProduct() throws Exception {
		GloVeHelper gh = new GloVeHelper("/home/dvalsamou/workspace/glove/vocab.txt", "/home/dvalsamou/workspace/glove/vectors.bin", true);
		Double score = 0.0;
		String[] words = {"activate", "man", "interact", "interacts", "interaction", "activation"};
		for (String word1:words){
			for (String word2:words){
				 score = gh.dotProduct(word1, word2);
				System.out.println(word1+", "+word2+" :"+score);
			}
		}
		
		
		assertTrue((score.compareTo(new Double(0.999))>= 0) &&(score.compareTo(new Double(1.001))< 0) );
	}

@Test
public void testEuclidVsDotProduct() throws Exception {
	GloVeHelper gh = new GloVeHelper("/home/dvalsamou/workspace/glove/vocab.txt", "/home/dvalsamou/workspace/glove/vectors.bin", true);
	Double scoreDot = 0.0;
	Double scoreEuc = 0.0;
	String[] words = {"activate", "man", "interact", "interacts", "interaction", "activation"};
	for (String word1:words){
		for (String word2:words){
			scoreDot = gh.dotProduct(word1, word2);
			scoreEuc = gh.euclideanDistance(word1, word2);
			System.out.println(word1+", "+word2+" DOT :"+scoreDot+" EUC : "+scoreEuc);
		}
	}
	
	
	assertTrue(scoreEuc.compareTo(new Double(0.0))== 0);
}
	
	
}

