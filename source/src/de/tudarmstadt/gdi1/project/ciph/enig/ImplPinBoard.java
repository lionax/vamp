package de.tudarmstadt.gdi1.project.ciph.enig;

import java.util.HashMap;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.exception.InvalidAlphabetException;
import de.tudarmstadt.gdi1.project.exception.InvalidCharacterException;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;

/**
 * @author vamp
 * @version FINAL
 *
 */
public class ImplPinBoard implements PinBoard {

	private HashMap<Character, Character> connections;

	public ImplPinBoard(Alphabet source, Alphabet destination){
		
		if (destination.size() > source.size()){
			throw new InvalidAlphabetException("Destination alphabet contains chars which do not exist in source.");
		}
		if ((! new ImplUtils().containsSameCharacters(source, destination)) && destination.size() == source.size()) {
			throw new InvalidAlphabetException();
		}
		
		connections = new HashMap<Character, Character>();
		
		for (int i = 0; i < source.size(); i++){
			if (destination.contains(source.getChar(i))){
				connections.put(source.getChar(i), destination.getChar(i));
				connections.put(destination.getChar(i), source.getChar(i));
			} else {
				connections.put(source.getChar(i), source.getChar(i));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard#translate(char)
	 */
	@Override
	public char translate(char c) {
		
		if (! connections.containsKey(c)){
			throw new InvalidCharacterException();
		}
		
		return connections.get(c);
	}

}
