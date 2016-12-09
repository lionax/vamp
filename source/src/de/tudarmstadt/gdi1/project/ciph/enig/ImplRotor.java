package de.tudarmstadt.gdi1.project.ciph.enig;

import de.tudarmstadt.gdi1.project.alphabet.Alphabet;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;
import de.tudarmstadt.gdi1.project.utils.ImplUtils;
import de.tudarmstadt.gdi1.project.utils.Utils;
import de.tudarmstadt.gdi1.project.exception.*;

/**
 * @author vamp
 * @version FINAL
 *
 */
public class ImplRotor implements Rotor{
	
	private int[] wires;
	private int initialPosition;
	private int position;
	private Alphabet entryAlph;
	private Alphabet exitAlph;
	private static Utils util = new ImplUtils();

	/**
	 * @param entryAlph input alphabet
	 * @param exitAlph For the initial connection, each char in the input alphabet will be connected with the char of same index of the exit alphabet.
	 * @param initialPosition The first position the rotor is set to.
	 */
	public ImplRotor(Alphabet entryAlph, Alphabet exitAlph, int initialPosition){

		if (! new ImplUtils().containsSameCharacters(entryAlph, exitAlph))
			throw new InvalidAlphabetException();
		
		this.entryAlph = entryAlph;
		this.exitAlph = exitAlph;
		wires = new int[entryAlph.size()];
		for (int i = 0; i < entryAlph.size(); i++){
			wires[i] = entryAlph.getIndex(exitAlph.getChar(i)) - i;
		}
		this.initialPosition = initialPosition;
		this.position = initialPosition;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.enigma.Rotor#translate(char, boolean)
	 */
	@Override
	public char translate(char c, boolean forward) {
		
		if (! entryAlph.contains(c)){
			throw new InvalidCharacterException();
		}
		
		char newChar;
		if (forward) {
			newChar = entryAlph.getChar((entryAlph.getIndex(c) + wires[(position + entryAlph.getIndex(c)) % wires.length] + wires.length) % wires.length);
		} else {
			newChar = entryAlph.getChar((entryAlph.getIndex(c) - wires[(position + exitAlph.getIndex(c)) % wires.length] + wires.length) % wires.length);
		}
		return newChar;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.enigma.Rotor#rotate()
	 */
	@Override
	public boolean rotate() {
		position = (position + 1) % wires.length;
		exitAlph = util.shiftAlphabet(exitAlph, -1);
		return position == initialPosition;
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.enigma.Rotor#reset()
	 */
	@Override
	public void reset() {
		position = initialPosition;
	}

}
