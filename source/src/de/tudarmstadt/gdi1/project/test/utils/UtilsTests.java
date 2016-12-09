package de.tudarmstadt.gdi1.project.test.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.test.TemplateTestUtils;
import de.tudarmstadt.gdi1.project.test.custom.TestUtils;
import de.tudarmstadt.gdi1.project.utils.*;

/**
 * Test cases for custom utils class
 * @author vamp
 * @version FINAL
 */
public class UtilsTests {

	private ImplUtils utils;
	
	@Before
	public void initializeUtils() {
		utils = new ImplUtils();
	}
	
	@Test
	public void testKillDoubles() {
		String test1 = TestUtils.getDefaultKeywordAsString();
		String test2 = "aaaa";
		String test3 = "gdipraktumdcba";
		String test1Result = utils.killDoubles(test1);
		String test2Result = utils.killDoubles(test2);
		String test3Result = utils.killDoubles(test3);
		assertEquals(TestUtils.getDefaultKeywordAsStringWithoutDoubles(), test1Result);
		assertEquals("a", test2Result);
		assertEquals("gdipraktumcb", test3Result);
	}

	@Test
	public void testBuildAlphabetWithKey() {
		Alphabet a = TemplateTestUtils.getMinimalAlphabet();
		String keyword = TestUtils.getDefaultKeywordAsString();
		Alphabet expected = TestUtils.getDefaultKeywordWithReversedMinimalAlphabet();
		
		Alphabet test1 = utils.buildAlphabetWithKey(a, keyword);
		org.junit.Assert.assertArrayEquals(expected.asCharArray(), test1.asCharArray());
	}
	
	@Test
	public void testToDisplay(){
		String ugly2 = "abcdefghijklmnopqrstufwxyz1234abcdefghijklmnopqrstufwxyz1234abcdefghijklmnopqrstufwxyz1234abcdefghijklmnopqrstufwxyz123";
		String pretty2 = "abcdefghij klmnopqrst ufwxyz1234 abcdefghij klmnopqrst ufwxyz1234" + System.lineSeparator()
				+ "abcdefghij klmnopqrst ufwxyz1234 abcdefghij klmnopqrst ufwxyz123";
		assertEquals(pretty2, utils.toDisplay(ugly2));	
	}
	
}