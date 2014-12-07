package semantictools;

import org.junit.Test;

import semantictools.ClusterThings;

public class ClusterThingsTest {
//
//	@Test
//	public void testMain() {
//		fail("Not yet implemented"); // TODO
//	}

	@Test
	public void testWord2vecScenario() throws Exception {
		ClusterThings.word2vecScenario("/bibdev/travail/word2vec/trunk/minipub.vectors.bin",  2);
	}

	@Test
	public void testGloveScenario()throws Exception  {
		///home/dvalsamou/workspace/glove/vocab.txt", "/home/dvalsamou/workspace/glove/vectors.bin"

		ClusterThings.gloveScenario("/home/dvalsamou/workspace/glove/vocab.txt", "/home/dvalsamou/workspace/glove/vectors.bin", 200);
	}

}
