/**
 * 
 */
package de.tudarmstadt.gdi1.project.ciph.subst.mono;

import java.util.*;

import de.tudarmstadt.gdi1.project.alphabet.*;
import de.tudarmstadt.gdi1.project.ciph.subst.AbstrSubsCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.exception.*;

/**
 * represents a monoalphabetic cipher
 * @author vamp
 * @version FINAL
 */
public class MonoAlphCiph extends AbstrSubsCipher implements MonoalphabeticCipher {
	
	//two translation tables one for translation, one for reverse
	private HashMap<Character, Character> translationTable;
	private HashMap<Character, Character> reverseTable;
	

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.undefined.AbstrSubsCipher#translate(char, int)
	 */
	@Override
	public char translate(char chr, int i) {
		return translationTable.get(chr);
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.undefined.AbstrSubsCipher#reverseTranslate(char, int)
	 */
	@Override
	public char reverseTranslate(char chr, int i) {
		return reverseTable.get(chr);
	}
	
	/**
	 * fills a HashMap with two arrays of Characters, where the i-th entry of the first array is 
	 * associated with the i-th entry of the second array
	 * 
	 * the two arrays should have the same length, if the first one is longer, an IndexOutOfBoundsException is thrown,
	 * if the second one has more entries, the entries at the end are ignored
	 * 
	 * @param a a <b>char[]</b> array of type <b>Character</b>, the elements are used as keys, therefore it should only contain any element once
	 * @param b a <b>char[]</b> array of type <b>Character</b>, is used as the values for the keys in a
	 * @return a <b>HashMap</b> consisting of the two values of the two arrays
	 */
	public HashMap<Character, Character> fillMap(char[] a, char[] b){
		HashMap<Character, Character> result = new HashMap<Character, Character>();
		for(int i = 0; i < a.length; i++){
			result.put(a[i], b[i]);
		}		
		return result;
	}
	
	
	/**
	 * Constructor of a mono alphabetic cipher, takes two alphabets with the same size
	 * the i-th character in the first alphabet gets substituted by the i-th character in the second alphabet
	 * if the size of the alphabets does not match an (@link InvalidAlphabetException) is thrown
	 * @param a the first alphabet
	 * @param b the second alphabet
	 */
	public MonoAlphCiph(Alphabet a, Alphabet b){
		if(a.size() == b.size()){
			translationTable = fillMap(a.asCharArray(), b.asCharArray());
			reverseTable = fillMap(b.asCharArray(), a.asCharArray());
		}
		else {
			throw new InvalidAlphabetException("The length of the two Alphabets does not match");
		}
	}
	

}
