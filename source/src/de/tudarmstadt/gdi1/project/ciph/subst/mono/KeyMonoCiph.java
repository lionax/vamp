package de.tudarmstadt.gdi1.project.ciph.subst.mono;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.KeywordMonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;

/**
 * Representation of the Monoalphabetic Cipher with a Keyword
 * @author vamp
 * @version FINAL
 */
public class KeyMonoCiph extends MonoAlphCiph implements
		KeywordMonoalphabeticCipher {
	
	private static ImplUtils util = new ImplUtils();
	
	/**
	 * Constructs a Object of type Monoalphabetic Cipher with Keyword
	 * @param a is the base alphabet
	 * @param keyword the key that will be used to generate the second alphabet
	 */
	public KeyMonoCiph(Alphabet a, String keyword) {
		super(a, util.buildAlphabetWithKey(a, keyword));
	}
}
