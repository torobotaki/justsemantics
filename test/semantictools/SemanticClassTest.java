package semantictools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class SemanticClassTest {



	@Test
	public void testAddGetPrint() {
		SemanticClass koko = new SemanticClass();
		ArrayList<String> wordList = new ArrayList<String>();
		assertFalse(koko.addAll(wordList));
		wordList.add("koko");
		wordList.add("lala");
		assertTrue(koko.addAll(wordList));
		assertTrue(koko.add("mimi"));
		assertTrue(koko.contains("mimi"));
		koko.print();
	}


}
