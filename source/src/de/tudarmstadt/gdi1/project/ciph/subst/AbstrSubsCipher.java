package de.tudarmstadt.gdi1.project.ciph.subst;

import de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher;

/**
 * 
 * @author vamp
 * @version FINAL
 */
public abstract class AbstrSubsCipher implements SubstitutionCipher {
	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.Cipher#encrypt(java.lang.String)
	 */
	@Override
	final public String encrypt(String text) {
		char[] textAsChar = text.toCharArray();
		char[] resultAsChar = new char[textAsChar.length];
		
		for(int i =0; i < textAsChar.length; i++){
			resultAsChar[i] = this.translate(textAsChar[i], i);
		}
		
		return new String(resultAsChar);
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.Cipher#decrypt(java.lang.String)
	 */
	@Override
	final public String decrypt(String text) {
		char[] textAsChar = text.toCharArray();
		char[] resultAsChar = new char[textAsChar.length];
		
		for(int i =0; i < textAsChar.length; i++){
			resultAsChar[i] = reverseTranslate(textAsChar[i], i);
		}
		
		return new String(resultAsChar);
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher#translate(char, int)
	 */
	@Override
	abstract public char translate(char chr, int i);

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.substitution.SubstitutionCipher#reverseTranslate(char, int)
	 */
	@Override
	abstract public char reverseTranslate(char chr, int i);

}
