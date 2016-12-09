package de.tudarmstadt.gdi1.project.ana.mono;

import java.util.LinkedHashMap;
import java.util.Map;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.alphabet.Dictionary;
import de.tudarmstadt.gdi1.project.alphabet.Distribution;
import de.tudarmstadt.gdi1.project.analysis.EncryptionOracle;
import de.tudarmstadt.gdi1.project.analysis.monoalphabetic.MonoalphabeticCpaNpaCryptanalysis;
import de.tudarmstadt.gdi1.project.cipher.substitution.monoalphabetic.MonoalphabeticCipher;
import de.tudarmstadt.gdi1.project.exception.*;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;

/**
 * Represents the cryptanalysis of the monoalphabetic encryption
 * @author vamp
 * @version FINAL
 *
 */
public class MonoCpaNpaCryptanalysis implements
		MonoalphabeticCpaNpaCryptanalysis {

	@Override
	public Object knownPlaintextAttack(String ciphertext, String plaintext,
			Distribution distribution) {
		
		return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
	}

	@Override
	public Object knownPlaintextAttack(String ciphertext, String plaintext,
			Distribution distribution, Dictionary dictionary) {

		return knownPlaintextAttack(ciphertext, plaintext, distribution.getAlphabet());
	}

	@Override
	public char[] knownPlaintextAttack(String ciphertext, String plaintext,
			Alphabet alphabet) {
		ImplUtils utils = new ImplUtils();
		
		if((ciphertext == null) || ciphertext.isEmpty()) {
			throw new IllegalArgumentException("Kein Ciphertext übergeben!");
		}
		
		char[] resChars = new char[alphabet.size()];
		
		String cleanedPlaintext = utils.killDoubles(plaintext);
		String cleanedCiphertext = utils.killDoubles(ciphertext);
		
		// use LinkedHashMap to keep Order of insertions
		Map<Character, Character> tempMap = new LinkedHashMap<Character, Character>();
		
		// create mapping of plaintext to ciphertext characters
		for(int i = 0; i < cleanedPlaintext.length(); i++) {
			tempMap.put(cleanedPlaintext.toCharArray()[i], cleanedCiphertext.toCharArray()[i]);
		}
		
		for(int i = 0; i < alphabet.size(); i++) {
			// check if character of alphabet has mapping to encrypted char
			if(tempMap.containsKey(alphabet.asCharArray()[i])) {
				resChars[i] = tempMap.get(alphabet.asCharArray()[i]);
			}
			else {
				// leave fields blank for which no solution could be computed
				resChars[i] = ' ';
			}
		}

		return resChars;
	}

	@Override
	public char[] chosenPlaintextAttack(
			EncryptionOracle<MonoalphabeticCipher> oracle, Alphabet alphabet) {
		String plaintext = new String(alphabet.asCharArray());
		
		if((plaintext == null) || plaintext.isEmpty()) {
			throw new InvalidAlphabetException("Das Alphabet enthält keine Zeichen!");
		}
		
		return oracle.encrypt(plaintext).toCharArray();
	}

}
