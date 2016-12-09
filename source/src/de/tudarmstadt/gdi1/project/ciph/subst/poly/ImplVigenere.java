/**
 * 
 */
package de.tudarmstadt.gdi1.project.ciph.subst.poly;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.Vigenere;
import de.tudarmstadt.gdi1.project.exception.InvalidAlphabetException;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;
import de.tudarmstadt.gdi1.project.utils.Utils;

/**
 * Vigenere-Cipher
 * @author vamp
 * @version FINAL
 */
public class ImplVigenere extends ImplPolyalphabeticCipher implements Vigenere {
	private static Utils util = new ImplUtils();

	/**
	 * generates a PolyAlphabeticCipher using a Vigenere table
	 * @param string keyphrase
	 * @param a the key alphabet
	 * 
	 */
	public ImplVigenere (String string, Alphabet a){
		super(a, generateArray(string, a));
	}
	
	/**
	 * generates an Alphabet[] using the keyphrase and an alphabet
	 * @param string keyphrase
	 * @param a key alphabet
	 * @return Alphabet array. A[1] = by Phrase[1] shifted key alphabet; A[2] = by Phrase[2] shifted key alphabet,... see Vigenere Table
	 */
	private static Alphabet[] generateArray(String string, Alphabet a){
		if (string.length() == 0){
			throw new IllegalArgumentException("Cannot use empty string as key");
		}
		if (a.size() == 0){
			throw new InvalidAlphabetException("Alphabet is empty");
		}
		if (! a.allows(string)){
			throw new IllegalArgumentException("Key is not allowed by alphabet");
		}
		
		char[] charArray = string.toCharArray();
		Alphabet[] alphArray = new Alphabet[charArray.length];
		
		for (int i = 0; i < charArray.length; i++){
			alphArray[i] = util.shiftAlphabet(a, a.getIndex(charArray[i]));
		}
		return alphArray;
	}
}
