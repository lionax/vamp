/**
 * 
 */
package de.tudarmstadt.gdi1.project.ciph.subst.poly;

import de.tudarmstadt.gdi1.project.ciph.subst.AbstrSubsCipher;
import de.tudarmstadt.gdi1.project.cipher.substitution.polyalphabetic.PolyalphabeticCipher;
import de.tudarmstadt.gdi1.project.alphabet.*;
import de.tudarmstadt.gdi1.project.exception.*;

/**
 * general polyalphabetic cipher
 * @author vamp
 * @version FINAL
 */
public class ImplPolyalphabeticCipher extends AbstrSubsCipher implements
		PolyalphabeticCipher {
	
	private Alphabet[] key;
	private Alphabet plain;


	/**
	 * @param paraPlain plaintext alphabet
	 * @param paraKey Array of substitution alphabets
	 */
	public ImplPolyalphabeticCipher(Alphabet paraPlain, Alphabet[] paraKey) {
		if (paraKey.length == 0){
			throw new InvalidKeyException("Substitutiontable must not be empty");
		}
		for (int i = 0; i < paraKey.length; i++){
			if (paraKey[i].size() == 0){
				throw new  InvalidAlphabetException("The alphabet does not contain any characters");
			}
		}
		
		key = paraKey;
		plain = paraPlain;
	}
	
	/**
	 * @param chr char to encrypt
	 * @param subst substitution alphabet
	 * @return encrypted char
	 */
	private char encrypt(char chr, Alphabet subst){
		return subst.getChar(plain.getIndex(chr));
	}
	
	/**
	 * @param chr char to decrypt
	 * @param subst substitution alphabet
	 * @return decrypted char
	 */
	private char decrypt(char chr, Alphabet subst){
		if (! subst.contains(chr)){
			throw new IllegalArgumentException("Substitionalphabet does not contain char");
		}
		return plain.getChar(subst.getIndex(chr));
	}
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.ciph.subst.AbstrSubsCipher#translate(char, int)
	 */
	@Override
	public char translate(char chr, int i) {
		if (! plain.contains(chr)){
			throw new IllegalArgumentException("Char is not part of plaintextAlphabet");
		}
		return encrypt(chr, key[i % key.length]);
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.ciph.subst.AbstrSubsCipher#reverseTranslate(char, int)
	 */
	@Override
	public char reverseTranslate(char chr, int i) {
		return decrypt(chr, key[i % key.length]);
	}

}
