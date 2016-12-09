package de.tudarmstadt.gdi1.project.ciph.enig;

import java.util.HashMap;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor;
import de.tudarmstadt.gdi1.project.exception.InvalidAlphabetException;
import de.tudarmstadt.gdi1.project.exception.InvalidCharacterException;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;

/**
 * @author vamp
 * @version FINAL
 *
 */
public class ImplReverseRotor implements ReverseRotor {

	private HashMap<Character, Character> connections;
	
	public ImplReverseRotor(Alphabet entryAlph, Alphabet exitAlph){
		
		if (! new ImplUtils().containsSameCharacters(entryAlph, exitAlph))
			throw new InvalidAlphabetException();
		
		connections = new HashMap<Character, Character>();
		for (int i = 0; i < entryAlph.size(); i++){
			if (entryAlph.getChar(i) == exitAlph.getChar(i)){
				throw new InvalidAlphabetException("Translation must not be reflexive.");
			} else {
				connections.put(entryAlph.getChar(i), exitAlph.getChar(i));
				connections.put(exitAlph.getChar(i), entryAlph.getChar(i));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor#translate(char)
	 */
	@Override
	public char translate(char c) {
		
		if (! connections.containsKey(c)){
			throw new InvalidCharacterException();
		}
		
		return connections.get(c);
	}

}
