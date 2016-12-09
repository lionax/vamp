/**
 * 
 */
package de.tudarmstadt.gdi1.project.ciph.enig;

import java.util.List;

import de.tudarmstadt.gdi1.project.cipher.enigma.Enigma;
import de.tudarmstadt.gdi1.project.cipher.enigma.PinBoard;
import de.tudarmstadt.gdi1.project.cipher.enigma.ReverseRotor;
import de.tudarmstadt.gdi1.project.cipher.enigma.Rotor;

/**
 * @author vamp
 * @version FINAL
 *
 */
public class ImplEnigma implements Enigma {

	private List<Rotor> rotors;
	private PinBoard pinboard;
	private ReverseRotor reverseRotor;

	public ImplEnigma(List<Rotor> rotors, PinBoard pinboard, ReverseRotor reverseRotor){
		this.rotors = rotors;
		this.pinboard = pinboard;
		this.reverseRotor = reverseRotor;
	}
	
	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.Cipher#encrypt(java.lang.String)
	 */
	@Override
	public String encrypt(String text) {
		StringBuilder encryptedText = new StringBuilder(256);
		for (char c: text.toCharArray()){
			c = pinboard.translate(c);
			for (Rotor rotor: rotors){
				c = rotor.translate(c, true);
			}
			c = reverseRotor.translate(c);
			for (int i = 1; i <= rotors.size(); i++){
				c = rotors.get(rotors.size() - i).translate(c, false);
			}
			encryptedText.append(pinboard.translate(c));
			rotateAll(rotors);
		}
		
		for (Rotor rotor: rotors){
			rotor.reset();
		}
		
		return encryptedText.toString();
	}
	
	/**
	 * Rotates the rotors. When the first rotor reaches its initial position, the second rotor does one tick and so on.
	 * @param rotors List of enigma Rotors
	 */
	private void rotateAll(List<Rotor> rotors){
		if ((rotors.size() > 0) && rotors.get(0).rotate()){
			rotateAll(rotors.subList(1, rotors.size()-1));
		}
	}

	/* (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.cipher.Cipher#decrypt(java.lang.String)
	 */
	@Override
	public String decrypt(String text) {
		System.out.println(text);
		return encrypt(text);
	}

}
