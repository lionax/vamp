package de.tudarmstadt.gdi1.project.test.alph;

import org.junit.Assert;
import org.junit.Test;

import de.tudarmstadt.gdi1.project.alph.ImplAlphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.test.TemplateTestCore;

/**
 * Some custom tests of dictionary class
 * @author vamp
 * @version FINAL
 *
 */
public class ImplDictionaryTests {

	@Test
	public void testImplDictionary() {
		Dictionary d = TemplateTestCore.getFactory().getDictionaryInstance(
				new ImplAlphabet(),
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis vel mi pulvinar, " +
						"euismod lorem eget, viverra enim. Cras quis pharetra. "
				);
		
		Assert.assertFalse(d.contains("vel"));
	}

}
