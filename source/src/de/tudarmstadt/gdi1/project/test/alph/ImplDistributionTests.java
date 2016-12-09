package de.tudarmstadt.gdi1.project.test.alph;

import static org.junit.Assert.*;

import org.junit.Test;

import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;
import de.tudarmstadt.gdi1.project.test.TemplateTestUtils;

/**
 * Testcases for some methods of ImplDistribution, that are not tested by the template
 * @author vamp
 * @version FINAL
 * 
 */
public class ImplDistributionTests {
	
	@Test
	public void testGetAlphabet() {
		String s = "abcdefghijklmnopqrstuvwxyz";
		
		Distribution distribution = TemplateTestCore.getFactory().getDistributionInstance(
				TemplateTestUtils.getDefaultAlphabet(), s, 2);
		
		org.junit.Assert.assertArrayEquals(distribution.getAlphabet().asCharArray(), TemplateTestUtils.getDefaultAlphabet().asCharArray());
	}

	@Test
	public void testGetByRank() {
		String text = "aaabbccccd";
		String sortedBigrams;

		Distribution d = TemplateTestCore.getFactory().getDistributionInstance(
				TemplateTestUtils.getDefaultAlphabet(), text, 2);
		
		// Test one
		sortedBigrams = d.getByRank(2, 1);
		assertEquals("cc", sortedBigrams);
		
		// Test two
		sortedBigrams = d.getByRank(2, 2);
		assertEquals("aa", sortedBigrams);
	}

}