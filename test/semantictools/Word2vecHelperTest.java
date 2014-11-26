package semantictools;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Word2vecHelperTest {

	@Test
	public void test() throws Exception {
		Word2vecHelper w2v = new Word2vecHelper("/bibdev/travail/word2vec/trunk/minipub.vectors.bin", true);
		Double score = w2v.dotProduct("the", "the");
//		assertEquals(new Double(0.3219287845531653), score);
		assertTrue(score.compareTo(new Double(0.0))> 0);
	}
	
	@Test
	public void testKMeans() throws Exception {
		Word2vecHelper w2v = new Word2vecHelper("/bibdev/travail/word2vec/trunk/minipub.vectors.bin", true);
		SemanticTable semTable = w2v.kMeansClustering(2);
		semTable.printSemanticClasses();

		
	}
	
	@Test
	public void testProduceInstances() throws Exception{
		Word2vecHelper w2v = new Word2vecHelper("/bibdev/travail/word2vec/trunk/minipub.vectors.bin", true);
		w2v.produceInstances();
	}
}
