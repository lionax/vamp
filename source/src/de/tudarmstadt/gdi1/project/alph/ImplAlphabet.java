package de.tudarmstadt.gdi1.project.alph;

import java.util.*;

import de.tudarmstadt.gdi1.project.exception.*;
import de.tudarmstadt.gdi1.project.alphabet.*;

/**
 * represents an Alphabet
 * @author vamp
 * @version FINAL
 */
public class ImplAlphabet implements Alphabet {

	private ArrayList<Character> alphabet;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Character> iterator() {
		return alphabet.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Alphabet#getIndex(char)
	 */
	@Override
	public int getIndex(char chr) {		
		return alphabet.indexOf(chr);
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Alphabet#getChar(int)
	 */
	@Override
	public char getChar(int index) {
		return alphabet.get(index);
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Alphabet#size()
	 */
	@Override
	public int size() {
		return alphabet.size();
	}

	
	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Alphabet#contains(char)
	 */
	@Override
	public boolean contains(char chr) {
		return alphabet.contains(chr);
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Alphabet#allows(java.lang.String)
	 */
	@Override
	public boolean allows(String word) {
		
		char[] charArr = word.toCharArray();
		
		for(char chr: charArr){
			if(!contains(chr)){
				return false;
			}
		}
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Alphabet#normalize(java.lang.String)
	 */
	@Override
	public String normalize(String input) {
		
		char[] charArr = input.toCharArray();
		StringBuilder res = new StringBuilder();
		
		for(char chr: charArr){
			if(contains(chr)){
				res.append(chr);
			}
		}
		
		return res.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see de.tudarmstadt.gdi1.project.alphabet.Alphabet#asCharArray()
	 */
	@Override
	public char[] asCharArray() {
		char[] res = new char[size()];
		for(int i = 0; i <size(); i++){
			res[i] = getChar(i);
		}
		return res;
	}
	
	/**
	 * adds a new character to the alphabet, ensures that one character can't be added twice in the list
	 * if the character has been in the alphabet before, a new InvalidCharacterException is thrown
	 * @param chr the character which should be added to the dictionary
	 * @return true if it worked
	 */
	private boolean addElement(char chr){
		if(contains(chr)){
			throw new InvalidCharacterException("Dieses Zeichen (" + chr +") ist bereits vorhanden");			
		}
		else{
			alphabet.add(chr);
			return true;
		}
	}
	
	/**
	 * Converts the Alphabet into a String. The Characters are simply appended.
	 */
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		for(char chr: alphabet) {
			res.append(chr);
		}
		
		return res.toString();
	}
	
	/**
	 * Constructor, which creates an empty alphabet
	 */
	public ImplAlphabet(){
		alphabet = new ArrayList<Character>();
	}
	
	
	/**
	 * Constructs a new Alphabet, consisting of the entries from the array
	 * if there are entries which occur twice, the second one is ignored and a message gets printed to the console 
	 * @param arr an array of chars of which the alphabet consists
	 */
	public ImplAlphabet(char[] arr){
		
		alphabet = new ArrayList<Character>();
		for(char chr: arr){
			addElement(chr);
		}
	}
	
	/**
	 * Constructs a new Alphabet, consisting of the entries from the array
	 * if there are entries which occur twice, the second one is ignored and a message gets printed to the console
	 * @param arr a collection of Characters of which the alphabet consists
	 */
	public ImplAlphabet(Collection<Character> characters){
		Character[] arr = characters.toArray(new Character[0]);
		alphabet = new ArrayList<Character>();
		for(char chr: arr){
			addElement(chr);
		}
		
	}

}
