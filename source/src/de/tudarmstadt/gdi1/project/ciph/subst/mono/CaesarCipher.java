package de.tudarmstadt.gdi1.project.ciph.subst.mono;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.Caesar;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;
import de.tudarmstadt.gdi1.project.utils.Utils;

/**
 * Representation of the Caesar Cipher method, which is of type Monoalphabetic
 * @author vamp
 * @version FINAL
 */
public class CaesarCipher extends MonoAlphCiph implements Caesar {
	
	private static Utils util = new ImplUtils();
	
	/**
	 * This Constructor takes a alphabet and a shift distance and generates a with these
	 * the second alphabet to which the first get's mapped to.
	 * @param a is an alphabet which is the base of both texts, encrypted and plaintext
	 * @param shift is the distance that the alphabet gets shifted by
	 */
	public CaesarCipher(Alphabet a, int shift) {
		super(a, util.shiftAlphabet(a, shift));
	}
	
}
