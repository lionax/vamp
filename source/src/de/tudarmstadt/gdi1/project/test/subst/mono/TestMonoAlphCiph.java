package de.tudarmstadt.gdi1.project.test.subst.mono;

import de.tudarmstadt.gdi1.project.alph.*;
import de.tudarmstadt.gdi1.project.ciph.subst.mono.MonoAlphCiph;
import de.tudarmstadt.gdi1.project.test.*;

import org.junit.*;

import de.tudarmstadt.gdi1.project.exception.*;

/**
 * tests the monoalphabetic cipher
 * @author vamp
 * @version FINAL
 *
 */
public class TestMonoAlphCiph {

	MonoAlphCiph ciph = new MonoAlphCiph(new ImplAlphabet(new char[]{'a', 'b', 'c'}), new ImplAlphabet(new char[]{'L', 'x', '!'}));
	
	@Test
	public void test1(){
		Assert.assertEquals('!', ciph.translate('c', 1));
		Assert.assertEquals('a', ciph.reverseTranslate('L', 10));
	}
	
	@Test(expected = InvalidAlphabetException.class)
	public void test2(){
		new MonoAlphCiph(new ImplAlphabet(), TemplateTestUtils.getDefaultAlphabet());
	}
	
	
}
